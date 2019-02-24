package com.msbilgin.sqlkolayexample;

import android.content.Context;

import com.msbilgin.sqlkolay.SQLkolay;

public class AppDB extends SQLkolay {
    private static final String databaseName = "database";
    private static final int version = 14;

    public static TableUser user = new TableUser();

    public AppDB(Context context) {
        super(context, databaseName, version);
    }
}
