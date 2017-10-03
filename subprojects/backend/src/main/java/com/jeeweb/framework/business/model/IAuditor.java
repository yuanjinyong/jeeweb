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

    /**
     * 1 新建
     */
    public static final Integer STATUS_NEW = 1;

    /**
     * 2 待审核
     */
    public static final Integer STATUS_PENDING = 2;

    /**
     * 3 正常、已审核、审核通过
     */
    public static final Integer STATUS_APPROVED = 3;

    /**
     * 4 拒绝、审核未通过
     */
    public static final Integer STATUS_REJECTED = 4;

    /**
     * 5 取消
     */
    public static final Integer STATUS_CANCEL = 5;

    Integer getF_auditor_id();

    void setF_auditor_id(Integer f_auditor_id);

    String getF_auditor_name();

    void setF_auditor_name(String f_auditor_name);

    Timestamp getF_audited_time();

    void setF_audited_time(Timestamp f_audited_time);

    String getF_audited_comments();

    void setF_audited_comments(String f_audited_comments);

    Integer getF_status();

    void setF_status(Integer f_status);
}
