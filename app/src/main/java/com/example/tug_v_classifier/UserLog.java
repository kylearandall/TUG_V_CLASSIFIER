package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class UserLog extends AppCompatActivity {
    private ListView userLogList;
    private Button mainMenu, search;
    private String selectedUserLogID;
    private ArrayList<UserLogItem> userLogItemsList;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        Bundle getUserName = getIntent().getExtras();
        userName=getUserName.getString("username");
        mainMenu=(Button)findViewById(R.id.gobackbutton);
        search = (Button)findViewById(R.id.searchbutton);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendName = new Bundle();
                sendName.putString("username", userName);
                Intent goBack = new Intent(UserLog.this, MainMenu.class);
                goBack.putExtras(sendName);
                startActivity(goBack);
            }
        });

        userLogList = (ListView)findViewById(R.id.userloglist);
        userLogItemsList = new ArrayList<>();

        //code for getting local DB values
        userLogItemsList = userLogItemDBAdapter.getAllLocalUserLogs();


        UserLogAdapter adapter = new UserLogAdapter(this, R.layout.user_log_list, userLogItemsList);

        userLogList.setAdapter(adapter);

        userLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUserLogID = userLogItemsList.get(position).getUserName()+"_"+userLogItemsList.get(position).getDate();
                Bundle userLogID = new Bundle();
                userLogID.putString("id", selectedUserLogID);
                Intent detailedInfo = new Intent(UserLog.this, UserLogDetailedInfo.class);
                detailedInfo.putExtras(userLogID);
                startActivity(detailedInfo);
            }
        });




    }
}
