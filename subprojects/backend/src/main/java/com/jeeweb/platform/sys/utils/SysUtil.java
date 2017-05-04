package com.jeeweb.platform.sys.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.platform.security.SecurityUser;
import com.jeeweb.platform.sys.entity.UserEntity;

public class SysUtil {
    public static String P_DEFAULT_PASSWORD = "DefaultPassword";
    public static String P_EXPIRY_DATE = "ExpiryDate";

    // public static String getParameterValue(String parameterId, String defaultValue) {
    // return getParameterValue(parameterId, defaultValue, getTenantId());
    // }
    //
    // public static String getParameterValue(String parameterId, String defaultValue, Integer tenantId) {
    // return getParameterValue(parameterId, defaultValue, String.class, tenantId);
    // }
    //
    // public static <T> T getParameterValue(String parameterId, T defaultValue, Class<T> clz) {
    // return getParameterValue(parameterId, defaultValue, clz, getTenantId());
    // }
    //
    // public static <T> T getParameterValue(String parameterId, T defaultValue, Class<T> clz, Integer tenantId) {
    // T value = ParameterCache.getParameterValue(getTenantIdList(tenantId), parameterId, clz);
    // return value == null ? defaultValue : value;
    // }
    //
    // public static List<DictItemEntity> getDictItemList(String dictCode) {
    // return DictCache.getDictItemList(getTenantIdList(), dictCode);
    // }
    //
    // public static Map<Object, Object> getDictItemMap(String dictCode) {
    // return DictCache.getDictItemMap(getTenantIdList(), dictCode);
    // }
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

    public static UserEntity getCurUser() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token instanceof UsernamePasswordAuthenticationToken && token.getPrincipal() instanceof SecurityUser) {
            SecurityUser user = (SecurityUser) token.getPrincipal();
            return user.getUser();
        }

        return null;
    }

    public static void appendCurUserAndRoles(ParameterMap params) {
        UserEntity user = getCurUser();
        // 是否为超级管理员
        if (user != null && !user.isSuperAdmin()) {
            params.put("cur_user_id", user.getF_id());
            params.put("cur_role_id_in", user.getF_role_ids());
        }
    }

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
