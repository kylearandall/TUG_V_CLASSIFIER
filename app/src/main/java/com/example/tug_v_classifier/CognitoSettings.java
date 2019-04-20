package com.example.tug_v_classifier;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {

    //These values must be changes when new User Pool is set up in AWS
    private String userPoolId= "us-east-2_E0tfECnx2";
    private String clientId="5jprt5u47qqgoqkilp4ug1irb3";
    private String clientSecret = "rboduv5rnb51enttlhvspuikv7d2l1q642tc9p81u6gaa0cbvrf";
    private Regions cognitoRegion = Regions.US_EAST_2;

    private Context context;

    public CognitoSettings(Context context){
        this.context = context;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Regions getCognitoRegion() {
        return cognitoRegion;
    }
    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);
    }



}
