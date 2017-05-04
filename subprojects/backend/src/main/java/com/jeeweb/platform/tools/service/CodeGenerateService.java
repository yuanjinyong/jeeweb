package com.jeeweb.platform.tools.service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.controller.BaseController;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.MenuEntity;
import com.jeeweb.platform.sys.mapper.MenuMapper;
import com.jeeweb.platform.tools.entity.GenerateRuleEntity;
import com.jeeweb.platform.tools.entity.GenerateRuleFieldEntity;
import com.jeeweb.platform.tools.entity.GenerateRuleTableEntity;
import com.jeeweb.platform.tools.mapper.GenerateRuleFieldMapper;
import com.jeeweb.platform.tools.mapper.GenerateRuleMapper;
import com.jeeweb.platform.tools.mapper.GenerateRuleTableMapper;

@Service
@Transactional
public class CodeGenerateService extends BaseService<Integer, GenerateRuleEntity> {
    private static final Logger LOG = LoggerFactory.getLogger(CodeGenerateService.class);

    @Autowired
    private GenerateRuleMapper generateRuleMapper;
    @Autowired
    private GenerateRuleTableMapper generateRuleTableMapper;
    @Autowired
    private GenerateRuleFieldMapper generateRuleFieldMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ArchetypeService archetypeService;

    @Override
    protected BaseMapper<Integer, GenerateRuleEntity> getMapper() {
        return generateRuleMapper;
    }

    @Override
    public GenerateRuleEntity selectEntity(Integer primaryKey) {
        GenerateRuleEntity entity = super.selectEntity(primaryKey);

        entity.setTableList(selectTableEntities(new ParameterMap("f_rule_id", entity.getF_id()).setOrderBy("f_order")));
        for (GenerateRuleTableEntity table : entity.getTableList()) {
            if (table.getF_is_main() == 1) {
                entity.setMainTable(table);
                break;
            }
        }

        return entity;
    }

    @Override
    public void insertEntity(GenerateRuleEntity entity) {
        super.insertEntity(entity);

        insertTableEntities(entity);
    }

    @Override
    public void updateEntity(GenerateRuleEntity entity) {
        deleteTableEntities(entity.getF_id());
        insertTableEntities(entity);
        super.updateEntity(entity);
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        deleteTableEntities(primaryKey);
        super.deleteEntity(primaryKey);
    }

    @Override
    public void deleteEntities(ParameterMap params) {
        ParameterMap deleteParams = new ParameterMap("f_rule_id_in", params.get("f_id_in"));
        generateRuleFieldMapper.deleteEntities(deleteParams);
        generateRuleTableMapper.deleteEntities(deleteParams);

        super.deleteEntities(params);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<GenerateRuleTableEntity> selectTableEntities(ParameterMap params) {
        List<GenerateRuleTableEntity> tableList = generateRuleTableMapper.selectEntityListPage(params);
        for (GenerateRuleTableEntity table : tableList) {
            table.setFieldList(generateRuleFieldMapper
                    .selectEntityListPage(new ParameterMap("f_table_id", table.getF_id()).setOrderBy("f_order")));
            for (GenerateRuleFieldEntity field : table.getFieldList()) {
                if (field.getF_is_primary()) {
                    table.setPrimaryField(field);
                    break;
                }
            }
        }

        return tableList;
    }

    private void insertTableEntities(GenerateRuleEntity entity) {
        List<GenerateRuleFieldEntity> fieldList = new ArrayList<GenerateRuleFieldEntity>();
        for (GenerateRuleTableEntity table : entity.getTableList()) {
            table.setF_rule_id(entity.getF_id());
            fieldList.addAll(insertTableEntity(table));
        }

        if (!HelpUtil.isEmpty(fieldList)) {
            generateRuleFieldMapper.insertEntities(fieldList);
        }
    }

    private List<GenerateRuleFieldEntity> insertTableEntity(GenerateRuleTableEntity entity) {
        generateRuleTableMapper.insertEntity(entity);
        if (HelpUtil.isEmpty(entity.getFieldList())) {
            return Collections.emptyList();
        }

        List<GenerateRuleFieldEntity> fieldList = entity.getFieldList();
        for (GenerateRuleFieldEntity field : fieldList) {
            field.setF_table_id(entity.getF_id());
        }
        return fieldList;
    }

    public void deleteTableEntities(Integer f_rule_id) {
        ParameterMap deleteParams = new ParameterMap("f_rule_id", f_rule_id);
        generateRuleFieldMapper.deleteEntities(deleteParams);
        generateRuleTableMapper.deleteEntities(deleteParams);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void generateCode(Integer f_rule_id) {
        URL rootPath = this.getClass().getResource("/");
        if (!"file".equals(rootPath.getProtocol())) {
            return;
        }

        File projectDir = new File(rootPath.getFile().substring(1, rootPath.getFile().indexOf("/src/")));
        File archetypesDir = new File(projectDir, "src/test/resources/META-INF/archetypes");
        File tempDir = new File(projectDir.getParentFile(), "t_" + UUID.randomUUID());
        GenerateRuleEntity rule = selectEntity(f_rule_id);
        rule.setF_view_path(rule.getF_request_url().substring(1));
        rule.setF_package_dir(rule.getF_package_name().replaceAll("\\.", "/"));

        try {
            Map<String, Object> tplParams = new HashMap<>();
            tplParams.put("feature", rule);
            for (GenerateRuleTableEntity table : rule.getTableList()) {
                fillEntityInfo(table);
                tplParams.put("entity", table);
                if (!RowMap.class.getName().equals(table.getF_entity_class())) {
                    // 先生成Entity
                    archetypeService.generateArchetype(new File(archetypesDir, "feature/entity"), tempDir, tplParams);
                }

                // 再生成Mapper
                tplParams.put("mapper", buildMapperInfo(table));
                archetypeService.generateArchetype(new File(archetypesDir, "feature/mapper"), tempDir, tplParams);
            }

            // 最后生成Service、Controller和jsp
            fillEntityInfo(rule.getMainTable());
            tplParams.put("entity", rule.getMainTable());
            tplParams.put("mapper", buildMapperInfo(rule.getMainTable()));
            tplParams.put("service", buildServiceInfo(rule));
            archetypeService.generateArchetype(new File(archetypesDir, "feature/service"), tempDir, tplParams);
            tplParams.put("web", buildWebInfo(rule));
            archetypeService.generateArchetype(new File(archetypesDir, "feature/web"), tempDir, tplParams);

            FileUtils.copyDirectory(tempDir, projectDir);
        } catch (Exception e) {
            LOG.error("生成代码失败！", e);
            throw new BusinessException("生成代码失败！", e);
        } finally {
            FileDeleteStrategy.FORCE.deleteQuietly(tempDir);
        }
    }

    private void fillEntityInfo(GenerateRuleTableEntity table) {
        table.setF_entity_class_name(getShorClassName(table.getF_entity_class()));
        table.setF_entity_base_class_name(getShorClassName(table.getF_entity_base_class()));
        GenerateRuleFieldEntity primaryField = table.getPrimaryField();
        primaryField.setF_java_type(getShorClassName(primaryField.getF_full_java_type()));
        for (GenerateRuleFieldEntity field : table.getFieldList()) {
            field.setF_java_type(getShorClassName(field.getF_full_java_type()));
        }
    }

    private Map<String, Object> buildMapperInfo(GenerateRuleTableEntity table) {
        Map<String, Object> mapper = new HashMap<>();
        mapper.put("f_full_class_name",
                table.getF_entity_class().replace(".entity.", ".mapper.").replace("Entity", "Mapper"));
        mapper.put("f_class_name", getShorClassName((String) mapper.get("f_full_class_name")));
        mapper.put("f_variable_name", HelpUtil.uncapitalize((String) mapper.get("f_class_name")));
        mapper.put("f_full_base_class_name", BaseMapper.class.getName());
        mapper.put("f_base_class_name", getShorClassName(BaseMapper.class.getName()));
        mapper.put("RowMap_class_name", RowMap.class.getName());
        return mapper;
    }

    private Map<String, Object> buildServiceInfo(GenerateRuleEntity rule) {
        Map<String, Object> service = new HashMap<>();
        service.put("f_full_class_name",
                rule.getF_package_name() + ".service." + HelpUtil.capitalize(rule.getF_code()) + "Service");
        service.put("f_class_name", getShorClassName((String) service.get("f_full_class_name")));
        service.put("f_variable_name", HelpUtil.uncapitalize((String) service.get("f_class_name")));
        service.put("f_full_base_class_name", BaseService.class.getName());
        service.put("f_base_class_name", getShorClassName(BaseService.class.getName()));
        return service;
    }

    private Map<String, Object> buildWebInfo(GenerateRuleEntity rule) {
        Map<String, Object> web = new HashMap<>();
        web.put("f_full_class_name", rule.getF_package_name() + ".web.controller." + rule.getF_code() + "Controller");
        web.put("f_class_name", getShorClassName((String) web.get("f_full_class_name")));
        web.put("f_full_base_class_name", BaseController.class.getName());
        web.put("f_base_class_name", getShorClassName(BaseController.class.getName()));
        web.put("f_parent_id", rule.getF_menu_parent_id());
        MenuEntity menu = menuMapper.selectEntity(rule.getF_menu_parent_id());
        web.put("f_parent_path", menu.getF_parent_path() + menu.getF_id() + "/");

        String id = HelpUtil.uncapitalize(rule.getF_code());
        Map<String, Object> jsp = new HashMap<>();
        web.put("jsp", jsp);
        jsp.put("id", id);
        jsp.put("featureId", id + "Feature");
        jsp.put("gridId", id + "Grid");

        return web;
    }

    private String getShorClassName(String fullClassName) {
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }
}
