package com.sasoftbd.ets.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.ArrayList;
import java.util.List;

public class AuthPrefsDataClass {

    String pic, CNo, details, branchCode, userTypeName, role;
    int userType;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    // Constructor
    public AuthPrefsDataClass(Context context) {

        //dbHelper = new SqliteDbHelper(context);
        //status = dbHelper.getloginfo();
        //model = status.get(0);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        pic = settings.getString("Picture", "");
        userType = settings.getInt("userType", 0);
        role = settings.getString("role", "");
        branchCode = settings.getString("branch_code", "");
        CNo = settings.getString("CardNo", "");
        details = settings.getString("Details", "");
        //final  String branchIdDist = branchCode;

        //setBranchIdDist(branchCode);

    }


    public  String getBranchCode(){
        return branchCode;
    }

    public String getRole(){
        if(userType == 0){
            userTypeName = role;
        }else if(userType == 1){
            userTypeName= role;
        }else if(userType == 2){
            userTypeName= role;
        }else if(userType == 4){
            userTypeName= role;
        }else if(userType == 5){
            userTypeName= role;
        }else if(userType == 6){
            userTypeName= role;
        }
        return userTypeName;
    }


    public String getBranchIdDist(){
        return branchCode;
    }



    public  String getBranchName(){
        if(branchCode.equals("0001")){
            return "Deeplaid";
        }else if(branchCode.equals("0003")){
            return "Herbal";
        }else if(branchCode.equals("0002")){
            return "Sales Center";
        }else {
            return branchCode;
        }
    }


    public String getCardNO(){
        return CNo;
    }

//    public String getUserId(){
//        return String.valueOf( model.getLngUniqueNo());
//    }
//
//    public String getUserName(){
//        return  model.getStrLedgerName().toString();
//    }
//
//    public String getMpoTc(){
//        return model.getStrTeritorryCode();
//    }

    public String getMarketName(){
        String[] split = details.split("\\|");
        return  split[0];
    }

    public String getAreaName(){
        String[] split = details.split("\\|");
        return  split[1];
    }

    public String getDivisionName(){
        String[] split = details.split("\\|");
        return  split[2];
    }

    public String getTeamName(){
        String[] split = details.split("\\|");
        return  split[3];
    }

    public String getZoneName(){
        String[] split = details.split("\\|");
        return  split[4];
    }

}
