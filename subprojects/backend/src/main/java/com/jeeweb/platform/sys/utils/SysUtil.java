package com.jeeweb.platform.sys.utils;

import java.util.List;
import java.util.Map;

import com.jeeweb.framework.core.aware.SpringContextAware;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.DictItemEntity;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.service.DictService;
import com.jeeweb.platform.sys.service.SettingService;

public final class SysUtil {
    public static String P_DEFAULT_PASSWORD = "DefaultPassword";
    public static String P_EXPIRY_DATE = "ExpiryDate";

    public static void appendCurUserAndRoles(ParamsMap params) {
        UserEntity user = SecurityUtil.getCurUser(null);
        // 是否为超级管理员
        if (user != null && !user.isSuperAdmin()) {
            params.put("cur_user_id", user.getF_id());
            params.put("cur_role_id_in", HelpUtil.joinToInString(user.getRoleIdList()));
        }
    }

    public static String getSetting(String code, String defaultValue) {
        return getSetting(code, defaultValue, String.class);
    }

    public static <T> T getSetting(String code, T defaultValue, Class<T> clz) {
        SettingService settingService = SpringContextAware.getBean(SettingService.class);
        T value = settingService.getValue(code, defaultValue, clz);
        return value == null ? defaultValue : value;
    }

    public static List<DictItemEntity> getDictItemList(String dictCode) {
        DictService dictService = SpringContextAware.getBean(DictService.class);
        return dictService.getDictItemList(dictCode);
    }

    public static Map<Object, Object> getDictItemMap(String dictCode) {
        DictService dictService = SpringContextAware.getBean(DictService.class);
        return dictService.getDictItemMap(dictCode);
    }
    //
    // public static Integer getTenantId() {
    // UserEntity user = getCurUser();
    // return user == null ? TenantEntity.ROOT_TENANT : getCurUser().getF_tenant_id();
    // }
    //
    // public static List<Integer> getTenantIdList() {
    // UserEntity user = getCurUser();
    // Integer tenantId = user == null ? TenantEntity.ROOT_TENANT : getCurUser().getF_tenant_id();
    // return TenantCache.getTenantIdList(tenantId);
    // }
    //
    // public static List<Integer> getTenantIdList(Integer tenantId) {
    // return TenantCache.getTenantIdList(tenantId);
    // }
    //
    // public static void appendCurTenantIds(MapEntity params) {
    // UserEntity user = getCurUser();
    // if (user != null && user.getF_tenant_id() != TenantEntity.ROOT_TENANT) {
    // if (user.getF_tenant_ids() == null) {
    // params.put("cur_tenant_id", user.getF_tenant_id());
    // } else {
    // params.put("cur_tenant_id_in", user.getF_tenant_id() + "," + user.getF_tenant_ids());
    // }
    // }
    // }
}
