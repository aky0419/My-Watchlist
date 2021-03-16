package com.example.tastytrademobilechallenge.Screens.symbolTypeahead;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.Models.WatchListSymbolCrossRef;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.Repositories.AutoSuggestRepository;
import com.example.tastytrademobilechallenge.Repositories.IEXApiRepository;
import com.example.tastytrademobilechallenge.Repositories.WatchListItemRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class AddItemViewModel extends AndroidViewModel {

    public MutableLiveData<List<SymbolAutocompleteModel>> symbols;
    AutoSuggestRepository mAutoSuggestRepository;
    WatchListItemRepository mWatchListItemRepository;
    IEXApiRepository mIEXApiRepository;

    public AddItemViewModel(@NonNull Application application) {
        super(application);
        mAutoSuggestRepository = new AutoSuggestRepository();
        mIEXApiRepository = new IEXApiRepository();
        mWatchListItemRepository = new WatchListItemRepository(application);
        symbols = new MutableLiveData<>();
    }

//    public Disposable searchSymbols(String symbolsFrag) {
//        return mAutoSuggestRepository
//                .searchSymbols(symbolsFrag)
//                .subscribe(symbols::postValue);
//    }

    public void addTicker(Symbol symbol, String listName) {
        Completable insertTask = mWatchListItemRepository.insertSymbol(symbol);
        Completable updateRelationTask = mWatchListItemRepository
                .insertWatchListSymbolCrossRef(
                        new WatchListSymbolCrossRef(listName, symbol.getSymbol())
                );
        Completable.concatArray(insertTask, updateRelationTask).subscribe();
    }

    public Disposable searchSymbols(String searchTerm) {
        return mAutoSuggestRepository.getSymbolListFromSearch(searchTerm)
                .onErrorReturnItem(new ArrayList<>())
                .subscribe(symbols::postValue);
    }

    public LiveData<WatchListWithSymbols> getSymbolsFromOneWatchList(String watchListName) {
        return mWatchListItemRepository.getSymbolsFromOneWatchList(watchListName);
    }

}
