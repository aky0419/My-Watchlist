package com.example.tastytrademobilechallenge.Repositories;

import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.RetrofitApi.AutoSuggestApi;

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
                .subscribeOn(Schedulers.io());
    }
}
