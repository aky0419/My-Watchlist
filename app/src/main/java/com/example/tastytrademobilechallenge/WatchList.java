package com.example.tastytrademobilechallenge;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


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
