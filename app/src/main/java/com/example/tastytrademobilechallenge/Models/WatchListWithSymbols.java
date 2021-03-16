package com.example.tastytrademobilechallenge.Models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class WatchListWithSymbols {
    @Embedded
    public WatchList mWatchList;
    @Relation(
            parentColumn = "watchlistName",
            entityColumn = "symbolName",
            associateBy = @Junction(WatchListSymbolCrossRef.class)
    )
    public List<Symbol> symbols;

    public WatchListWithSymbols(WatchList watchList, List<Symbol> symbols) {
        mWatchList = watchList;
        this.symbols = symbols;
    }
}
