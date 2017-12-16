/**
 * 
 */
package com.jeeweb.framework.business.model;

import java.sql.Timestamp;

/**
 * @author 袁进勇
 *
 */
public interface IModifier {
    Long getF_modifier_id();

    void setF_modifier_id(Long f_modifier_id);

    String getF_modifier_name();

    void setF_modifier_name(String f_modifier_name);

    Timestamp getF_modified_time();

    void setF_modified_time(Timestamp f_modified_time);
}
