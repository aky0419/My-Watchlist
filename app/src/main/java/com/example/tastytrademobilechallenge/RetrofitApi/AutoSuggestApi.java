package com.example.tastytrademobilechallenge.RetrofitApi;

import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AutoSuggestApi {

    @GET("symbols/search/{fragment}")
    Observable<Map<String, Map<String, List<SymbolAutocompleteModel>>>> getSymbolsBySearch(@Path("fragment") String symbolFrag);
}
