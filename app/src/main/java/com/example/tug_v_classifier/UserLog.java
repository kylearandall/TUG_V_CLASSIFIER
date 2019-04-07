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

        UserLogItem testOne = new UserLogItem("User A", "02-05-2019: 15:39:19", "Cobb County Airport", "A", "A", "Correct", null, null, null );

        ArrayList<String> testTwoFactors = new ArrayList<>();
        testTwoFactors.add("Overcast");
        testTwoFactors.add("Snow");
        testTwoFactors.add("Dirty Camera");
        UserLogItem testTwo = new UserLogItem("User B", "02-17-2019: 09:12:57", "Hartsfield-Jackson International Airport", "C", "B", "Incorrect", "Admin A", null, testTwoFactors);

        ArrayList<String>testThreeFactors = new ArrayList<>();
        testThreeFactors.add("Other");
        UserLogItem testThree = new UserLogItem("User A", "02-28-2019: 12:35:18", "Cobb County Airport", "N/A", "D", "Inconclusive", null, "New Aircraft type", testThreeFactors);

        userLogItemsList.add(testOne);
        userLogItemsList.add(testTwo);
        userLogItemsList.add(testThree);

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
