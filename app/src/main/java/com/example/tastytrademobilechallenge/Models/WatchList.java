package com.example.tastytrademobilechallenge.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "watchlists")
public class WatchList {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "watchlistName", index = true)
    public String name;

    public WatchList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
