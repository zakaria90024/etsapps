package com.sasoftbd.ets.latlong;




import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIService {


//    //Attendance Service API========================================
//    @GET
//    Call<List<AttendanceShiftModel>> getAttendance(@Url String url);
//
//
//    @FormUrlEncoded
//    @POST("OTPVerification/Post")
//    Call<String> getOtp(@Field("Mobile") String mobileNo,
//                        @Field("Token") String tokenId);
//
//
//    @FormUrlEncoded
//    @POST("smsapiverify")
//        //http://192.168.1.83:8080/api/smsapiverify/Post
//    Call<String> sendMobileNumber(@Field("mobile") String mobile);
//
//
//    @POST("verify ")
//        //http://192.168.1.83:8080/api/SMSAPI/Post
//    Call<String> submitVerify(@Body JsonObject json
//    );
//
//
//    @FormUrlEncoded
//    @POST("CheckStatus/Post")
//    Call<String> checkUserStatus(@Field("strLedgerName") String strLedgerName);
//
//    @GET
//    Call<List<StockGroup>> getGroup(@Url String url);
//
//    @GET
//    Call<List<PrescriptionModel>> getPrescriptinLIst(@Url String url);
//
//
//    @GET("StockItem/{strItemName}")
//    Call<List<StockItemModel>> getItem(@Path("strItemName") String strItemName);
//
//    @GET("mLoadItemRate")
//    Call<List<StockItemWithPriceModel>> getItemList();
//
//    @GET
//    Call<List<StockItemWithPriceModel>> getItemList(@Url String url);
//
//
//    @GET("Commissionslab")
//    Call<List<CommissionSlabModel>> getCommissionslab();
//
//
//    @GET("mLoadItemRate/mGetItemCommitionPercent")
//    Call<List<CommissionSlabModel>> getCommissionslabForDist();
//
//
//    @GET("GetCustomer/{id}")
//    Call<List<DoctorModel>> getDoctor(@Path("id") String id);
//
//    @GET("MPO/GetDistributorListforMPO")
//    Call<List<DoctorModel>> getDistributorListForMpo(@Query("id") String id);
//
//
//    @POST("CustomerUpdateStatus/Update/")
//    Call<String> submitActiveDoctorList(@Body JsonObject json);
//
//    @GET("/posts")
//    Call<List<Todo>> getTodos();
//
//    @GET("GetMPOName/{id}")
//    Call<List<LoginModel>> getUseNo(@Path("id") long id);
//
//    @POST("submitOrderList")
//    Call<List<DraftItemInfoModel>> submitOrderLists();
//
//
//    @POST("SalesOrderInsert/Save")
//    Call<String> submitOrderList(@Body JsonObject json
//    );
//
//    @POST(" ApprovedSalesOrder/Approved")
//    Call<String> submitApprovedList(@Body List<Summary> json
//    );
//
//    @POST("Mct/Approved")
//    Call<String> submitApprovedListDist(@Body List<Summary> json);
//
//
//    @POST("GetNotification/Post")
//    Call<List<OrderNotification>> getNotification(@Body JsonObject json);
//
//
//
//    @GET
//    Call<List<OrderNotification>> getNotificationDist(@Url String url);
//
//
//
//    @POST("ChangeSalesOrder/Post")
//    Call<SubmitOrderModel> getUpdateApproveOrderList(@Body JsonObject json
//    );
//
//
//    @POST("NotificationUpdate/Post")
//    Call<String> notificationUpdate(@Body JsonObject json   //    /not working
//    );
//
//
//    @Headers("Content-Type: application/json")
//    @POST("SalesOrderInsert/Save")
//    Call<String> getDataBytMac(@Body JsonObject data);
//
//    @FormUrlEncoded
//    @POST("MPO/post")
//    Call<LoginModel> login(@Field("UserID") String userID,
//                           @Field("Password") String password,
//                           @Field("admin") String admin,
//                           @Field("branchid") String branchid,
//                           @Field("empcardNo") String empcardNo);
//
//
//    @GET("MPOArea/{id}")
//    Call<List<MpoModel>> getMpo(@Path("id") long id);
//
//
//    @GET("GetDivision/{id}")
//    Call<List<MpoModel>> getMpofromDivision(@Path("id") long id);
//
//
//    @POST("DisplaySalesOrder/Post/")
//    Call<SubmitOrderModel> getMpoOrderList(@Body JsonObject data);
//
//
//    @GET
//    Call<ResponseBody> downlload(@Path("itemName") String id);
//
//    @GET("Website")
//    Call<List<Weblink>> getWeblink();
//
//
//    @POST("GetPDF")
//    Call<List<StudyModel>> getBytePdf(@Body JsonObject json);
//
//    @POST("PDFgroup")
//    Call<List<StockGroup>> getpafGroup(@Body JsonObject json);
//
//    @POST("PDFitem")
//    Call<List<StockItem>> getpafItem(@Body JsonObject json);
//
//    //for Division, District, Thana, Upazilla
//    @GET("HRMData/GetHrmDIV")
//    Call<List<DivisionModel>> getDivision();
//
//    @POST
//    Call<List<DistrictModel>> getDistrict(@Url String url);
//
//    @POST
//    Call<List<UpazilaModel>> getUpazilla(@Url String url);
//
//    @POST
//    Call<List<UnionModel>> getUnion(@Url String url);
//
//
//    @POST
//    Call<DashboardMasterModel> getDashboardDataFull(@Url String url);
//
//    @GET
//    Call<List<Groups>> getSelectionGroup(@Url String url);
//
//    @FormUrlEncoded
//    @POST("InvoiceList/Invoice")
//    Call<List<InvoiceModel>> getInvoicedate(@Field("strMerzeName") String strMerzeName,
//                                            @Field("inttype") String inttype);
//
//    @FormUrlEncoded
//    @POST("InvoiceList/challan")
//    Call<List<ChallanModel>> getChallandate(@Field("strMerzeName") String strMerzeName,
//                                            @Field("inttype") String inttype);
//
//
//    //test for biz login
//    @Headers({"Content-Type: application/x-www-form-urlencoded"})
//    @FormUrlEncoded
//    @POST("oauth/token")
//    Call<LoginBiZ> getLoginBiZ(@Field("grant_type") String grant_type,
//                               @Field("username") String username,
//                               @Field("password") String password);
//
//
//    //bizmotion ====================================================================================
//    @POST("ShfitConfig/SaveAttendance")
//    Call<String> getAttendanceDataSubmit(@Body JsonObject data);
//
//
//    @POST("ShfitConfig/mUpdateAtnnStatApps")
//    Call<String> getApprovePostAttendance(@Body JsonArray jsonArray);
//
//    @POST("ShfitConfig/mUpdateLeaveAppsStatus")
//    Call<String> getApprovePostLeave(@Body JsonArray jsonArray);
//
//    @POST("ShfitConfig/mGetAppsAttendance")
//    Call<List<AttendanceResponce>> getAttendanceSummaryData(
//            @Query("strUserName") String strUserName,
//            @Query("strCardNo") String strCardNo,
//            @Query("strAsonDate") String strAsonDate
//    );
//
//
//    @POST("ShfitConfig/mGetAbsentList")
//    Call<List<AbsentModel>> getAbsentSummaryData(
//            @Query("strUserName") String strUserName,
//            @Query("strCardNo") String strCardNo,
//            @Query("strAsonDate") String strAsonDate
//    );
//
//
//
//    @POST("ShfitConfig/mGetPresentList")
//    Call<List<PresentModel>> getPresentSummaryData(
//            @Query("strUserName") String strUserName,
//            @Query("strCardNo") String strCardNo,
//            @Query("strAsonDate") String strAsonDate
//    );
//
//
//    @POST("ShfitConfig/mGetAppsAttnDetails")
//    Call<List<AHinfoModel>> getIDtoAHinfo(
//            @Query("strUserName") String strUserName,
//            @Query("strCardNo") String strCardNo);
//
//
//    @GET("ShfitConfig/mGetEmp")
//    Call<List<CardNoModel>> getCardNo();
//
//    @POST("ShfitConfig/mGetUserReturnVal")
//    Call<List<AttendanceModel>> getButtonStatus(@Body JsonObject data);
//
//    @GET("ShfitConfig/mGetAttendance")
//    Call<List<AttendanceModel>> getAllAttendance();
//
//
//    @GET("ShfitConfig/mGetSpecialTaskLists")
//    Call<List<MessageInappModel>> getMessage();
//
//
//    @POST("ShfitConfig/mPostSpecialTaskResponsive")
//    Call<String> postSeenInfo(@Body JsonObject data);
//
//
//
//    @POST("ShfitConfig/ChangePassword")
//    Call<String> postForPasswordChange(@Body JsonObject data);
//
//    @GET
//    Call<List<LeaveModel>> getLeaveType(@Url String url);
//
//
//    @GET("ShfitConfig/mGetAppsLeaveList")
//    Call<List<LeaveApproveModel>> getLeaveApproveList();
//
//
//    @POST("ShfitConfig/mPostLeave")
//    Call<String> postLeave(@Body JsonObject data);
//
//
//
//    @POST("ShfitConfig/mPostEditDeleteLeave")
//    Call<String> postLeaveUpdateDelete(@Body JsonObject data);
//
//
//    @POST("ShfitConfig/mPostForGetStatusLeaveAttendance")
//    Call<String> getStatusLeaveAttendance(@Body JsonObject data);
//
//
//    @POST("ShfitConfig/mPostForDeleteLeaveInsertAttendance")
//    Call<String> postForDeleteLeaveInsertAttendane(@Body JsonObject data);
//
//
//
//    @POST("ShfitConfig/mGetUserLeave")
//    Call<List<LeaveListModel>> getLeaveListWithDate(@Body JsonObject data);
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostWeekend")
//    Call<String> postWeekend(@Body JsonObject data);
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mGetUserWeekendList")
//    Call<List<WeekendListModel>> mGetUserWeekendList(@Body JsonObject data);
//
//    @GET("ShfitConfig/mGetTourList")
//    Call<List<TourTypeModel>> getTourTypeModel();
//
//    @POST("ShfitConfig/mGetTourLedgerList")
//    Call<List<MarketAndRouteModel>> getMarketAndRouteModel(@Body JsonObject data);
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostTourPlanRouteList")
//    Call<String> mGetPostTourPlanRouteList(@Body JsonArray jsonArray);
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mGetTourPlanRouteReturnValue")
//    Call<List<getTourFromList>> mGetTourPlanRouteReturnValue(@Body JsonObject data);
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mGetTourPlanRouteReturnApps")
//    Call<List<getTourFromList>> mGetTourPlanRouteReturnValueApp(@Body JsonObject data);
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostDoctorVisitssslist")
//    Call<List<VisitList>> mGetVisitPlanRouteReturnValue(@Body JsonObject data);
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostPrescriptionSlip")
//    Call<String> mGetmPostPrescriptionSlip(@Body JsonObject data);
//
//    @GET("ShfitConfig/mGetNoticeList")
//    Call<List<NoticeModel>> getNoticeList();
//
//
//    @POST("ShfitConfig/mPostNoticeSeenBy")
//    Call<String> mPostSeenBy(@Body JsonObject data);
//
//
//    @GET("ShfitConfig/mGetNoticeList")
//    Call<List<NoticeModell>> getNoticeList1();
//
//
//    @GET
//    Call<List<TrainingModel>> getTrainingList(@Url String url);
//
//
//    @GET
//    Call<List<ExamModel>> getExamWithMarketAccess(@Url String url);
//
//    //Exam Api
//    @GET("ShfitConfig/mGetExamList")
//    Call<List<ExamModel>> getExamList();
//
//
//    @GET("ShfitConfig/GetExamListForFieldForce")
//    Call<List<ExamItem>> getExamResultList(
//            @Query("branchId") String branchId,
//            @Query("cardNo") String cardNo
//    );
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostExamResults")
//    Call<String> mSubmitExamMark(@Body JsonObject data);
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mGetdailyTaskList")
//    Call<List<TodaysTask>> mGetdailyTaskList(@Body JsonObject data);
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostTadaClaims")
//    Call<String> mPostTadaList(@Body JsonObject data);
//
//
//    @GET("ShfitConfig/mGetDoctorLedgerTypeList")
//    Call<List<VisitType>> getTypeVisit();
//
//
//    @Headers({"Content-type: application/json", "Accept: */*"})
//    @POST("ShfitConfig/mPostDoctorVisits")
//    Call<String> mPostDoctorPlanRouteList(@Body JsonArray jsonArray);
//
//    @GET("ShfitConfig/mGetProductNameList")
//    Call<List<ProductModel>> mGetProductNameList();
//
//    //@Headers("Content-Type: application/json")
//    @POST("InvoiceList/DuesAggrement")
//    Call<List<AgreementModel>> getAgreementData(@Body JsonObject jsonObject);
//
//    @POST("InvoiceList/mSaveAggrenment")
//    Call<String> getAgreementOutputData(@Body JsonObject jsonObject);
//
//
//    @POST("InvoiceList/mCreditLimit")
//    Call<List<LimitModel>> getLimitApi(@Body JsonObject jsonObject);
//
//    @POST("InvoiceList/mUpdateHalt")
//    Call<String> getHaltLimit(@Body JsonObject jsonObject);
//
//
//    @POST("InvoiceList/mUpdateChangeLimit")
//    Call<String> getChangeLimitFinal(@Body JsonObject jsonObject);
//
    @POST("ShfitConfig/mPostLocations")
    Call<String> postLocation(@Body JsonObject jsonObject);


//    //mGetHonorariumLists===========================================================================
//    @GET("Honorarium/mGetHonorariumLists")
//    Call<List<Product>> getHonorariumList();
//
//    @POST("Honorarium/mPostHonorarium")
//    Call<String> postHonorarium(@Body JsonObject jsonObject);
//
//    @GET("Honorarium/mGetAllHonorariumLists")
//    Call<List<HonoriamSubmitModel>> getAllHonorariumList();
//
//    @POST("Honorarium/mPostHonorariumUpdate")
//    Call<String> getAllHonorariumListUpdate(@Body JsonArray jsonArray);
//
//    @POST("Honorarium/mPostHonorariumDelete")
//    Call<String> deleteHonorarium(@Query("honorariumId") int honorariumId);
//
//
//    @GET("Honorarium/mGetAllHonorariumReport")
//    Call<List<HonoReportModel>> getHonorariumReports(
//            @Query("fromdate") String fromDate,
//            @Query("todate") String toDate,
//            @Query("mearzName") String mearzName,
//            @Query("role") String role
//    );
//
//
//    //api for version===============================================================================
//    @POST("Version/mPostAppVersionSave")
//    Call<String> saveVersion(@Query("honorariumId") int honorariumId);
//
//
//    @GET("Version/mGetAppVersion")
//    Call<VersionModel> getAllVersionCode(@Query("branchId") String branchId);
//
//
//    //api for Dist Slip upload =====================================================================
//    @POST("Distributor/SlipUpload")
//    Call<String> postDistSlip(@Body JsonObject data);
//
//
//    @GET("Distributor/mgetDistributorDepositSlip")
//    Call<List<DipositModel>> getDistSlip(@Query("card_no") String cardNo);
//
//
//    @GET
//    Call<List<DoctorDistModel>> getDoctorListDist(@Url String url);
//
//
//    @GET
//    Call<SubmitOrderModelDist> pendingOrderGetDist(@Url String url);
//
//
//    @GET("Tracking/mGetLocationdataList")
//    Call<ResponseStatusModel> getLocationData(
//            @Query("date") String date,
//            @Query("empCardNo") String empCardNo
//    );
//
//
//    @POST("AppsDashboard")
//    Call<DashboardMasterModel> getAppsDashboard(
//            @Query("strUserName") String userName,
//            @Query("strFdate") String fromDate,
//            @Query("strTdate") String toDate,
//            @Query("strBranchid") String branchId
//    );

}