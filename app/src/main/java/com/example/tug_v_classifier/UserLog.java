package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class UserLog extends AppCompatActivity {
    private ListView userLogList;
    private Button mainMenu, search;
    private String selectedUserLogID;
    private ArrayList<UserLogItem> userLogItemsListLocal, userLogItemsListCloud;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private String userName;
    private Switch localSwitch;
    private TextView userLogEmpty;
    private boolean localChecked;
    private UserLogAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        Bundle getUserName = getIntent().getExtras();
        userName=getUserName.getString("username");
        mainMenu=(Button)findViewById(R.id.gobackbutton);
        search = (Button)findViewById(R.id.searchbutton);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        localSwitch=(Switch)findViewById(R.id.localswitch);
        userLogEmpty=(TextView)findViewById(R.id.nouserlogstext);
        localChecked = getUserName.getString("localcheck").equals("1");
        if(localChecked){
            localSwitch.setChecked(true);
        }


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
        userLogItemsListLocal = new ArrayList<>();
        userLogItemsListCloud = new ArrayList<>();

        adapter = new UserLogAdapter(this, R.layout.user_log_list, userLogItemsListCloud);
        userLogItemsListLocal = userLogItemDBAdapter.getAllLocalUserLogs();
        //TODO ADD code to get list of call User Logs in Cloud

        if(localChecked){
            showlocalUserLogs();
        }else{
            showCloudUserLogs();
        }

        localSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    localChecked=true;
                    showlocalUserLogs();
                }else{
                    localChecked=false;
                    showCloudUserLogs();
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearch = new Intent(UserLog.this, UserLogSearch.class);
            }
        });





        userLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(localChecked){
                    selectedUserLogID = userLogItemsListLocal.get(position).getUserLogID();
                }else{
                    selectedUserLogID = userLogItemsListCloud.get(position).getUserLogID();
                }
                Bundle userLogID = new Bundle();
                userLogID.putString("id", selectedUserLogID);
                userLogID.putString("username", userName);
                if(localChecked){
                    userLogID.putString("localcheck","1");
                }else{
                    userLogID.putString("localcheck","0");
                }
                Intent detailedInfo = new Intent(UserLog.this, UserLogDetailedInfo.class);
                detailedInfo.putExtras(userLogID);
                startActivity(detailedInfo);
            }
        });




    }

    private void showlocalUserLogs(){
        //method for getting local DB values
        userLogEmpty.setVisibility(View.INVISIBLE);
        adapter = new UserLogAdapter(this, R.layout.user_log_list, userLogItemsListLocal);
        userLogList.setAdapter(adapter);
        if(userLogItemsListLocal.size()==0){
            userLogEmpty.setText("No Local User Logs");
            userLogEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void showCloudUserLogs(){
        //method for getting cloud DB values
        if(userLogItemsListCloud.size()==0){
            userLogEmpty.setVisibility(View.VISIBLE);
        }
        adapter = new UserLogAdapter(this, R.layout.user_log_list, userLogItemsListCloud);
        userLogList.setAdapter(adapter);

    }
}
