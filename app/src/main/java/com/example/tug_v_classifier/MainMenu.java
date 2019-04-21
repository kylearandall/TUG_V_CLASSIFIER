package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    Button classifier, userLog;
    private String name;
    private UserLogItemDBAdapter userLogItemDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent getName = getIntent();
        Bundle sentName= getName.getExtras();
        name = sentName.getString("username");

        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        ArrayList<UserLogItem> allLogs = userLogItemDBAdapter.getAllLocalUserLogs();
        ArrayList<UserLogItem> logsNotUploaded = new ArrayList<>();
        for(int i =0; i<allLogs.size();i++){
            if(allLogs.get(i).isUploaded()==0){
                logsNotUploaded.add(allLogs.get(i));
            }
        }
        if(logsNotUploaded.size()>0){
            Intent startUpload = new Intent(MainMenu.this, uploadLogsToCloud.class);
            startService(startUpload);
        }



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
