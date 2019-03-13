package com.msbilgin.sqlkolayexample;

import android.content.Context;

import com.msbilgin.sqlkolay.SQLkolay;

public class AppDB extends SQLkolay {
    private static final String databaseName = "app.db";
    private static final int version = 18;

    public TableBooks books = new TableBooks();

    public AppDB(Context context) {
        super(context, databaseName, version);
        registerTables(books);
    }
}
