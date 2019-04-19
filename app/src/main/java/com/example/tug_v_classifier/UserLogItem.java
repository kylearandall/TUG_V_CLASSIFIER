package com.example.tug_v_classifier;

import java.util.ArrayList;

public class UserLogItem {

    private String userLogID, userName, date, location, recClass, setClass, resultStatus, adminApprovedName, otherUnknownText, factors, pictureStrings;
    private int uploaded;

    public UserLogItem(String userLogID, String userName, String date, String location, String recClass, String setClass, String resultStatus, String pictureStrings, String adminApprovedName, String otherUnknownText, String factors, int uploaded) {
        this.userLogID = userLogID;
        this.userName = userName;
        this.date = date;
        this.location = location;
        this.recClass = recClass;
        this.setClass = setClass;
        this.resultStatus = resultStatus;
        this.pictureStrings = pictureStrings;
        this.adminApprovedName = adminApprovedName;
        this.otherUnknownText = otherUnknownText;
        this.factors = factors;
        this.uploaded = uploaded;
    }

    public String getUserLogID() {
        return userLogID;
    }

    public void setUserLogID(String userLogID) {
        this.userLogID = userLogID;
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

    public String getPictureStrings() {
        return pictureStrings;
    }

    public void setPictureStrings(String pictureStrings) {
        this.pictureStrings = pictureStrings;
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

    public String getFactors() {
        return factors;
    }

    public void setFactors(String factors) {
        this.factors = factors;
    }

    public int isUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }
}
