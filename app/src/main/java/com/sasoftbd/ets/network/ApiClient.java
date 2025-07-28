package com.sasoftbd.ets.network;


import android.content.Context;

import com.sasoftbd.ets.Auth.Login.TokenInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    //public static String BASE_URL_SERVER = "http://192.168.1.63:8080/api/";

    //public static String BASE_URL_SERVER = "http://192.168.1.216:8080/api/";
    //public static final String BASE_URL_SERVER = "http://192.168.115.1:8080/api/";


    //ngrok http 8080
    //ngrok config add-authtoken 308QBurqWEW9kuBcsr8XMoxbEXQ_7ierBXaRLw4o2bPQgMANG
    public static final String BASE_URL_SERVER = "https://adffc615a922.ngrok-free.app/api/";
    //public static final String BASE_URL_SERVER = "http://35.192.59.236:9191/api/";



    //public static String BASE_URL_SERVER = "http://35.192.59.236:9191/api/";



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



    public static Retrofit getRetrofit(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor(context))
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
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
