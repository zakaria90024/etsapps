package com.sasoftbd.ets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardDate {

    @SerializedName("strEMP_CARD_NO")
    @Expose
    private String strEMPCARDNO;
    @SerializedName("strATTEN_DATEIN")
    @Expose
    private String strATTENDATEIN;

    public String getStrEMPCARDNO() {
        return strEMPCARDNO;
    }

    public void setStrEMPCARDNO(String strEMPCARDNO) {
        this.strEMPCARDNO = strEMPCARDNO;
    }

    public String getStrATTENDATEIN() {
        return strATTENDATEIN;
    }

    public void setStrATTENDATEIN(String strATTENDATEIN) {
        this.strATTENDATEIN = strATTENDATEIN;
    }


}