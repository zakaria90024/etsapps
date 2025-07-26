package com.sasoftbd.ets.activites.attendance.Tracking;



import com.sasoftbd.ets.activites.attendance.ResponseStatusModel;
import com.sasoftbd.ets.latlong.APIService;
import com.sasoftbd.ets.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackingPresenter {


    private TrackingView mTrackingListView;
    private ApiClient mApiClient;

    public TrackingPresenter(TrackingView mTrackingListView) {
        this.mTrackingListView = mTrackingListView;
        if (this.mApiClient == null) {
            this.mApiClient = new ApiClient();
        }
    }


    public void getLocationUseWiseList(String date, String empCardNo) {

        APIService apiService = ApiClient.getRetrofit().create(APIService.class);
        Call<List<ResponseStatusModel>> call = apiService.postLocationRead(date, empCardNo);
        call.enqueue(new Callback<List<ResponseStatusModel>>() {
            @Override
            public void onResponse(Call<List<ResponseStatusModel>> call, Response<List<ResponseStatusModel>> response) {
                mTrackingListView.onTrackingList(response.body());
            }

            @Override
            public void onFailure(Call<List<ResponseStatusModel>> call, Throwable t) {
                mTrackingListView.onErrorTourType(t.getMessage());
            }
        });

    }

}
