/**
 * 
 */
package com.jeeweb.framework.business.enums;

/**
 * @author 袁进勇
 *
 */
public final class Enums {
    public static final Integer TRUE = 101;
    public static final Integer FALSE = 102;

    public static Boolean $true(Integer value) {
        return TRUE.equals(value);
    }

    public static Boolean $false(Integer value) {
        return FALSE.equals(value);
    }

    public static final Integer ENABLE = 101;
    public static final Integer DISABLE = 102;

    public static Boolean $enable(Integer value) {
        return ENABLE.equals(value);
    }

    public static Boolean $disable(Integer value) {
        return DISABLE.equals(value);
    }
}
