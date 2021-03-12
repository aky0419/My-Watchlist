package com.example.tastytrademobilechallenge;

import android.app.Application;

import com.example.tastytrademobilechallenge.DataBase.DBManger;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManger.initDB(getApplicationContext());
    }
}
