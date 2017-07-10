package com.jeeweb.platform.sys.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.SettingEntity;
import com.jeeweb.platform.sys.mapper.SettingMapper;


@Service
@Transactional
public class SettingService extends BaseService<Integer, SettingEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(SettingService.class);
    @Autowired
    private SettingMapper settingMapper;
    // @Autowired
    // private JsonRedisTemplate<SettingEntity> jsonRedisTemplate;

    @Override
    protected BaseMapper<Integer, SettingEntity> getMapper() {
        return settingMapper;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO 需要改成Redis缓存
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public <T> T getValue(String code, T defaultValue, Class<T> clz) {
        List<RowMap> rows = settingMapper.selectMapEntityListPage(new ParameterMap("f_code", code));
        if (HelpUtil.isEmpty(rows)) {
            return null;
        }

        RowMap row = rows.get(0);
        if (clz == String.class) {
            return (T) row.getString("f_value", (String) defaultValue);
        } else if (clz == Boolean.class) {
            return (T) row.getBoolean("f_value", (Boolean) defaultValue);
        } else if (clz == Integer.class) {
            return (T) row.getInteger("f_value", (Integer) defaultValue);
        } else if (clz == Long.class) {
            return (T) row.getLong("f_value", (Long) defaultValue);
        } else if (clz == Double.class) {
            return (T) row.getDouble("f_value", (Double) defaultValue);
        } else if (clz == BigDecimal.class) {
            return (T) row.getBigDecimal("f_value", (BigDecimal) defaultValue);
        } else {
            Object value = row.get("f_value");
            return value == null ? defaultValue : (T) value;
        }
    }
}
