package com.msbilgin.sqlkolay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Method;

class DB extends SQLiteOpenHelper {
    private final Table[] tables;
    private final UpgradeCallback upgradeCallback;

    DB(Context context, String name, int version, UpgradeCallback upgradeCallback, Table[] tables) {
        super(context, name, null, version);
        this.tables = tables;
        this.upgradeCallback = upgradeCallback;
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
        if (upgradeCallback != null) {
            upgradeCallback.call(db);
        }

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
