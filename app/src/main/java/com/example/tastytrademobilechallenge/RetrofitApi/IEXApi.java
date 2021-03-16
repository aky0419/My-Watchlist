package com.example.tastytrademobilechallenge.RetrofitApi;

import com.example.tastytrademobilechallenge.Models.HistoricalDataModel;
import com.example.tastytrademobilechallenge.Models.QuoteModel;
import com.example.tastytrademobilechallenge.Models.StockPriceModel;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//pk_c3ce2b10dc92443a8eb298e501c2121a
public interface IEXApi {

    @GET("stable/tops")
    Observable<List<StockPriceModel>> stockPriceResponse(@Query("symbols") String symbols, @Query("token") String accessToken);



//    @GET("stable/search/{fragment}")
//    Observable<List<SymbolAutocompleteModel>> getSymbolsBySearch(@Path("fragment") String symbolFrag, @Query("token") String accessToken);

    @GET("stable/stock/market/batch")
    Observable<Map<String, Map<String, QuoteModel>>> getMarketQuotes(@Query("types") String types, @Query("symbols") String symbols, @Query("token") String accessToken);

    @GET("stable/stock/{symbol}/chart/{range}")
    Observable<List<HistoricalDataModel>> getLast30DaysQuotes(@Path("symbol") String symbol, @Path("range") String range, @Query("token") String accessToken);

}
