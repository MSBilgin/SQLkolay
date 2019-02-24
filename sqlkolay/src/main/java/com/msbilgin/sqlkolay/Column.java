package com.msbilgin.sqlkolay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    enum Type {TEXT, INTEGER, REAL}

    Type type() default Type.TEXT;
}

