package com.example.tastytrademobilechallenge;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tastytrademobilechallenge.Models.HistoricalDataModel;
import com.example.tastytrademobilechallenge.Repositories.IEXApiRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HistoryGraphViewModel extends AndroidViewModel {

    IEXApiRepository mIEXApiRepository;
    MutableLiveData<List<HistoricalDataModel>> historicalDataList;

    public HistoryGraphViewModel(@NonNull Application application) {
        super(application);
        mIEXApiRepository = new IEXApiRepository();
        historicalDataList = new MutableLiveData<>();
    }

    public Disposable getHistoricalDataList(String symbol) {
        return mIEXApiRepository
                .getHistoricalDataList(symbol)
                .onErrorResumeNext(throwable -> {
                    Log.d("HistoryGraphhViewModel", throwable.getLocalizedMessage());
                    return Observable.error(throwable);
                })
                .subscribe();
    }
}
