/**
 * 
 */
package com.jeeweb.framework.business.enums;

/**
 * @author 袁进勇
 *
 */
public final class AuditorStatus {
    /**
     * 101、新建
     */
    public static final Integer NEW = 101;

    /**
     * 102、待审核
     */
    public static final Integer PENDING = 102;

    /**
     * 103、正常/已审核/审核通过
     */
    public static final Integer APPROVED = 103;

    /**
     * 104、拒绝/审核未通过
     */
    public static final Integer REJECTED = 104;

    /**
     * 105、取消
     */
    public static final Integer CANCEL = 105;
}
