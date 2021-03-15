package com.example.tastytrademobilechallenge.Models;

import android.content.ClipData;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity (primaryKeys = {"watchlistName", "symbolName"}, indices = {@Index("watchlistName"), @Index("symbolName")})
public class WatchListSymbolCrossRef {
    public WatchListSymbolCrossRef(String watchlistName, @NonNull String symbolName) {
        this.watchlistName = watchlistName;
        this.symbolName = symbolName;
    }
    @NonNull
    public String watchlistName;

    @NonNull
    public String symbolName;
}
