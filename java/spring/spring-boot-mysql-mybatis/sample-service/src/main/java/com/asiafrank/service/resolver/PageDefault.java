package com.asiafrank.service.resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author user created at 4/27/2017.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
@Documented
public @interface PageDefault {
    int pageNum() default 1;
    int pageSize() default 10;
}
