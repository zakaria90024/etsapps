package com.sasoftbd.ets.Auth.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TokenInterceptor implements Interceptor {
    private SharedPreferences prefs;
    private Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = prefs.getString("access_token", null);

        Request.Builder builder = originalRequest.newBuilder();
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }

        Response response = chain.proceed(builder.build());

        // If token expired
        if (response.code() == 401) {
            synchronized (this) {
                String refreshToken = prefs.getString("refresh_token", null);
                if (refreshToken != null) {
                    String newAccessToken = refreshAccessToken(refreshToken);
                    if (newAccessToken != null) {
                        prefs.edit().putString("access_token", newAccessToken).apply();

                        // Retry the request with new token
                        Request newRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer " + newAccessToken)
                                .build();
                        return chain.proceed(newRequest);
                    } else {
                        // Refresh token invalid → logout
                        prefs.edit().clear().apply();
                        Intent intent = new Intent(context, LoginJwtActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }
            }
        }

        return response;
    }

    private String refreshAccessToken(String refreshToken) {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"refreshToken\":\"" + refreshToken + "\"}");
            Request request = new Request.Builder()
                    .url("http://35.192.59.236:9191/api/auth/refresh")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String json = response.body().string();
                JSONObject obj = new JSONObject(json);
                return obj.getString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
