package com.msbilgin.sqlkolayexample;

import android.content.ContentValues;
import android.database.Cursor;

import com.msbilgin.sqlkolay.Column;
import com.msbilgin.sqlkolay.Table;

import java.util.ArrayList;
import java.util.List;

public class TableBooks extends Table {
    private static final String tableName = "BOOKS";

    @Column(type = Column.Type.INTEGER_AUTOINC, primary = true)
    private Column id;

    private Column name;
    private Column author;

    @Column(type = Column.Type.NUMBER)
    private Column date;

    @Column(type = Column.Type.REAL)
    private Column price;

    public TableBooks() {
        super(tableName);
    }

    public void add(Book book) {
        ContentValues values = new ContentValues();
        values.put("name", book.name);
        values.put("author", book.author);
        values.put("date", book.publishDate);
        values.put("price", book.price);
        insert(null, values);
    }

    public Book getByID(int id) {
        Book book = new Book();

        String sql = String.format("select * from %s where id=%s", tableName, id);
        Cursor cursor = getDB().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            book.id = cursor.getInt(cursor.getColumnIndex("id"));
            book.name = cursor.getString(cursor.getColumnIndex("name"));
            book.author = cursor.getString(cursor.getColumnIndex("author"));
            book.price = cursor.getDouble(cursor.getColumnIndex("price"));
            book.publishDate = cursor.getString(cursor.getColumnIndex("date"));
        }
        return book;
    }

    public List<Book> getByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "select * from " + tableName + " where author like '%" + author + "%'";

        Cursor cursor = getDB().rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.id = cursor.getInt(cursor.getColumnIndex("id"));
                book.name = cursor.getString(cursor.getColumnIndex("name"));
                book.author = cursor.getString(cursor.getColumnIndex("author"));
                book.price = cursor.getDouble(cursor.getColumnIndex("price"));
                book.publishDate = cursor.getString(cursor.getColumnIndex("date"));
                books.add(book);
            } while (cursor.moveToNext());
        }
        return books;
    }

    public void remove(int id) {
        delete("id=" + id, null);
    }
}
