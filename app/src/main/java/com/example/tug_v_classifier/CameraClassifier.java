package com.example.tug_v_classifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CameraClassifier extends AppCompatActivity {
    private String location;
    private String date;
    private String userName;

    public String getDate(){
        return date;
    }
    public String getLocation(){
        return location;
    }
    public String getUserName(){
        return userName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_classifier);

        Bundle userInfo = getIntent().getExtras();
        location = userInfo.getString("location");
        date = userInfo.getString("date");
        userName = userInfo.getString("username");



        if (null == savedInstanceState) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }
}
