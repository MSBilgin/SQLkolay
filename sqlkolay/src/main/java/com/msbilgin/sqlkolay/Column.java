package com.msbilgin.sqlkolay;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    enum Type {TEXT, NUM, INT, INT_AUTOINC, REAL, BLOB}

    enum PrimaryKey {True, False}

    enum Unique {True, False}

    enum NotNull {True, False}

    Type type() default Type.TEXT;

    PrimaryKey primary() default PrimaryKey.False;

    Unique unique() default Unique.False;

    NotNull notNull() default NotNull.True;
}

