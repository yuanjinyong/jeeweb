/**
 * 
 */
package com.jeeweb.framework.business.model;

import java.sql.Timestamp;

/**
 * @author 袁进勇
 *
 */
public interface IAuditor extends ICancel {
    Long getF_auditor_id();

    void setF_auditor_id(Long f_auditor_id);

    String getF_auditor_name();

    void setF_auditor_name(String f_auditor_name);

    Timestamp getF_audited_time();

    void setF_audited_time(Timestamp f_audited_time);

    String getF_audited_comments();

    void setF_audited_comments(String f_audited_comments);

    @Override
    Integer getF_status();

    @Override
    void setF_status(Integer f_status);
}
