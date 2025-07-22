package com.sasoftbd.ets.Auth.Login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("cardNo")
    @Expose
    private String cardNo;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("modules")
    @Expose
    private List<AccessModuleModel> modules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<AccessModuleModel> getModules() {
        return modules;
    }

    public void setModules(List<AccessModuleModel> modules) {
        this.modules = modules;
    }

}