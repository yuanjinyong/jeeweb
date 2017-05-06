/**
 * 
 */
package com.jeeweb.framework.business.model;

/**
 * 是否为系统预置
 * 1、是；2、否
 * 
 * @author 袁进勇
 *
 */
public interface IPreset {
    public static final Integer YES = 1;
    public static final Integer NO = 2;

    Integer getF_is_preset();

    void setF_is_preset(Integer f_is_preset);
    
    Boolean isPreset();
}
