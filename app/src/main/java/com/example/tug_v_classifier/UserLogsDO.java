package com.example.tug_v_classifier;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "tugapp-mobilehub-1952717818-UserLogs")

public class UserLogsDO {
    private String _userlogid;
    private String _date;
    private String _adminApprovedName;
    private String _factors;
    private String _keywords;
    private String _location;
    private String _otherUnknownText;
    private String _recClass;
    private String _resultStatus;
    private String _setClass;
    private String _username;

    @DynamoDBHashKey(attributeName = "userlogid")
    @DynamoDBAttribute(attributeName = "userlogid")
    public String getUserlogid() {
        return _userlogid;
    }

    public void setUserlogid(final String _userlogid) {
        this._userlogid = _userlogid;
    }
    @DynamoDBRangeKey(attributeName = "date")
    @DynamoDBAttribute(attributeName = "date")
    public String getDate() {
        return _date;
    }

    public void setDate(final String _date) {
        this._date = _date;
    }
    @DynamoDBAttribute(attributeName = "adminApprovedName")
    public String getAdminApprovedName() {
        return _adminApprovedName;
    }

    public void setAdminApprovedName(final String _adminApprovedName) {
        this._adminApprovedName = _adminApprovedName;
    }
    @DynamoDBAttribute(attributeName = "factors")
    public String getFactors() {
        return _factors;
    }

    public void setFactors(final String _factors) {
        this._factors = _factors;
    }
    @DynamoDBAttribute(attributeName = "keywords")
    public String getKeywords() {
        return _keywords;
    }

    public void setKeywords(final String _keywords) {
        this._keywords = _keywords;
    }
    @DynamoDBAttribute(attributeName = "location")
    public String getLocation() {
        return _location;
    }

    public void setLocation(final String _location) {
        this._location = _location;
    }
    @DynamoDBAttribute(attributeName = "otherUnknownText")
    public String getOtherUnknownText() {
        return _otherUnknownText;
    }

    public void setOtherUnknownText(final String _otherUnknownText) {
        this._otherUnknownText = _otherUnknownText;
    }
    @DynamoDBAttribute(attributeName = "recClass")
    public String getRecClass() {
        return _recClass;
    }

    public void setRecClass(final String _recClass) {
        this._recClass = _recClass;
    }
    @DynamoDBAttribute(attributeName = "resultStatus")
    public String getResultStatus() {
        return _resultStatus;
    }

    public void setResultStatus(final String _resultStatus) {
        this._resultStatus = _resultStatus;
    }
    @DynamoDBAttribute(attributeName = "setClass")
    public String getSetClass() {
        return _setClass;
    }

    public void setSetClass(final String _setClass) {
        this._setClass = _setClass;
    }
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return _username;
    }

    public void setUsername(final String _username) {
        this._username = _username;
    }

}
