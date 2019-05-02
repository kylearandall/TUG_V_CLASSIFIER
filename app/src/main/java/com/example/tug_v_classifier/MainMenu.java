package com.example.tug_v_classifier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AwsChunkedEncodingInputStream;
import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.auth.core.SignInStateChangeListener;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    Button classifier, userLog, signOut;
    private String name;
    private String TAG = "Main Menu Activity: ";
    private AWSCredentialsProvider credentialsProvider;
    private AWSConfiguration configuration;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);








        if(ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainMenu.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1);
        }

        Intent getName = getIntent();
        Bundle sentName= getName.getExtras();
        name = sentName.getString("username");




        classifier = (Button)findViewById(R.id.IDbutton);
        userLog = (Button)findViewById(R.id.userlogbutton);

        classifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendName = new Bundle();
                sendName.putString("username", name);
                Intent lClass = new Intent(MainMenu.this, LaunchClassifier.class);
                lClass.putExtras(sendName);
                startActivity(lClass);
            }
        });

        userLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendName = new Bundle();
                sendName.putString("username", name);
                sendName.putString("localcheck","1");
                sendName.putString("frommenu", "1");
                Intent uLog = new Intent(MainMenu.this, UserLog.class);
                uLog.putExtras(sendName);
                startActivity(uLog);
            }
        });

        /*signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IdentityManager.getDefaultIdentityManager().signOut();
            }
        });*/


    }
}
