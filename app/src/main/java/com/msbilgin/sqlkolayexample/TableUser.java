package com.msbilgin.sqlkolayexample;

import android.content.ContentValues;
import android.database.Cursor;

import com.msbilgin.sqlkolay.Column;
import com.msbilgin.sqlkolay.Table;

public class TableUser extends Table {
    private static final String tableName = "USERS";

    @Column(type = Column.Type.INTEGER)
    private Column uid;

    private Column name;
    private Column surname;

    @Column(type = Column.Type.INTEGER)
    private Column age;

    @Column(type = Column.Type.REAL)
    private Column income;

    public TableUser() {
        super(tableName);
    }

    public void add(User user) {
        ContentValues values = new ContentValues();
        values.put("uid", user.uid);
        values.put("name", user.name);
        values.put("surname", user.surname);
        values.put("age", user.age);
        values.put("income", user.income);
        insert(null, values);
    }

    public void remove(long uid) {
        delete("uid=?", new String[]{String.valueOf(uid)});
    }

    public User get(long uid) {
        String sql = String.format("select * from %s where uid=%s", tableName, uid);
        User user = null;

        Cursor cursor = getDB().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            double income = cursor.getDouble(cursor.getColumnIndex("income"));
            user = new User(uid, name, surname, age, income);
        }

        return user;
    }
}
