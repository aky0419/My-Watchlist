package com.example.tastytrademobilechallenge.Screens.watchlistDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.Models.WatchListSymbolCrossRef;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.Repositories.IEXApiRepository;
import com.example.tastytrademobilechallenge.Repositories.WatchListItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListItemViewModel extends AndroidViewModel {

    private final WatchListItemRepository mRepository;
    private final Single<List<WatchList>> allWatchList;
    private final LiveData<List<WatchListWithSymbols>> watchListsWithSymbols;
    private final IEXApiRepository mIEXApiRepository;
    public WatchListWithSymbols lastWatchListWithSymbols;


    public WatchListItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WatchListItemRepository(application);
        mIEXApiRepository = new IEXApiRepository();
        allWatchList = mRepository.getAllWatchLists();
        watchListsWithSymbols = mRepository.getWatchListsWithSymbols();
    }


    public LiveData<List<WatchListWithSymbols>> getWatchListsWithSymbols() {
        return watchListsWithSymbols;
    }

    Completable insertWatchList(WatchList watchList) {
        return mRepository.insertWatchList(watchList);
    }

    public LiveData<WatchListWithSymbols> getSymbolsFromOneWatchList(String watchListName) {
        return mRepository.getSymbolsFromOneWatchList(watchListName);
    }

    Single<List<WatchList>> getAllWatchLists() {
        return allWatchList;
    }


    public void insertSymbol(Symbol symbol) {
        mRepository.insertSymbol(symbol).subscribe();
    }


    public void insertWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        mRepository.insertWatchListSymbolCrossRef(crossRef).subscribe();
    }

    public void deleteWatchList(WatchListWithSymbols watchListWithSymbols) {
        mRepository.deleteWatchList(watchListWithSymbols.mWatchList).subscribe();
        mRepository.deleteWatchListSymbolCrossRefs(watchListWithSymbols.symbols
                .stream()
                .map(symbol -> new WatchListSymbolCrossRef(watchListWithSymbols.mWatchList.name, symbol.getSymbol()))
                .collect(Collectors.toList()))
                .subscribe();
    }


    public void deleteSymbol(Symbol symbol) {
        mRepository.deleteSymbol(symbol).subscribe();
    }

    public void deleteWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        mRepository.deleteWatchListSymbolCrossRef(crossRef).subscribe();
    }

    public Disposable addDefaultWatchListIfNotExist() {
        return mRepository.addDefaultWatchListIfNotExist();
    }

    public Disposable addNewWatchList(WatchList watchList) {
        return mRepository.addNewWatchList(watchList);
    }

    private String convertListToString() {
        if (this.lastWatchListWithSymbols == null) {
            return null;
        }
        return this.lastWatchListWithSymbols.symbols
                .stream()
                .map(Symbol::getSymbol)
                .collect(Collectors.joining(","));
    }

    public Disposable fetchAndUpdatePrice() {
        return mIEXApiRepository.getResponse(convertListToString())
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(new ArrayList<>())
                .subscribe(symbols -> mRepository.updateSymbols(symbols).subscribe());
    }


    public Disposable periodicallyFetchPrice() {
        return Observable.interval(5, TimeUnit.SECONDS)
                .doOnNext(a -> this.fetchAndUpdatePrice())
                .subscribe();
    }
}


