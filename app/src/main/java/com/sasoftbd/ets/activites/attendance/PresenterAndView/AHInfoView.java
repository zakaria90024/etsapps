package com.sasoftbd.ets.activites.attendance.PresenterAndView;

import com.sasoftbd.ets.Auth.Login.User;

import java.util.List;

public interface AHInfoView {
    void onAHinfoStatus(List<User> allAHinfoList);
    void onErrorAH(String error);
}
