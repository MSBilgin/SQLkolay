package com.msbilgin.sqlkolay;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class Table {
    public final String tableName;
    private SQLiteDatabase db;


    public Table(String tableName) {
        this.tableName = tableName;
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
                String defVal = "";

                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    unique = column.unique() ? "UNIQUE" : "";
                    primaryKey = column.primary() ? "PRIMARY KEY" : "";
                    notNull = column.notNull() ? "NOT NULL" : "";
                    defVal = column.defval();

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
                        defVal = TextUtils.isEmpty(column.defval()) ? "" : "DEFAULT " + defVal;
                    }
                }

                String[] array = {name, type, primaryKey, defVal, unique, notNull};
                columnDefinitions.add(TextUtils.join(" ", array).trim());
            }
        }

        String columnStr = TextUtils.join(",", columnDefinitions);
        String sql = String.format("CREATE TABLE %s(%s)", tableName, columnStr);
        return sql;
    }

    private String sqlDrop() {
        return "DROP TABLE IF EXISTS " + tableName;
    }

    /**
     * Used for setting Sqlite database. Connection is opened.
     *
     * @param db
     */
    private void setDB(SQLiteDatabase db) {
        this.db = db;
    }

    public SQLiteDatabase getDB() {
        return this.db;
    }


    protected int delete(String whereClause, String[] whereArgs) {
        return getDB().delete(tableName, whereClause, whereArgs);
    }

    protected long insert(String nullColumnHack, ContentValues values) {
        return getDB().insert(tableName, nullColumnHack, values);
    }

    protected int update(ContentValues values, String whereClause, String[] whereArgs) {
       return getDB().update(tableName, values, whereClause, whereArgs);
    }


}
