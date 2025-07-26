package com.sasoftbd.ets.activites.attendance.Tracking;


import com.sasoftbd.ets.activites.attendance.ResponseStatusModel;

import java.util.List;

public interface TrackingView {
    void onTrackingList(List<ResponseStatusModel> trackingListModel);
    void onErrorTourType(String error);
}
