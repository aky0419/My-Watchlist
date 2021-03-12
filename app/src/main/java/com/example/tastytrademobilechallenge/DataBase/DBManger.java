package com.example.tastytrademobilechallenge.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManger {
    private static final String TAG = "DBManger";
    private static SQLiteDatabase database;

    public static void initDB(Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
    }

    /**
     * get watchLists
     */
    public static List<String> getWatchLists(){
        List<String> watchLists = new ArrayList<>();
        String sql = "select distinct(listName) from watchlisttb order by listName asc";
        Cursor cursor = database.rawQuery(sql,null);
        while(cursor.moveToNext()){
            String listName = cursor.getString(cursor.getColumnIndex("listName"));
            watchLists.add(listName);
        }
        return watchLists;
    }

    public static int getItemsCount(String listName){
        Cursor cursor = database.query("itemtb", null, "listName=?", new String[]{listName}, null, null, null);;
        return cursor.getCount();

    }

    /**
     * get items in a specific watchlist
     */

    public static List<String> getItemsList(String listName){
        List<String> itemsList= new ArrayList<>();
        Cursor cursor = database.query("itemtb", null, "listName=?", new String[]{listName}, null, null, null);;
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("symbol"));
            itemsList.add(name);
        }
        return itemsList;
    }

    public static void addNewWatchList(String listName) {
        ContentValues values = new ContentValues();
        values.put("listName", listName);
        database.insert("watchlisttb", null, values);
    }

    public static int deleteItemFromItemTb(String symbol){
        return database.delete("itemtb", "symbol=?", new String[]{symbol});
    }

    public static long addItem(String listName, String symbol){
        ContentValues values = new ContentValues();
        values.put("listName", listName);
        values.put("symbol", symbol);
        return database.insert("itemtb", null, values);
    }
}
