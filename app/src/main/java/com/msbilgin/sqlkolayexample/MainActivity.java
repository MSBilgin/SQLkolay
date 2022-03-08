package com.msbilgin.sqlkolayexample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //add a book
        AppDB appDB = AppDB.getInstance(getApplicationContext());
        Book book = new Book("Seyahatname", "Evliya Çelebi", "01.01.1600", 2000);
        appDB.books.add(book);

        //query
        Log.d("TOTAL BOOKS", String.valueOf(appDB.books.getByAuthor("Evliya").size()));


    }
}
