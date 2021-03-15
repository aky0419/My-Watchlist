package com.example.tastytrademobilechallenge.Repositories;

import android.nfc.Tag;
import android.util.Log;

import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.RetrofitApi.AutoSuggestApi;
import com.example.tastytrademobilechallenge.RetrofitApi.IEXApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AutoSuggestRepository {

    final private String IEXBaseUrl = "https://financialmodelingprep.com/";
    private AutoSuggestApi mAutoSuggestApi;
    private static final String TAG = "AutosuggestRepo";

    public AutoSuggestRepository() {
        OkHttpClient.Builder okhttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        okhttpClient.addInterceptor(logging);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IEXBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okhttpClient.build())
                .build();

        mAutoSuggestApi = retrofit.create(AutoSuggestApi.class);
    }

    public Observable<List<SymbolAutocompleteModel>> searchSymbols(String symbol) {
        return mAutoSuggestApi
                .getSymbolsBySearch(symbol, 10, "NASDAQ", "demo")
                .map(symbolAutocompleteModels -> {
                    Log.d(TAG, "response array size -> " + symbolAutocompleteModels.size());
                    return symbolAutocompleteModels;
                })
                .subscribeOn(Schedulers.io());
    }
}
