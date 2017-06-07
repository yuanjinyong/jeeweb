/**
 * 
 */
package com.jeeweb.framework.business.model;

/**
 * @author 袁进勇
 *
 */
public interface ITenant {
    Integer getF_tenant_id();

    void setF_tenant_id(Integer f_tenant_id);

    String getF_tenant_name();

    void setF_tenant_name(String f_tenant_name);
}
