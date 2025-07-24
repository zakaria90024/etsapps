package com.sasoftbd.ets.activites.attendance;



import com.sasoftbd.ets.latlong.APIService;
import com.sasoftbd.ets.network.ApiClient;

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


    public void getNoticeList(String date, String empCardNo) {


        APIService apiService = RetrofitClientSecond.getClient().create(APIService.class);
        Call<ResponseStatusModel> call = apiService.postLocationRead(date, empCardNo);
        call.enqueue(new Callback<ResponseStatusModel>() {
            @Override
            public void onResponse(Call<ResponseStatusModel> call, Response<ResponseStatusModel> response) {
                mTrackingListView.onTrackingList(response.body());
            }

            @Override
            public void onFailure(Call<ResponseStatusModel> call, Throwable t) {
                mTrackingListView.onErrorTourType(t.getMessage());
            }
        });

    }

}
