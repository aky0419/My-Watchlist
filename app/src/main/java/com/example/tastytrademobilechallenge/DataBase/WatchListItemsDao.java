package com.example.tastytrademobilechallenge.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.Models.WatchListSymbolCrossRef;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface WatchListItemsDao {

//    @Transaction
//    @Query("SELECT * FROM watchlists WHERE name = :watchListName LIMIT 1")
//    LiveData<WatchListWithSymbols> getSymbolsFromWatchList(String watchListName);

    @Transaction
    @Query("SELECT * FROM watchlists")
    LiveData<List<WatchListWithSymbols>> getWatchListsWithSymbols();

    @Transaction
    @Query("SELECT * FROM watchlists WHERE watchlistName = :watchListName")
    LiveData<WatchListWithSymbols> getSymbolsFromOneWatchList(String watchListName);

    @Query("SELECT * FROM watchlists")
    Single<List<WatchList>> getAllWatchLists();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable insertWatchList(WatchList watchList);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable insertSymbol(Symbol symbol);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    Completable insertWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef);

    @Delete
    void deleteWatchList(WatchList watchList);

    @Delete
    void deleteSymbol(Symbol symbol);

    @Delete
    void deleteWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef);

    @Update
    Completable updateSymbols(List<Symbol> symbols);

}