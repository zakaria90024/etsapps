package com.sasoftbd.ets.latlong;
import com.google.gson.JsonObject;
import com.sasoftbd.ets.Auth.Login.LoginDetailsModel;
import com.sasoftbd.ets.Auth.Login.TokenModel;
import com.sasoftbd.ets.model.AttendanceModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
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




    //login 2 api ==================================================================================
    @POST("auth/login")
    Call<TokenModel> callWithUsernamePasswordGetLoginToken(@Body JsonObject data);


    @POST("auth/login-details")
    Call<LoginDetailsModel> calWithTokenGetLoginDetails(@Header("Authorization") String token, @Body JsonObject data);

    //login 2 api ==================================================================================


}