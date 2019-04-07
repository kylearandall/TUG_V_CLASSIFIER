package com.example.tug_v_classifier;

import java.util.ArrayList;

public class UserLogItem {

    private String userName, date, location, recClass, setClass, resultStatus, adminApprovedName, otherUnknownText;
    private ArrayList<String> factors;

    public UserLogItem(String userName, String date, String location, String recClass, String setClass, String resultStatus, String adminApprovedName, String otherUnknownText, ArrayList<String> factors) {
        this.userName = userName;
        this.date = date;
        this.location = location;
        this.recClass = recClass;
        this.setClass = setClass;
        this.resultStatus = resultStatus;
        this.adminApprovedName = adminApprovedName;
        this.otherUnknownText = otherUnknownText;
        this.factors = factors;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRecClass() {
        return recClass;
    }

    public void setRecClass(String recClass) {
        this.recClass = recClass;
    }

    public String getSetClass() {
        return setClass;
    }

    public void setSetClass(String setClass) {
        this.setClass = setClass;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getAdminApprovedName() {
        return adminApprovedName;
    }

    public void setAdminApprovedName(String adminApprovedName) {
        this.adminApprovedName = adminApprovedName;
    }

    public String getOtherUnknownText() {
        return otherUnknownText;
    }

    public void setOtherUnknownText(String otherUnknownText) {
        this.otherUnknownText = otherUnknownText;
    }

    public ArrayList<String> getFactors() {
        return factors;
    }

    public void setFactors(ArrayList<String> factors) {
        this.factors = factors;
    }
}
