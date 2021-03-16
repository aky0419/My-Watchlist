package com.example.tastytrademobilechallenge.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.DataBase.WatchListItemsDao;
import com.example.tastytrademobilechallenge.DataBase.WatchListItemsDatabase;
import com.example.tastytrademobilechallenge.Models.WatchListSymbolCrossRef;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListItemRepository {
    private WatchListItemsDao mWatchListItemsDao;
    private Single<List<WatchList>> watchLists;
    private LiveData<List<WatchListWithSymbols>> watchListsWithSymbols;
    final String[] DEFAULT_SYMBOLS = {"AAPL", "MSFT", "GOOG"};
    final String DEFAULT_LIST_NAME = "My first list";


    public WatchListItemRepository(Application application) {
        WatchListItemsDatabase database = WatchListItemsDatabase.getInstance(application);
        mWatchListItemsDao = database.mWatchListItemsDao();
        watchLists = mWatchListItemsDao.getAllWatchLists();
        watchListsWithSymbols = mWatchListItemsDao.getWatchListsWithSymbols();
    }

    public LiveData<WatchListWithSymbols> getSymbolsFromOneWatchList(String watchListName) {
        return mWatchListItemsDao.getSymbolsFromOneWatchList(watchListName);
    }


    public Completable insertWatchList(WatchList watchList) {
        return mWatchListItemsDao.insertWatchList(watchList);
    }


    public Completable insertSymbol(Symbol symbol) {
        return mWatchListItemsDao.insertSymbol(symbol).subscribeOn(Schedulers.io());
    }


    public Completable insertWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        return mWatchListItemsDao.insertWatchListSymbolCrossRef(crossRef).subscribeOn(Schedulers.io());
    }

    public LiveData<Symbol> getSymbol(String symbolName) {
        return mWatchListItemsDao.getSymbol(symbolName);
    }


    public Single<List<WatchList>> getAllWatchLists() {
        return watchLists;
    }

    public LiveData<List<WatchListWithSymbols>> getWatchListsWithSymbols() {
        return watchListsWithSymbols;
    }


    public Completable deleteWatchList(WatchList watchList) {
        return mWatchListItemsDao.deleteWatchList(watchList).subscribeOn(Schedulers.io());
    }


    public Completable deleteSymbol(Symbol symbol) {
        return mWatchListItemsDao.deleteSymbol(symbol).subscribeOn(Schedulers.io());
    }

    public void deleteWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        mWatchListItemsDao.deleteWatchListSymbolCrossRef(crossRef);
    }


    public Disposable addDefaultWatchListIfNotExist() {
        return watchLists.subscribeOn(Schedulers.io())
                .subscribe((watchLists) -> {
                    if (watchLists.size() == 0) {
                        WatchList watchList = new WatchList(DEFAULT_LIST_NAME);
                        this.insertWatchList(watchList).subscribe();
                        for (String s : DEFAULT_SYMBOLS) {
                            this.insertSymbol(new Symbol(s, 0, 0, 0)).subscribe();
                            this.insertWatchListSymbolCrossRef(new WatchListSymbolCrossRef(DEFAULT_LIST_NAME, s)).subscribe();
                        }
                    }
                });
    }

    public Disposable addNewWatchList(WatchList watchList) {
        return insertWatchList(watchList).subscribeOn(Schedulers.io()).subscribe();
    }

    public Completable updateSymbols(List<Symbol> symbols) {
        return mWatchListItemsDao.updateSymbols(symbols);
    }


}
