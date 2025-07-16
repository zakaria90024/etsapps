package com.sasoftbd.ets.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    //public static String BASE_URL_SERVER = "http://192.168.1.63:8080/api/";

    public static String BASE_URL_SERVER = "http://192.168.1.216:8080/api/";
    //public static String BASE_URL_SERVER = "http://182.160.126.21:8082/api/";//Real Server

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitPublic() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SERVER)
                    .client(okClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_SERVER)
                    .client(okClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient okClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .build();
    }


//call with info
//    private static OkHttpClient okClient() {
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY); // Logs full request/response
//
//        return new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .connectTimeout(2, TimeUnit.MINUTES)
//                .writeTimeout(2, TimeUnit.MINUTES)
//                .readTimeout(2, TimeUnit.MINUTES)
//                .build();
//    }

}
