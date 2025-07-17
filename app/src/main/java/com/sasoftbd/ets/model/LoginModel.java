package com.sasoftbd.ets.model;




public class LoginModel {



    private String id;
    private int intMpoType;
    private int lngUniqueNo;
    private String strCardNo;
    private String strLedgerName;
    private String strMobileNo;
    private String strResponse;
    private String strSecurityCode;
    private String strTeritorryCode;
    private String strTeritorryName;
    private String strUserID;
    private String strUserPassword;
    private String status;
    private String token;
    private String areaHead;
    private String division;
    private String deviceInfo;
    private String appinfo;
    private String deviceId;



    private String strbranchID;
    private String strTeam;
    private String strZone;
    private String strDivision;
    private String strArea;
    private String strRole;
    private String strIamge;


    public String getStrIamge() {
        return strIamge;
    }

    public void setStrIamge(String strIamge) {
        this.strIamge = strIamge;
    }

    public String getStrCardNo() {
        return strCardNo;
    }

    public String getStrbranchID() {
        return strbranchID;
    }

    public void setStrbranchID(String strbranchID) {
        this.strbranchID = strbranchID;
    }

    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public String getStrZone() {
        return strZone;
    }

    public void setStrZone(String strZone) {
        this.strZone = strZone;
    }

    public String getStrDivision() {
        return strDivision;
    }

    public void setStrDivision(String strDivision) {
        this.strDivision = strDivision;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrRole() {
        return strRole;
    }

    public void setStrRole(String strRole) {
        this.strRole = strRole;
    }

    public LoginModel() {
    }

    public LoginModel(String strUserID, String strUserPassword) {
        this.strUserID = strUserID;
        this.strUserPassword = strUserPassword;
    }


    public LoginModel(String id, int intMpoType, int lngUniqueNo,String strCardNo, String strLedgerName, String strMobileNo,
                      String strResponse, String strSecurityCode, String strTeritorryCode,
                      String strTeritorryName, String strUserID, String strUserPassword,
                      String status, String token, String areaHead, String division, String deviceId) {
        this.id = id;
        this.intMpoType = intMpoType;
        this.lngUniqueNo = lngUniqueNo;
        this.strCardNo = strCardNo;
        this.strLedgerName = strLedgerName;
        this.strMobileNo = strMobileNo;
        this.strResponse = strResponse;
        this.strSecurityCode = strSecurityCode;
        this.strTeritorryCode = strTeritorryCode;
        this.strTeritorryName = strTeritorryName;
        this.strUserID = strUserID;
        this.strUserPassword = strUserPassword;
        this.status = status;
        this.token = token;
        this.areaHead = areaHead;
        this.division = division;
        this.deviceId = deviceId;
//        this.deviceInfo = String.valueOf(deviceInfo);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppinfo() {
        return appinfo;
    }

    public void setAppinfo(String appinfo) {
        this.appinfo = appinfo;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getAreaHead() {
        return areaHead;
    }

    public void setAreaHead(String areaHead) {
        this.areaHead = areaHead;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrResponse() {
        return strResponse;
    }

    public void setStrResponse(String strResponse) {
        this.strResponse = strResponse;
    }

    public int getIntMpoType() {
        return intMpoType;
    }

    public void setIntMpoType(int intMpoType) {
        this.intMpoType = intMpoType;
    }


    public String getCardNo() {
        return strCardNo;
    }

    public void setStrCardNo(String setCardNo) {
        this.strCardNo = setCardNo;
    }



    public int getLngUniqueNo() {
        return lngUniqueNo;
    }

    public void setLngUniqueNo(int lngUniqueNo) {
        this.lngUniqueNo = lngUniqueNo;
    }

    public String getStrLedgerName() {
        return strLedgerName;
    }

    public void setStrLedgerName(String strLedgerName) {
        this.strLedgerName = strLedgerName;
    }

    public String getStrMobileNo() {
        return strMobileNo;
    }

    public void setStrMobileNo(String strMobileNo) {
        this.strMobileNo = strMobileNo;
    }

    public String getStrSecurityCode() {
        return strSecurityCode;
    }

    public void setStrSecurityCode(String strSecurityCode) {
        this.strSecurityCode = strSecurityCode;
    }

    public String getStrTeritorryCode() {
        return strTeritorryCode;
    }

    public void setStrTeritorryCode(String strTeritorryCode) {
        this.strTeritorryCode = strTeritorryCode;
    }

    public String getStrTeritorryName() {
        return strTeritorryName;
    }

    public void setStrTeritorryName(String strTeritorryName) {
        this.strTeritorryName = strTeritorryName;
    }

    public String getStrUserID() {
        return strUserID;
    }

    public void setStrUserID(String strUserID) {
        this.strUserID = strUserID;
    }

    public String getStrUserPassword() {
        return strUserPassword;
    }

    public void setStrUserPassword(String strUserPassword) {
        this.strUserPassword = strUserPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
