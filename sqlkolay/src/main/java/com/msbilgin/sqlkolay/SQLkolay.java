package com.msbilgin.sqlkolay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class SQLkolay {

    public SQLkolay(Context context, String databaseName, int version) {
        List<Table> tables = getTables();
        Sqlite sqlite = new Sqlite(context, databaseName, version, tables);
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        for (Table table : tables) {
            try {
                Class tableClass = table.getClass().getSuperclass();
                Method method = tableClass.getDeclaredMethod("setDB", SQLiteDatabase.class);
                method.setAccessible(true);
                method.invoke(table,sqLiteDatabase);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * {@link Table} tipindeki nesnelerin listesini getirir.
     *
     * @return Table listesi
     */
    private List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            if (Table.class.isAssignableFrom(field.getType())) {
                try {
                    tables.add((Table) field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return tables;
    }

    /**
     * SQLite sınıfı
     */
    private class Sqlite extends SQLiteOpenHelper {
        private List<Table> tables;

        Sqlite(Context context, String name, int version, List<Table> tables) {
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
