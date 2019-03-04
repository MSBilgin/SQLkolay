package com.msbilgin.sqlkolay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    enum Type {TEXT, INTEGER, REAL, NUMBER, BLOB, INTEGER_AUTOINC}

    Type type() default Type.TEXT;

    boolean primary() default false;

    boolean unique() default false;

    boolean notNull() default false;
}

