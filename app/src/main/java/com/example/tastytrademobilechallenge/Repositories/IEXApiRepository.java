package com.example.tastytrademobilechallenge.Repositories;

import android.util.Log;

import com.example.tastytrademobilechallenge.Models.HistoricalDataModel;
import com.example.tastytrademobilechallenge.Models.QuoteModel;
import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.RetrofitApi.IEXApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IEXApiRepository {

    private static final String TAG = "IEXApiRepository";

    final private String IEXBaseUrl = "https://sandbox.iexapis.com/";
    final private String accessToken = "Tsk_1e9768b480e147ffa41115f55eed9f2f";
    List<HistoricalDataModel> mHistoricalDataModelList;

    private IEXApi iexApi;

    public IEXApiRepository() {
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

        iexApi = retrofit.create(IEXApi.class);
        mHistoricalDataModelList = new ArrayList<>();
    }

//    public Observable<List<Symbol>> stockPriceResponse(String symbols){
//        if (symbols == null || symbols.trim().isEmpty()) {
//            return Observable.just(new ArrayList<>());
//        }
//        return iexApi.stockPriceResponse(symbols, accessToken)
//                .map(stockPriceModels -> {
//                    List<Symbol> symbolList = new ArrayList<>();
//                    for (StockPriceModel stockPriceModel : stockPriceModels){
//                        try {
//                            symbolList.add(new Symbol(stockPriceModel.getSymbol(),
//                                    stockPriceModel.getBidPrice(),
//                                    stockPriceModel.getAskPrice(),
//                                    stockPriceModel.getLastSalePrice()));
//                        } catch (RuntimeException error) {
//                            Log.d(TAG, error.getLocalizedMessage());
//                        }
//                    }
//                    return symbolList;
//
//                })
//                .subscribeOn(Schedulers.io());
//    }


    public Observable<List<Symbol>> getResponse(String symbols) {
        Log.d(TAG, "getResponse: I am working every 5s -> " + symbols);
        if (symbols == null || symbols.trim().isEmpty()) {
            return Observable.just(new ArrayList<>());
        }
        return iexApi
                .getMarketQuotes("quote", symbols, accessToken)
                .map(stringQuoteModelMap -> {
                    List<Symbol> next = new ArrayList<>();
                    for (Map.Entry<String, Map<String, QuoteModel>> entry : stringQuoteModelMap.entrySet()) {
                        for (Map.Entry<String, QuoteModel> inner : entry.getValue().entrySet()) {
                            QuoteModel value = inner.getValue();
                            try {
                                next.add(new Symbol(value.getSymbol(),
                                        value.getIexBidPrice(),
                                        value.getIexAskPrice(),
                                        value.getLatestPrice(),
                                        value.getOpen()
                                ));
                            } catch (RuntimeException error) {
                                Log.d(TAG, error.getLocalizedMessage());
                            }
                        }

                    }
                    return next;
                })
                .doOnError(throwable -> Log.d(TAG, throwable.getLocalizedMessage()))
                .subscribeOn(Schedulers.io());
    }


    public Observable<List<HistoricalDataModel>> getHistoricalDataList(String symbol) {
        return iexApi
                .getLast30DaysQuotes(symbol, "30d", accessToken)
                .doOnSubscribe(disposable -> Log.d(TAG, "subscribing"))
                .doOnComplete(() -> Log.d(TAG, "completed"))
                .doOnError(throwable -> Log.d(TAG, throwable.getLocalizedMessage()))
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<SymbolAutocompleteModel>> getSymbolListFromSearch(String searchTerm) {
        return iexApi.getSymbolsBySearch(searchTerm, accessToken)
                .subscribeOn(Schedulers.io());
    }


}
