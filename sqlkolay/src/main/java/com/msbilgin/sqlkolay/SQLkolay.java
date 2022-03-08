package com.msbilgin.sqlkolay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Method;

public abstract class SQLkolay {
    private final Context context;
    private final int version;
    private final String databaseName;
    private final UpgradeCallback upgradeCallback;

    private boolean isRegistered = false;

    public SQLkolay(Context context, String databaseName, int version, UpgradeCallback upgradeCallback) {
        this.context = context;
        this.databaseName = databaseName;
        this.version = version;
        this.upgradeCallback = upgradeCallback;
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

        DB DB = new DB(context, databaseName, version, upgradeCallback, tables);
        SQLiteDatabase sqLiteDatabase = DB.getWritableDatabase();

        for (Table table : tables) {
            try {
                Class tableClass = table.getClass().getSuperclass();
                Method method = tableClass.getDeclaredMethod("setDB", SQLiteDatabase.class);
                method.setAccessible(true);
                method.invoke(table, sqLiteDatabase);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isRegistered = true;
    }

    public void setOnUpgradeCallback(Runnable callback) {

    }
}
