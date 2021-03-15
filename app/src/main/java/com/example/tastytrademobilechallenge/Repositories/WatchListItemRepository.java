package com.example.tastytrademobilechallenge.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tastytrademobilechallenge.Symbol;
import com.example.tastytrademobilechallenge.WatchList;
import com.example.tastytrademobilechallenge.WatchListItemsDao;
import com.example.tastytrademobilechallenge.WatchListItemsDatabase;
import com.example.tastytrademobilechallenge.WatchListSymbolCrossRef;
import com.example.tastytrademobilechallenge.WatchListWithSymbols;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListItemRepository {
    private WatchListItemsDao mWatchListItemsDao;
    private Single<List<WatchList>> watchLists;
    private LiveData<List<WatchListWithSymbols>> watchListsWithSymbols;
    final String[] defaultSymbols = {"AAPL", "MSFT", "GOOG"};


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


    public Single<List<WatchList>> getAllWatchLists() {
        return watchLists;
    }

    public LiveData<List<WatchListWithSymbols>> getWatchListsWithSymbols() {
        return watchListsWithSymbols;
    }


    public void deleteWatchList(WatchList watchList) {
        mWatchListItemsDao.deleteWatchList(watchList);
    }


    public void deleteSymbol(Symbol symbol) {
        mWatchListItemsDao.deleteSymbol(symbol);
    }

    public void deleteWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        mWatchListItemsDao.deleteWatchListSymbolCrossRef(crossRef);
    }


    public Disposable addDefaultWatchListIfNotExist() {
        return watchLists.subscribeOn(Schedulers.io())
                .subscribe((watchLists) -> {
                    if (watchLists.size() == 0) {
                        WatchList watchList = new WatchList("My first list");
                        this.insertWatchList(watchList).subscribe();
                        for (String s : defaultSymbols) {
                            this.insertSymbol(new Symbol(s, 0, 0, 0)).subscribe();
                            this.insertWatchListSymbolCrossRef(new WatchListSymbolCrossRef("My first list", s)).subscribe();
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
