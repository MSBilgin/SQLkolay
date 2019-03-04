package com.msbilgin.sqlkolay;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Table {
    public final String name;
    private SQLiteDatabase db;


    public Table(String tableName) {
        this.name = tableName;
    }

    private String sqlCreate() {
        List<String> columnDefinitions = new ArrayList<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == Column.class) {
                String name = field.getName();
                String type = "TEXT";
                String unique = "";
                String primaryKey = "";
                String notNull = "";

                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    unique = column.unique() ? "UNIQUE" : "";
                    primaryKey = column.primary() ? "PRIMARY KEY" : "";
                    notNull = column.notNull() ? "NOT NULL" : "";

                    //autoincrement and primary key order.
                    if (column.type() == Column.Type.INTEGER_AUTOINC) {
                        if (column.primary() == true) {
                            type = "INTEGER PRIMARY KEY AUTOINCREMENT";
                            primaryKey = "";
                        } else {
                            type = "INTEGER AUTOINCREMENT";
                        }
                    } else {
                        type = column.type().name();
                    }
                }

                String[] array = {name, type, primaryKey, unique, notNull};
                columnDefinitions.add(TextUtils.join(" ", array).trim());
            }
        }

        String columnStr = TextUtils.join(",", columnDefinitions);
        String sql = String.format("CREATE TABLE %s(%s)", name, columnStr);
        return sql;
    }

    private String sqlDrop() {
        return "DROP TABLE IF EXISTS " + name;
    }

    private void setDB(SQLiteDatabase db) {
        this.db = db;
    }

    public SQLiteDatabase getDB() {
        return this.db;
    }


    protected void delete(String whereClause, String[] whereArgs) {
        getDB().delete(name, whereClause, whereArgs);
    }

    protected void insert(String nullColumnHack, ContentValues values) {
        getDB().insert(name, nullColumnHack, values);
    }

    protected void update(ContentValues values, String whereClause, String[] whereArgs) {
        getDB().update(name, values, whereClause, whereArgs);
    }


}
