/**
 * 
 */
package com.jeeweb.framework.core.listener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;

/**
 * @author 袁进勇
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Documented
public @interface Startup {

    /**
     * The startup order value.
     * <p>
     * Default is {@link Ordered#LOWEST_PRECEDENCE}.
     * 
     * @see Ordered#getOrder()
     */
    int order() default Ordered.LOWEST_PRECEDENCE;

    String desc() default "";
}
