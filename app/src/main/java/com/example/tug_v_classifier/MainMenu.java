package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    Button classifier, userLog;
    private String name;
    private String TAG = "Main Menu Activity: ";

    private UserLogItemDBAdapter userLogItemDBAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d(TAG, "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        Intent getName = getIntent();
        Bundle sentName= getName.getExtras();
        name = sentName.getString("username");

        ArrayList<UserLogItem> logsNotUploaded = userLogItemDBAdapter.getLogsNotUploaded();
        for(int i =0;i<logsNotUploaded.size();i++){
            userLogItemDBAdapter.deleteUserLog(logsNotUploaded.get(i));
            userLogItemDBAdapter.reinsertUserLog(logsNotUploaded.get(i));
        }

        //for(int i=0;i<logsNotUploaded.size();i++){
       //     userLogItemDBAdapter.uploadedLog(logsNotUploaded.get(i).getUserLogID());
       // }

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
                sendName.putString("localcheck","0");
                Intent uLog = new Intent(MainMenu.this, UserLog.class);
                uLog.putExtras(sendName);
                startActivity(uLog);
            }
        });


    }
}
