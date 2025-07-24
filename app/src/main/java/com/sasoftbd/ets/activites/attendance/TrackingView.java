package com.sasoftbd.ets.activites.attendance;


import java.util.List;

public interface TrackingView {
    void onTrackingList(List<ResponseStatusModel> trackingListModel);
    void onErrorTourType(String error);
}
