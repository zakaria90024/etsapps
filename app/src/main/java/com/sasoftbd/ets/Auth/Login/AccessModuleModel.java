package com.sasoftbd.ets.Auth.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AccessModuleModel {

    @SerializedName("moduleName")
    @Expose
    private String moduleName;
    @SerializedName("canAccess")
    @Expose
    private Boolean canAccess;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Boolean getCanAccess() {
        return canAccess;
    }

    public void setCanAccess(Boolean canAccess) {
        this.canAccess = canAccess;
    }

}