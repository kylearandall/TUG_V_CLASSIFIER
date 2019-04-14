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
    private UserLogItem selectedUserLog;

    public UserLogItem getSelectedUserLog(){
        return selectedUserLog;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        mainMenu=(Button)findViewById(R.id.gobackbutton);
        search = (Button)findViewById(R.id.searchbutton);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(UserLog.this, MainMenu.class);
                startActivity(goBack);
            }
        });

        userLogList = (ListView)findViewById(R.id.userloglist);


        ArrayList<UserLogItem> userLogItemsList = new ArrayList<>();

        //test values for user log



        //end test value code for user log

        UserLogAdapter adapter = new UserLogAdapter(this, R.layout.user_log_list, userLogItemsList);

        userLogList.setAdapter(adapter);

        userLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUserLog = userLogItemsList.get(position);
                Intent detailedInfo = new Intent(UserLog.this, UserLogDetailedInfo.class);
                startActivity(detailedInfo);
            }
        });




    }
}
