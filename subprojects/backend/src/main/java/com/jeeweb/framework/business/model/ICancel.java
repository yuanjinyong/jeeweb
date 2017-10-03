package com.jeeweb.framework.business.model;

public interface ICancel {
    /**
     * 5 取消
     */
    public static final Integer STATUS_CANCEL = 5;

    Integer getStatusCancel();

    Integer getF_status();

    void setF_status(Integer f_status);
}
