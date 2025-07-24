package com.sasoftbd.ets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceModel {


    @SerializedName("strUSER_NAME")
    @Expose
    private String strUSERNAME;
    @SerializedName("strROLE")
    @Expose
    private String strROLE;
    @SerializedName("strEMP_CARD_NO")
    @Expose
    private String strEMPCARDNO;
    @SerializedName("strTC")
    @Expose
    private String strTC;
    @SerializedName("strATTEN_DATEIN")
    @Expose
    private String strATTENDATEIN;
    @SerializedName("strATTEN_TIMEIN")
    @Expose
    private String strATTENTIMEIN;
    @SerializedName("strLATITUDE")
    @Expose
    private String strLATITUDE;
    @SerializedName("strLONGITUDE")
    @Expose
    private String strLONGITUDE;
    @SerializedName("strADDRESS")
    @Expose
    private String strADDRESS;
    @SerializedName("intDISTANCE")
    @Expose
    private String intDISTANCE;
    @SerializedName("intTOTAL_WORKING_HOUR")
    @Expose
    private String intTOTALWORKINGHOUR;
    @SerializedName("strSTAY_HOUR")
    @Expose
    private String strSTAYHOUR;
    @SerializedName("strATTEN_TIMEOUT")
    @Expose
    private String strATTENTIMEOUT;
    @SerializedName("strATTEN_SHIFT")
    @Expose
    private String strATTENSHIFT;
    @SerializedName("strATTEN_COMMENTS")
    @Expose
    private String strATTENCOMMENTS;
    @SerializedName("strACTION")
    @Expose
    private String strACTION;
    @SerializedName("strATTEN_STATUS")
    @Expose
    private String strATTENSTATUS;

    @SerializedName("strEMP_IMAGE")
    @Expose
    private String strEMPIMAGE;


    @SerializedName("insertDate")
    @Expose
    private String insertDate;

    @SerializedName("strTERRITORRY_NAME")
    @Expose
    private String strTERRITORRY_NAME;



    @SerializedName("strAttnKey")
    @Expose
    private String strAttnKey;




    @SerializedName("appsVersion")
    @Expose
    private String appsVersion;

    public String getstrTERRITORRY_NAME() {
        return strTERRITORRY_NAME;
    }


    public String getAppsVersion() {
        return appsVersion;
    }

    public void setAppsVersion(String appsVersion) {
        this.appsVersion = appsVersion;
    }

    public String getStrAttnKey() {
        return strAttnKey;
    }

    public void setStrAttnKey(String strAttnKey) {
        this.strAttnKey = strAttnKey;
    }

    public String getstrINSERT_DATE() {
        return insertDate;
    }
    public String getStrUSERNAME() {
        return strUSERNAME;
    }

    public void setStrUSERNAME(String strUSERNAME) {
        this.strUSERNAME = strUSERNAME;
    }

    public String getStrROLE() {
        return strROLE;
    }

    public void setStrROLE(String strROLE) {
        this.strROLE = strROLE;
    }

    public String getStrEMPCARDNO() {
        return strEMPCARDNO;
    }

    public void setStrEMPCARDNO(String strEMPCARDNO) {
        this.strEMPCARDNO = strEMPCARDNO;
    }

    public String getStrTC() {
        return strTC;
    }

    public void setStrTC(String strTC) {
        this.strTC = strTC;
    }

    public String getStrATTENDATEIN() {
        return strATTENDATEIN;
    }

    public void setStrATTENDATEIN(String strATTENDATEIN) {
        this.strATTENDATEIN = strATTENDATEIN;
    }

    public String getStrATTENTIMEIN() {
        return strATTENTIMEIN;
    }

    public void setStrATTENTIMEIN(String strATTENTIMEIN) {
        this.strATTENTIMEIN = strATTENTIMEIN;
    }

    public String getStrLATITUDE() {
        return strLATITUDE;
    }

    public void setStrLATITUDE(String strLATITUDE) {
        this.strLATITUDE = strLATITUDE;
    }

    public String getStrLONGITUDE() {
        return strLONGITUDE;
    }

    public void setStrLONGITUDE(String strLONGITUDE) {
        this.strLONGITUDE = strLONGITUDE;
    }

    public String getStrADDRESS() {
        return strADDRESS;
    }

    public void setStrADDRESS(String strADDRESS) {
        this.strADDRESS = strADDRESS;
    }

    public String getIntDISTANCE() {
        return intDISTANCE;
    }

    public void setIntDISTANCE(String intDISTANCE) {
        this.intDISTANCE = intDISTANCE;
    }

    public String getIntTOTALWORKINGHOUR() {
        return intTOTALWORKINGHOUR;
    }

    public void setIntTOTALWORKINGHOUR(String intTOTALWORKINGHOUR) {
        this.intTOTALWORKINGHOUR = intTOTALWORKINGHOUR;
    }

    public String getStrSTAYHOUR() {
        return strSTAYHOUR;
    }

    public void setStrSTAYHOUR(String strSTAYHOUR) {
        this.strSTAYHOUR = strSTAYHOUR;
    }

    public String getStrATTENTIMEOUT() {
        return strATTENTIMEOUT;
    }

    public void setStrATTENTIMEOUT(String strATTENTIMEOUT) {
        this.strATTENTIMEOUT = strATTENTIMEOUT;
    }

    public String getStrATTENSHIFT() {
        return strATTENSHIFT;
    }

    public void setStrATTENSHIFT(String strATTENSHIFT) {
        this.strATTENSHIFT = strATTENSHIFT;
    }

    public String getStrATTENCOMMENTS() {
        return strATTENCOMMENTS;
    }

    public void setStrATTENCOMMENTS(String strATTENCOMMENTS) {
        this.strATTENCOMMENTS = strATTENCOMMENTS;
    }

    public String getStrACTION() {
        return strACTION;
    }

    public void setStrACTION(String strACTION) {
        this.strACTION = strACTION;
    }

    public String getStrATTENSTATUS() {
        return strATTENSTATUS;
    }

    public void setStrATTENSTATUS(String strATTENSTATUS) {
        this.strATTENSTATUS = strATTENSTATUS;
    }

    public String getStrEMPIMAGE() {
        return strEMPIMAGE;
    }

    public void setStrEMPIMAGE(String strEMPIMAGE) {
        this.strEMPIMAGE = strEMPIMAGE;
    }


}
