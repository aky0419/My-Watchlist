package com.example.tastytrademobilechallenge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tastytrademobilechallenge.Repositories.IEXApiRepository;
import com.example.tastytrademobilechallenge.Repositories.WatchListItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class WatchListItemViewModel extends AndroidViewModel {

    private WatchListItemRepository mRepository;
    private Single<List<WatchList>> allWatchList;
    private LiveData<List<WatchListWithSymbols>> watchListsWithSymbols;
    private IEXApiRepository mIEXApiRepository;
    public WatchListWithSymbols lastWatchListWithSymbols;


    public WatchListItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WatchListItemRepository(application);
        mIEXApiRepository = new IEXApiRepository();
        allWatchList = mRepository.getAllWatchLists();
        watchListsWithSymbols = mRepository.getWatchListsWithSymbols();
    }

    public LiveData<WatchListWithSymbols> getSymbolsFromOneWatchList(String watchListName) {
        return mRepository.getSymbolsFromOneWatchList(watchListName);
    }

    public LiveData<List<WatchListWithSymbols>> getWatchListsWithSymbols() {
        return watchListsWithSymbols;
    }

//    Single<Long> insertWatchList(WatchList watchList) {
//        return mRepository.insertWatchList(watchList);
//    }

    Completable insertWatchList(WatchList watchList) {
        return mRepository.insertWatchList(watchList);
    }

    Single<List<WatchList>> getAllWatchLists() {
        return allWatchList;
    }


    void insertSymbol(Symbol symbol) {
        mRepository.insertSymbol(symbol);
    }


    void insertWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        mRepository.insertWatchListSymbolCrossRef(crossRef);
    }

    void deleteWatchList(WatchList watchList) {
        mRepository.deleteWatchList(watchList);
    }


    void deleteSymbol(Symbol symbol) {
        mRepository.deleteSymbol(symbol);
    }

    void deleteWatchListSymbolCrossRef(WatchListSymbolCrossRef crossRef) {
        mRepository.deleteWatchListSymbolCrossRef(crossRef);
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

    public Disposable periodicallyFetchPrice(String listName) {
        return Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap((Function<Long, Observable<List<Symbol>>>) aLong -> {
                            if (lastWatchListWithSymbols == null) return Observable.empty();
                            return mIEXApiRepository.getResponse(convertListToString());
                        }
                )
                .subscribe(symbols -> mRepository.updateSymbols(symbols).subscribe());
    }


}


