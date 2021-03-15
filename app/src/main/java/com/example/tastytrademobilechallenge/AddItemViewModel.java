package com.example.tastytrademobilechallenge;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.Repositories.AutoSuggestRepository;
import com.example.tastytrademobilechallenge.Repositories.WatchListItemRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;

public class AddItemViewModel extends AndroidViewModel {

    public MutableLiveData<List<SymbolAutocompleteModel>> symbols;
    AutoSuggestRepository mAutoSuggestRepository;
    WatchListItemRepository mWatchListItemRepository;

    public AddItemViewModel(@NonNull Application application) {
        super(application);
        mAutoSuggestRepository = new AutoSuggestRepository();
        mWatchListItemRepository = new WatchListItemRepository(application);
        symbols = new MutableLiveData<>();
    }

    public Disposable searchSymbols(String symbolsFrag) {
        return mAutoSuggestRepository
                .searchSymbols(symbolsFrag)
                .subscribe(symbols::postValue);
    }

    public void addTicket(Symbol symbol, String listName) {
        Completable insertTask = mWatchListItemRepository.insertSymbol(symbol);
        Completable updateRelationTask = mWatchListItemRepository.insertWatchListSymbolCrossRef(new WatchListSymbolCrossRef(listName, symbol.getSymbol()));
        Completable.concatArray(insertTask,updateRelationTask).subscribe();
    }
}
