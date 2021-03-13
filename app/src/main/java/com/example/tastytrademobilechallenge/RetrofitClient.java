//package com.example.tastytrademobilechallenge;
//
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClient {
//    private static Retrofit retrofitInstance;
//
//    public static Retrofit getInstance(){
//        if (retrofitInstance == null){
//            retrofitInstance = new Retrofit.Builder()
//                    .baseUrl("https://cloud.iexapis.com/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .build();
//
//        }
//        return retrofitInstance;
//    }
//
//    private RetrofitClient(){};
//}
