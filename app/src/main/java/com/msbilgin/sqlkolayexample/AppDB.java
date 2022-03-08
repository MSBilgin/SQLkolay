package com.msbilgin.sqlkolayexample;

import android.content.Context;
import android.util.Log;

import com.msbilgin.sqlkolay.SQLkolay;

public class AppDB extends SQLkolay {
    private static final String databaseName = "app.db";
    private static final int version = 20;

    private static AppDB instance;

    public TableBooks books = new TableBooks();

    public static AppDB getInstance(Context context) {
        instance = instance == null ? new AppDB(context) : instance;
        return instance;
    }

    private AppDB(Context context) {
        super(context, databaseName, version, null);
        registerTables(books);
    }
}
