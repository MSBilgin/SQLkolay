package com.msbilgin.sqlkolayexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDB appDB = new AppDB(getApplicationContext());
        User user1 = new User(111,"Mehmet Selim", "Bilgin", 29,100000);
        appDB.user.add(user1);
        Log.d("TEST", String.valueOf(appDB.user.get(111).calc()));
    }
}
