package com.example.tastytrademobilechallenge.RetrofitApi;

import android.util.Log;

import com.example.tastytrademobilechallenge.Models.StockPriceModel;
import com.example.tastytrademobilechallenge.RetrofitApi.IEXApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockPriceService {
    private static final String TAG = "StockPriceService";

    String IEXBaseUrl = "https://cloud.iexapis.com/";

    private IEXApi iexApi;

    StockPriceService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IEXBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        iexApi = retrofit.create(IEXApi.class);
    }

    public Observable<List<StockPriceModel>> getResponse(String symbols, String accessToken){
        Log.d(TAG, "getResponse: I am working every 5s");
        return iexApi.stockPriceResponse(symbols, accessToken).subscribeOn(Schedulers.io());
    }
}
