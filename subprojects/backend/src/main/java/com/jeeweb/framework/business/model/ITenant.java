/**
 * 
 */
package com.jeeweb.framework.business.model;

/**
 * @author 袁进勇
 *
 */
public interface ITenant {
    Long getF_tenant_id();

    void setF_tenant_id(Long f_tenant_id);

    String getF_tenant_name();

    void setF_tenant_name(String f_tenant_name);
}
