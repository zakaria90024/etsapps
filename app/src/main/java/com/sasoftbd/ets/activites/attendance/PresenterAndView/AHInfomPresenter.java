package com.sasoftbd.ets.activites.attendance.PresenterAndView;



import com.sasoftbd.ets.Auth.Login.User;
import com.sasoftbd.ets.latlong.APIService;
import com.sasoftbd.ets.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AHInfomPresenter {

    private AHInfoView mAHInfoView;
    private ApiClient mApiClient;
    public AHInfomPresenter(AHInfoView mAHInfoView) {
        this.mAHInfoView = mAHInfoView;
        if (this.mApiClient == null) {
            this.mApiClient = new ApiClient();

        }
    }


    public void getAhINfo() {


        APIService apiservice = ApiClient.getRetrofit().create(APIService.class);

        Call<List<User>> call = apiservice.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mAHInfoView.onAHinfoStatus(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mAHInfoView.onErrorAH(""+t.getMessage());
            }
        });


    }

}
