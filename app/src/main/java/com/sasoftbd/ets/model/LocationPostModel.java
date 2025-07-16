package com.sasoftbd.ets.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationPostModel {


    private String strSTART_TIME;
    private String strEND_TIME;
    private String strDURATION;
    private String strADDRESS;
    private String strDISTANCE;
    private String strBAT_CHARGE;
    private String strINTERVAL;
    private String strLATITUDE;
    private String strLONGITUDE;
    private String strUSER_NAME;
    private String strEMP_CARD_NO;
    private String strRole;

    public LocationPostModel(String strSTART_TIME, String strEND_TIME, String strDURATION, String strDISTANCE, String strADDRESS, String strBAT_CHARGE, String strINTERVAL, String strLATITUDE, String strLONGITUDE, String strUSER_NAME, String strEMP_CARD_NO, String strRole) {
        this.strSTART_TIME = strSTART_TIME;
        this.strEND_TIME = strEND_TIME;
        this.strDURATION = strDURATION;
        this.strDISTANCE = strDISTANCE;
        this.strADDRESS = strADDRESS;
        this.strBAT_CHARGE = strBAT_CHARGE;
        this.strINTERVAL = strINTERVAL;
        this.strLATITUDE = strLATITUDE;
        this.strLONGITUDE = strLONGITUDE;
        this.strUSER_NAME = strUSER_NAME;
        this.strEMP_CARD_NO = strEMP_CARD_NO;
        this.strRole = strRole;
    }


    public String getStrRole() {
        return strRole;
    }

    public void setStrRole(String strRole) {
        this.strRole = strRole;
    }

    public String getStrSTART_TIME() {
        return strSTART_TIME;
    }

    public void setStrSTART_TIME(String strSTART_TIME) {
        this.strSTART_TIME = strSTART_TIME;
    }

    public String getStrEND_TIME() {
        return strEND_TIME;
    }

    public void setStrEND_TIME(String strEND_TIME) {
        this.strEND_TIME = strEND_TIME;
    }

    public String getStrDURATION() {
        return strDURATION;
    }

    public void setStrDURATION(String strDURATION) {
        this.strDURATION = strDURATION;
    }

    public String getStrADDRESS() {
        return strADDRESS;
    }

    public void setStrADDRESS(String strADDRESS) {
        this.strADDRESS = strADDRESS;
    }

    public String getStrDISTANCE() {
        return strDISTANCE;
    }

    public void setStrDISTANCE(String strDISTANCE) {
        this.strDISTANCE = strDISTANCE;
    }

    public String getStrBAT_CHARGE() {
        return strBAT_CHARGE;
    }

    public void setStrBAT_CHARGE(String strBAT_CHARGE) {
        this.strBAT_CHARGE = strBAT_CHARGE;
    }

    public String getStrINTERVAL() {
        return strINTERVAL;
    }

    public void setStrINTERVAL(String strINTERVAL) {
        this.strINTERVAL = strINTERVAL;
    }

    public String getStrLONGITUDE() {
        return strLONGITUDE;
    }

    public void setStrLONGITUDE(String strLONGITUDE) {
        this.strLONGITUDE = strLONGITUDE;
    }

    public String getStrLATITUDE() {
        return strLATITUDE;
    }

    public void setStrLATITUDE(String strLATITUDE) {
        this.strLATITUDE = strLATITUDE;
    }

    public String getStrUSER_NAME() {
        return strUSER_NAME;
    }

    public void setStrUSER_NAME(String strUSER_NAME) {
        this.strUSER_NAME = strUSER_NAME;
    }

    public String getStrEMP_CARD_NO() {
        return strEMP_CARD_NO;
    }

    public void setStrEMP_CARD_NO(String strEMP_CARD_NO) {
        this.strEMP_CARD_NO = strEMP_CARD_NO;
    }
}
