package com.example.tastytrademobilechallenge.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.function.ObjIntConsumer;

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "watchList.db", null, 1);
    }

    /*
    String listName;
    String symbol;
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table watchlisttb (id integer primary key autoincrement, listName varchar(50))";
        sqLiteDatabase.execSQL(sql);
        insertItemToWatchListTb(sqLiteDatabase);

        String itemtb_sql = "create table itemtb (id integer primary key autoincrement, listName varchar(50), symbol varchar(10))";
        sqLiteDatabase.execSQL(itemtb_sql);
        insertItemToItemTb(sqLiteDatabase);
    }

    private void insertItemToWatchListTb(SQLiteDatabase sqLiteDatabase) {
        String sql = "insert into watchlisttb(listName) values(?)";
        sqLiteDatabase.execSQL(sql, new Object[]{"My first list"});
    }

    private void insertItemToItemTb(SQLiteDatabase sqLiteDatabase) {
        String sql = "insert into itemtb (listName, symbol) values(?,?)";
        sqLiteDatabase.execSQL(sql, new Object[]{"My first list", "APPL"});
        sqLiteDatabase.execSQL(sql, new Object[]{"My first list", "MSFT"});
        sqLiteDatabase.execSQL(sql, new Object[]{"My first list", "GOOG"});
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
