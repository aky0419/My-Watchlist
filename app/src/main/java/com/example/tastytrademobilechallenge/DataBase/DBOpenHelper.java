package com.example.tastytrademobilechallenge.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "watchList.db", null, 1);
    }

    /*
        String listName;
    String symbol;
    double bidPrice;
    double askPrice;
    double lastPrice;
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table stocktb(id integer primary key autoincrement, listName varchar(50), symbol varchar(5), bidPrice float, askPrice float, lastPrice float)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
