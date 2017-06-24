package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.mapper.SqlMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.DictEntity;
import com.jeeweb.platform.sys.entity.DictItemEntity;
import com.jeeweb.platform.sys.mapper.DictItemMapper;
import com.jeeweb.platform.sys.mapper.DictMapper;

@Service
@Transactional
public class DictService extends BaseService<Integer, DictEntity> implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(DictService.class);

    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private DictItemMapper dictItemMapper;

    @Autowired
    @Qualifier("defaultSqlMapper")
    private SqlMapper sqlMapper;

    @Override
    protected BaseMapper<Integer, DictEntity> getMapper() {
        return dictMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public DictEntity selectEntity(Integer primaryKey) {
        DictEntity entity = getMapper().selectEntity(primaryKey);
        entity.setItemList(
                selectDictItemList(new ParameterMap("f_dict_code", entity.getF_code()).setOrderBy("f_item_order")));
        return entity;
    }

    @Override
    public void insertEntity(DictEntity entity) {
        entity.setF_tenant_column("0");
        insertDictItemList(entity);

        super.insertEntity(entity);
    }

    @Override
    public void updateEntity(Integer primaryKey, DictEntity entity) {
        deleteDictItemList(entity);
        insertDictItemList(entity);

        super.updateEntity(primaryKey, entity);
    }

    @Override
    protected void beforeDeleteEntity(DictEntity entity) {
        deleteDictItemList(entity);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional(readOnly = true)
    public void afterPropertiesSet() throws Exception {
        long startTime = System.currentTimeMillis();
        LOG.info("====加载字典信息。");

        getDicts();

        LOG.info("====加载字典信息完成，耗时：{}ms。", (System.currentTimeMillis() - startTime));
    }

    // TODO 后续需要改为Redis缓存，区分启动加载和首次使用加载
    @Transactional(readOnly = true)
    public Map<String, List<DictItemEntity>> getDicts() {
        Map<String, List<DictItemEntity>> dicts = new HashMap<>();

        LOG.info("====加载通用字典组。");
        List<DictItemEntity> dictItemList = dictItemMapper
                .selectEntityListPage(new ParameterMap().setOrderBy("f_dict_code,f_item_order"));
        addDict(dicts, dictItemList);

        LOG.info("====加载单表字典组。");
        List<DictEntity> dictList = dictMapper
                .selectEntityListPage(new ParameterMap("f_table_name_notEqual", "t_sys_dict_item"));
        for (DictEntity dict : dictList) {
            addDict(dicts, dict);
        }

        return dicts;
    }

    private void addDict(Map<String, List<DictItemEntity>> dicts, DictEntity dict) {
        String sql = String.format("SELECT %s as f_item_code, %s as f_item_text FROM %s.%s %s ORDER BY %s",
                dict.getF_code_column(), dict.getF_name_column(), dict.getF_db_name(), dict.getF_table_name(),
                HelpUtil.isEmpty(dict.getF_where_clause()) ? "" : dict.getF_where_clause(), dict.getF_order_column());
        LOG.debug("====加载{}：{}。", dict.getF_code(), sql);
        dicts.put(dict.getF_code(), sqlMapper.selectListPage(sql, DictItemEntity.class));
    }

    private void addDict(Map<String, List<DictItemEntity>> dicts, List<DictItemEntity> dictItemList) {
        for (DictItemEntity dictItem : dictItemList) {
            String dictCode = dictItem.getF_dict_code();
            List<DictItemEntity> dictItems = dicts.get(dictCode);
            if (HelpUtil.isEmpty(dictItems)) {
                dictItems = new ArrayList<>();
                dicts.put(dictCode, dictItems);
            }
            // dictItem.setF_id(null);
            // dictItem.setF_tenant_id(null);
            // dictItem.setF_dict_code(null);
            // dictItem.setF_item_order(null);
            // dictItem.setF_is_preset(null);
            dictItems.add(dictItem);
        }
    }

    // TODO 后续需要改为Redis缓存
    @Transactional(readOnly = true)
    public List<DictItemEntity> getDictItemList(String dictCode) {
        return selectDictItemList(new ParameterMap("f_dict_code", dictCode).setOrderBy("f_item_order"));
    }

    // TODO 后续需要改为Redis缓存
    @Transactional(readOnly = true)
    public Map<Object, Object> getDict(String dictCode) {
        Map<Object, Object> dictMap = new HashMap<>();
        List<RowMap> dictItemList = dictItemMapper.selectMapEntityListPage(new ParameterMap("f_dict_code", dictCode));
        for (RowMap dictItemMap : dictItemList) {
            dictMap.put(dictItemMap.get("f_item_code"), dictItemMap.get("f_item_name"));
        }
        return dictMap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<DictItemEntity> selectDictItemList(ParameterMap params) {
        return dictItemMapper.selectEntityListPage(params);
    }

    private void insertDictItemList(DictEntity dict) {
        List<DictItemEntity> itemList = dict.getItemList();
        // 只有页面和按钮下面才能配置可以访问的URL地址
        if (HelpUtil.isEmpty(itemList)) {
            return;
        }

        int i = 0;
        for (DictItemEntity item : itemList) {
            item.setF_tenant_id(0);
            item.setF_dict_code(dict.getF_code());
            item.setF_item_order(i++);
        }

        dictItemMapper.insertEntities(itemList);
    }

    private void deleteDictItemList(DictEntity dict) {
        dictItemMapper.deleteEntities(new ParameterMap("f_dict_code", dict.getF_code()));
    }
}
