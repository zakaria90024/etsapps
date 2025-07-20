package com.sasoftbd.ets.latlong;
import com.google.gson.JsonObject;
import com.sasoftbd.ets.model.AttendanceModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface APIService {


    //ShfitConfig/mPostLocations
    @POST("location/insert")
    Call<String> postLocation(@Body JsonObject jsonObject);

    @POST("ShfitConfig/mPostForGetStatusLeaveAttendance")
    Call<String> getStatusLeaveAttendance(@Body JsonObject data);


    @POST("ShfitConfig/mGetUserReturnVal")
    Call<List<AttendanceModel>> getButtonStatus(@Body JsonObject data);

    //ShfitConfig/SaveAttendance
    @POST("attendance/insert")
    Call<String> getAttendanceDataSubmit(@Body JsonObject data);


}