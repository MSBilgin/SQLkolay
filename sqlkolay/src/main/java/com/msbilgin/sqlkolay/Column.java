package com.msbilgin.sqlkolay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * Data types.
     */
    enum Type {
        TEXT, INTEGER, REAL, NUMBER, BLOB, INTEGER_AUTOINC
    }

    /**
     * Column type. Default is Text
     *
     * @return
     */
    Type type() default Type.TEXT;

    /**
     * Marks as Primary Key
     *
     * @return
     */
    boolean primary() default false;

    /**
     * Marks as Unique
     *
     * @return
     */
    boolean unique() default false;

    /**
     * Marks as Not Null
     *
     * @return
     */
    boolean notNull() default false;

    /**
     * Used for setting default value. Text values must be defined with quotes.
     *
     * @return
     */
    String defval() default "";
}

