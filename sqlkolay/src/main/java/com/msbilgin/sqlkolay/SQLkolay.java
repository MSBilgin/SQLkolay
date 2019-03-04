package com.msbilgin.sqlkolay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class SQLkolay {
    private final int version;
    private final String databaseName;
    private final Context context;

    private boolean isRegistered = false;

    public SQLkolay(Context context, String databaseName, int version) {
        this.context = context;
        this.databaseName = databaseName;
        this.version = version;
    }

    protected void registerTables(Table... tables) {
        if (isRegistered) {
            Log.w("SQLkolay", "registerTables can be called only one time");
            return;
        }

        boolean calledConstructer = false;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement traceElement : stackTraceElements) {
            calledConstructer = traceElement.getMethodName().equals("<init>") ? true : calledConstructer;
        }

        if (!calledConstructer) {
            Log.w("SQLkolay", "registerTables can be called only in constructer");
            return;
        }

        SqliteDB sqliteDB = new SqliteDB(context, databaseName, version, tables);
        SQLiteDatabase sqLiteDatabase = sqliteDB.getWritableDatabase();

        for (int i = 0; i < tables.length; i++) {
            try {
                Class tableClass = tables[i].getClass().getSuperclass();
                Method method = tableClass.getDeclaredMethod("setDB", SQLiteDatabase.class);
                method.setAccessible(true);
                method.invoke(tables[i], sqLiteDatabase);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isRegistered = true;
    }


    /**
     * SQLite class
     */
    private class SqliteDB extends SQLiteOpenHelper {
        private Table[] tables;

        SqliteDB(Context context, String name, int version, Table[] tables) {
            super(context, name, null, version);
            this.tables = tables;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (Table table : tables) {
                try {
                    Class tableClass = table.getClass().getSuperclass();
                    Method method = tableClass.getDeclaredMethod("sqlCreate");
                    method.setAccessible(true);
                    String sql = (String) method.invoke(table);
                    db.execSQL(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (Table table : tables) {
                try {
                    Class tableClass = table.getClass().getSuperclass();
                    Method method = tableClass.getDeclaredMethod("sqlDrop");
                    method.setAccessible(true);
                    String sql = (String) method.invoke(table);
                    db.execSQL(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            onCreate(db);
        }
    }

}
