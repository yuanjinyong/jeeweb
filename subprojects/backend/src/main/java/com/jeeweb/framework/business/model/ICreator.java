/**
 * 
 */
package com.jeeweb.framework.business.model;

import java.sql.Timestamp;

/**
 * @author 袁进勇
 *
 */
public interface ICreator {
    Integer getF_creator_id();

    void setF_creator_id(Integer f_creator_id);

    String getF_creator_name();

    void setF_creator_name(String f_creator_name);

    Timestamp getF_created_time();

    void setF_created_time(Timestamp f_created_time);
}
