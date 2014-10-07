package com.century.logregator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ignore columns in dbunit check
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IgnoreCol {
    /**
     * name of table
     */
    String table() default "";

    /**
     * columns to ignore
     */
    String[] cols() default {"id"};

    /**
     * list of replacemnts for dataset
     */
    String[] replacemnt() default {};
}
