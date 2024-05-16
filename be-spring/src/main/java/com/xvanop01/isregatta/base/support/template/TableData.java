package com.xvanop01.isregatta.base.support.template;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TableData
 * Anotacia urcujuca nazov implementacii TableDataService
 * @author 2024 Peter Vano
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface TableData {
    String value() default "";
}
