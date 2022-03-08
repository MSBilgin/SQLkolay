package com.msbilgin.sqlkolay;

import android.database.sqlite.SQLiteDatabase;

public interface UpgradeCallback {
    void call(SQLiteDatabase sqLiteDatabase);
}
