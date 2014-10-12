package com.century.logregator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configuration for database testing
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DbTest {
    /**
     * name of xml with expected dataset
     */
    String expected() default "";

    /**
     * list of tables for assertion
     */
    String[] assertTables() default {};

    /**
     * list of replacemnts for dataset
     */
    String[] replacemnt() default {};
}
