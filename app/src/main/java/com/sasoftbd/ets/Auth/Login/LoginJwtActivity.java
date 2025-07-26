package com.sasoftbd.ets.Auth.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.sasoftbd.ets.R;
import com.sasoftbd.ets.activites.attendance.AttendanceActivity;
import com.sasoftbd.ets.activites.attendance.Tracking.TrackingGoogleMapActivity;
import com.sasoftbd.ets.latlong.APIService;
import com.sasoftbd.ets.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginJwtActivity extends AppCompatActivity {

    SharedPreferences prefs;
    CheckBox checkBox;
    SharedPreferences sharedPreferenceslogin;
    APIService apiservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_jwt);

        prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
        sharedPreferenceslogin = getSharedPreferences("auth_prefs_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        SharedPreferences.Editor editors = sharedPreferenceslogin.edit();
        checkBox = findViewById(R.id.checkBox);

        apiservice = ApiClient.getRetrofit().create(APIService.class);


        // Auto login if token exists
        String savedToken = prefs.getString("access_token", null);
        if (savedToken != null) {
            startActivity(new Intent(this, AttendanceActivity.class));
            finish();
        }


        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(v -> {

            if (checkBox.isChecked()) {
                Intent intent = new Intent(LoginJwtActivity.this, TrackingGoogleMapActivity.class);
                startActivity(intent);
            } else {


                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                JsonObject requestData = new JsonObject();
                requestData.addProperty("username", "zakaria123456");
                requestData.addProperty("password", "12345678156");


                //requestData.addProperty("username", username);
                //requestData.addProperty("password", password);


                Log.d("hello ", "" + requestData);


                apiservice.callWithUsernamePasswordGetLoginToken(requestData).enqueue(new Callback<TokenModel>() {
                    @Override
                    public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            //Toast.makeText(LoginJwtActivity.this, "Success: " + response.body().getToken(), Toast.LENGTH_SHORT).show();

                            String token = response.body().getToken();
                            String refresh = response.body().getRefreshToken();

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("access_token", token);
                            editor.putString("refresh_token", refresh);
                            editor.apply();

//


//                        editor.putString("auth_token", response.body().getToken());  // token comes from login response
//                        editor.apply();

                            // Get token from SharedPreferences
                            //SharedPreferences prefss = getSharedPreferences("auth_prefs", MODE_PRIVATE);
                            //String token = prefss.getString("auth_token", null);

                            callWithToken(response.body().getToken(), requestData, apiservice);

                        } else {
                            Toast.makeText(LoginJwtActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenModel> call, Throwable t) {
                        Toast.makeText(LoginJwtActivity.this, "Fail: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void callWithToken(String token, JsonObject requestData, APIService apiservice) {


        if (token != null) {
            String bearerToken = "Bearer " + token;

            apiservice.calWithTokenGetLoginDetails(bearerToken, requestData).enqueue(new Callback<LoginDetailsModel>() {
                @Override
                public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                    if (response.isSuccessful()) {
                        // Handle success
                        //Toast.makeText(getApplicationContext(), "Details loaded", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editors = sharedPreferenceslogin.edit();

                        editors.putString("userID", String.valueOf(response.body().getUser().getId()));
                        editors.putString("userType", response.body().getUser().getRole());
                        editors.putString("UserName", response.body().getUser().getUsername());
                        //editor.putString("branch_code", staticBranchcode);
                        editors.putString("access_token", response.body().getToken());
                        editors.putString("refresh_token", response.body().getRefreshToken());
                        editors.putString("role", response.body().getUser().getRole());
                        editors.putString("CardNo", response.body().getUser().getCardNo());
                        editors.putString("Picture", response.body().getUser().getImage());
                        editors.apply();
                        //editor.putString("Details", userInfos.getStrTeritorryName() + "|" + userInfos.getStrArea() + "|" + userInfos.getStrDivision() + "|" + userInfos.getStrTeam() + "|" + userInfos.getStrZone());


                        Toast.makeText(LoginJwtActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginJwtActivity.this, AttendanceActivity.class));
                        finish();

                    } else {

                        Toast.makeText(LoginJwtActivity.this, "Token expired!", Toast.LENGTH_SHORT).show();
                        prefs.edit().clear().apply();
                        startActivity(new Intent(LoginJwtActivity.this, LoginJwtActivity.class));
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<LoginDetailsModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        } else {
            Toast.makeText(this, "Token not found", Toast.LENGTH_SHORT).show();
        }

    }


}