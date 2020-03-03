package com.solarexsoft.solarexrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *    Author: houruhou
 *    CreatAt: 11:24/2020/3/3
 *    Desc:
 * </pre>
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface SolarexExtra {
    String name() default "";
}
