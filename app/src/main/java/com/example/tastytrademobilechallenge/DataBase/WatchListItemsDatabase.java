package com.example.tastytrademobilechallenge.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.Models.WatchListSymbolCrossRef;

// can add more entity in curly brackets
// setup database
@Database(entities = {WatchList.class, Symbol.class, WatchListSymbolCrossRef.class}, version = 1, exportSchema = false)
public abstract class WatchListItemsDatabase extends RoomDatabase {
    private static WatchListItemsDatabase instance;

    public abstract WatchListItemsDao mWatchListItemsDao();

    public static synchronized WatchListItemsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WatchListItemsDatabase.class, "watch_list_items_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
