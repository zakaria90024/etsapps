package com.sasoftbd.ets.activites.attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseStatusModel {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("strADDRESS")
    @Expose
    private String strADDRESS;
    @SerializedName("strBAT_CHARGE")
    @Expose
    private String strBATCHARGE;
    @SerializedName("strDISTANCE")
    @Expose
    private String strDISTANCE;
    @SerializedName("strDURATION")
    @Expose
    private String strDURATION;
    @SerializedName("strEMP_CARD_NO")
    @Expose
    private String strEMPCARDNO;
    @SerializedName("strEND_TIME")
    @Expose
    private String strENDTIME;
    @SerializedName("strINTERVAL")
    @Expose
    private String strINTERVAL;
    @SerializedName("strLATITUDE")
    @Expose
    private String strLATITUDE;
    @SerializedName("strLONGITUDE")
    @Expose
    private String strLONGITUDE;
    @SerializedName("strRole")
    @Expose
    private Object strRole;
    @SerializedName("strSTART_TIME")
    @Expose
    private String strSTARTTIME;
    @SerializedName("strUSER_NAME")
    @Expose
    private String strUSERNAME;
    @SerializedName("insertDate")
    @Expose
    private String insertDate;
    @SerializedName("insertTime")
    @Expose
    private String insertTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrADDRESS() {
        return strADDRESS;
    }

    public void setStrADDRESS(String strADDRESS) {
        this.strADDRESS = strADDRESS;
    }

    public String getStrBATCHARGE() {
        return strBATCHARGE;
    }

    public void setStrBATCHARGE(String strBATCHARGE) {
        this.strBATCHARGE = strBATCHARGE;
    }

    public String getStrDISTANCE() {
        return strDISTANCE;
    }

    public void setStrDISTANCE(String strDISTANCE) {
        this.strDISTANCE = strDISTANCE;
    }

    public String getStrDURATION() {
        return strDURATION;
    }

    public void setStrDURATION(String strDURATION) {
        this.strDURATION = strDURATION;
    }

    public String getStrEMPCARDNO() {
        return strEMPCARDNO;
    }

    public void setStrEMPCARDNO(String strEMPCARDNO) {
        this.strEMPCARDNO = strEMPCARDNO;
    }

    public String getStrENDTIME() {
        return strENDTIME;
    }

    public void setStrENDTIME(String strENDTIME) {
        this.strENDTIME = strENDTIME;
    }

    public String getStrINTERVAL() {
        return strINTERVAL;
    }

    public void setStrINTERVAL(String strINTERVAL) {
        this.strINTERVAL = strINTERVAL;
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

    public Object getStrRole() {
        return strRole;
    }

    public void setStrRole(Object strRole) {
        this.strRole = strRole;
    }

    public String getStrSTARTTIME() {
        return strSTARTTIME;
    }

    public void setStrSTARTTIME(String strSTARTTIME) {
        this.strSTARTTIME = strSTARTTIME;
    }

    public String getStrUSERNAME() {
        return strUSERNAME;
    }

    public void setStrUSERNAME(String strUSERNAME) {
        this.strUSERNAME = strUSERNAME;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }


}
