/**
 * 
 */
package com.jeeweb.framework.core.model;

/**
 * @author 袁进勇
 *
 */
public interface IValue<O, V> {
    V getValue(O obj);
}
