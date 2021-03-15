package com.example.tastytrademobilechallenge.Repositories;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.tastytrademobilechallenge.Models.QuoteModel;
import com.example.tastytrademobilechallenge.Models.StockPriceModel;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.RetrofitApi.IEXApi;
import com.example.tastytrademobilechallenge.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IEXApiRepository {

    private static final String TAG = "StockPriceService";

    final private String IEXBaseUrl = "https://cloud.iexapis.com/";
    final private String accessToken = "pk_c3ce2b10dc92443a8eb298e501c2121a";

    private IEXApi iexApi;

    public IEXApiRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IEXBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        iexApi = retrofit.create(IEXApi.class);
    }

    public Observable<List<Symbol>> getResponse(String symbols) {
        Log.d(TAG, "getResponse: I am working every 5s -> " + symbols);
        if (symbols == null || symbols.trim().isEmpty()) {
            return Observable.just(new ArrayList<>());
        }
        return iexApi
                .getMarketQuotes("quote", symbols, accessToken)
                .map(symbol -> {
                    Log.d(TAG, "GET symbol count -> " + symbol.size());
                    return symbol;
                })
                .map(stringQuoteModelMap -> {
                    List<Symbol> next = new ArrayList<>();
                    for (Map.Entry<String, Map<String, QuoteModel>> entry : stringQuoteModelMap.entrySet()) {
                        for (Map.Entry<String, QuoteModel> inner : entry.getValue().entrySet()) {
                            QuoteModel value = inner.getValue();
                            try {
                                next.add(new Symbol(value.getSymbol(),
                                        value.getIexOpen(),
                                        value.getIexClose(),
                                        value.getLatestPrice()));
                            } catch (RuntimeException error) {
                                Log.d(TAG, error.getLocalizedMessage());
                            }
                        }

                    }
                    return next;
                })
                .subscribeOn(Schedulers.io());
    }





}
