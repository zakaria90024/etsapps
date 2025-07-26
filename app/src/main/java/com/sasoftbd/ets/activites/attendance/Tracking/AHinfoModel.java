package com.sasoftbd.ets.activites.attendance.Tracking;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AHinfoModel {


    @SerializedName("TEAM_NAME")
    @Expose
    private Object teamName;
    @SerializedName("strParticulars")
    @Expose
    private String strParticulars;
    @SerializedName("NOMPO")
    @Expose
    private Object nompo;
    @SerializedName("Present")
    @Expose
    private Object present;
    @SerializedName("LEAVE")
    @Expose
    private Object leave;
    @SerializedName("ABSENT")
    @Expose
    private Object absent;
    @SerializedName("ZONE_NAME")
    @Expose
    private Object zoneName;
    @SerializedName("MPO_DIV")
    @Expose
    private Object mpoDiv;
    @SerializedName("MPO_AREA")
    @Expose
    private Object mpoArea;

    @SerializedName("TERRITORRY_NAME")
    @Expose
    private Object territorryName;



    @SerializedName("TERRITORRY_CODE")
    @Expose
    private Object territorryCode;

    @SerializedName("Role")
    @Expose
    private Object role;
    @SerializedName("Position")
    @Expose
    private Object position;
    @SerializedName("DIVISION")
    @Expose
    private Object division;
    @SerializedName("AREA")
    @Expose
    private Object area;
    @SerializedName("MARKET_NAME")
    @Expose
    private Object marketName;
    @SerializedName("strStatus")
    @Expose
    private Object strStatus;
    @SerializedName("strCardNO")
    @Expose
    private String strCardNO;
    @SerializedName("struserID")
    @Expose
    private String struserID;

    public Object getTerritorryCode() {
        return territorryCode;
    }

    public void setTerritorryCode(Object territorryCode) {
        this.territorryCode = territorryCode;
    }

    public Object getTeamName() {
        return teamName;
    }

    public void setTeamName(Object teamName) {
        this.teamName = teamName;
    }

    public String getStrParticulars() {
        return strParticulars;
    }

    public void setStrParticulars(String strParticulars) {
        this.strParticulars = strParticulars;
    }

    public Object getNompo() {
        return nompo;
    }

    public void setNompo(Object nompo) {
        this.nompo = nompo;
    }

    public Object getPresent() {
        return present;
    }

    public void setPresent(Object present) {
        this.present = present;
    }

    public Object getLeave() {
        return leave;
    }

    public void setLeave(Object leave) {
        this.leave = leave;
    }

    public Object getAbsent() {
        return absent;
    }

    public void setAbsent(Object absent) {
        this.absent = absent;
    }

    public Object getZoneName() {
        return zoneName;
    }

    public void setZoneName(Object zoneName) {
        this.zoneName = zoneName;
    }

    public Object getMpoDiv() {
        return mpoDiv;
    }

    public void setMpoDiv(Object mpoDiv) {
        this.mpoDiv = mpoDiv;
    }

    public Object getMpoArea() {
        return mpoArea;
    }

    public void setMpoArea(Object mpoArea) {
        this.mpoArea = mpoArea;
    }

    public Object getTerritorryName() {
        return territorryName;
    }

    public void setTerritorryName(Object territorryName) {
        this.territorryName = territorryName;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getDivision() {
        return division;
    }

    public void setDivision(Object division) {
        this.division = division;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public Object getMarketName() {
        return marketName;
    }

    public void setMarketName(Object marketName) {
        this.marketName = marketName;
    }

    public Object getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(Object strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrCardNO() {
        return strCardNO;
    }

    public void setStrCardNO(String strCardNO) {
        this.strCardNO = strCardNO;
    }

    public String getStruserID() {
        return struserID;
    }

    public void setStruserID(String struserID) {
        this.struserID = struserID;
    }


}
