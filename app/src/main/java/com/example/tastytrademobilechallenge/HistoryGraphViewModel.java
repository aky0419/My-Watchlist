package com.example.tastytrademobilechallenge;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tastytrademobilechallenge.Models.HistoricalDataModel;
import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.Repositories.IEXApiRepository;
import com.example.tastytrademobilechallenge.Repositories.WatchListItemRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryGraphViewModel extends AndroidViewModel {

    IEXApiRepository mIEXApiRepository;
    MutableLiveData<List<HistoricalDataModel>> historicalDataList;
    MutableLiveData<Symbol> symbol;
    WatchListItemRepository mWatchListItemRepository;

    public HistoryGraphViewModel(@NonNull Application application) {
        super(application);
        mIEXApiRepository = new IEXApiRepository();
        historicalDataList = new MutableLiveData<>();
        symbol = new MutableLiveData<>();
        mWatchListItemRepository = new WatchListItemRepository(application);

    }

    public Disposable getHistoricalDataList(String symbol) {
        return mIEXApiRepository
                .getHistoricalDataList(symbol)
                .onErrorResumeNext(throwable -> {
                    Log.d("HistoryGraphViewModel", throwable.getLocalizedMessage());
                    return Observable.error(throwable);
                })
                .subscribe(historicalDataList :: postValue);
    }

    public LiveData<Symbol> getSymbol(String symbolName) {
        return mWatchListItemRepository.getSymbol(symbolName);
    }

}
