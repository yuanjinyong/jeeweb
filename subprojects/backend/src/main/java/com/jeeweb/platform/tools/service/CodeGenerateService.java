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
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
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
                if (field.getF_is_primary() == 1) {
                    table.setPrimaryField(field);
                    break;
                }
            }
        }

        return tableList;
    }

    private void insertTableEntities(GenerateRuleEntity entity) {
        List<GenerateRuleFieldEntity> fieldList = new ArrayList<>();
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
        rule.setF_package_dir(rule.getF_package_name().replaceAll("\\.", "/"));
        String url = rule.getF_request_url();
        if (url.matches("^/api/v\\d+/.*$")) {
            rule.setF_view_dir(url.substring(url.indexOf("/", "/api/v".length()) + 1));
        } else if (url.matches("^/api/.*$")) {
            rule.setF_view_dir(url.substring("/api/".length()));
        } else {
            rule.setF_view_dir(url);
        }

        try {
            Map<String, Object> tplParams = new HashMap<>();
            tplParams.put("rule", rule);
            tplParams.put("ResponseResult_class", ResponseResult.class.getName());
            tplParams.put("ParameterMap_class", ParameterMap.class.getName());
            tplParams.put("RowMap_class", RowMap.class.getName());

            for (GenerateRuleTableEntity table : rule.getTableList()) {
                fillEntityInfo(table);
                tplParams.put("entity", table);
                if (!RowMap.class.getName().equals(table.getF_entity_class())) {
                    // 先生成Entity
                    archetypeService.generateArchetype(new File(archetypesDir, "backend/entity"), tempDir, tplParams);
                }

                // 再生成Mapper
                // tplParams.put("mapper", buildMapperInfo(table));
                archetypeService.generateArchetype(new File(archetypesDir, "backend/mapper"), tempDir, tplParams);
            }

            // 最后生成Service、Controller和jsp
            fillEntityInfo(rule.getMainTable());
            tplParams.put("entity", rule.getMainTable());
            // tplParams.put("mapper", buildMapperInfo(rule.getMainTable()));
            // tplParams.put("service", buildServiceInfo(rule));
            archetypeService.generateArchetype(new File(archetypesDir, "backend/service"), tempDir, tplParams);

            // tplParams.put("web", buildWebInfo(rule));
            archetypeService.generateArchetype(new File(archetypesDir, "backend/web"), tempDir, tplParams);

            FileUtils.copyDirectory(tempDir, projectDir);
        } catch (Exception e) {
            LOG.error("生成代码失败！", e);
            throw new BusinessException("生成代码失败！ " + e.getMessage(), e);
        } finally {
            FileDeleteStrategy.FORCE.deleteQuietly(tempDir);
        }
    }

    private void fillEntityInfo(GenerateRuleTableEntity table) {
        table.setF_entity_class_name(getShortClassName(table.getF_entity_class()));
        table.setF_entity_base_class_name(getShortClassName(table.getF_entity_base_class()));
        table.setF_mapper_class_name(getShortClassName(table.getF_mapper_class()));
        table.setF_mapper_base_class_name(getShortClassName(table.getF_mapper_base_class()));
        table.setF_service_class_name(getShortClassName(table.getF_service_class()));
        table.setF_service_base_class_name(getShortClassName(table.getF_service_base_class()));
        table.setF_rest_class_name(getShortClassName(table.getF_rest_class()));
        table.setF_rest_base_class_name(getShortClassName(table.getF_rest_base_class()));

        GenerateRuleFieldEntity primaryField = table.getPrimaryField();
        primaryField.setF_short_java_type(getShortClassName(primaryField.getF_java_type()));
        for (GenerateRuleFieldEntity field : table.getFieldList()) {
            field.setF_short_java_type(getShortClassName(field.getF_java_type()));
        }
    }

    private String getShortClassName(String fullClassName) {
        return HelpUtil.isEmpty(fullClassName) ? "" : fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }
}
