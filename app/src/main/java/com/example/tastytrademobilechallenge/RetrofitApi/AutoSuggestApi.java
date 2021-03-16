package com.example.tastytrademobilechallenge.RetrofitApi;

import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AutoSuggestApi {

    @GET("api/v3/search")
    Observable<List<SymbolAutocompleteModel>> getSymbolsBySearch(@Query("query") String symbol, @Query("limit") int limit, @Query("exchange") String exchange, @Query("apikey") String apiKey);
}
