package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserLogSearchResults extends AppCompatActivity {
    private String userName, searchUserName, searchLocation, searchDate, searchRecClass, searchSetClass, searchResultStatus;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private ArrayList<UserLogItem> nameArray, locationArray, dateArray, recClassArray, setClassArray, resultsArray;
    private boolean localOnly;
    private Bundle sendInfo;
    private ListView userLogList;
    private Button mainMenu, back;
    private String selectedUserLogID;
    private ArrayList<UserLogItem> finalList;
    private UserLogAdapter adapter;
    private TextView noUserLogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_search_results);

        Bundle getSearchInfo = getIntent().getExtras();
        userName=getSearchInfo.getString("username");
        searchUserName=getSearchInfo.getString("searchname");
        searchLocation=getSearchInfo.getString("searchlocation");
        searchDate=getSearchInfo.getString("searchdate");
        searchResultStatus=getSearchInfo.getString("searchresult");
        searchSetClass=getSearchInfo.getString("searchsetclass");
        searchRecClass=getSearchInfo.getString("searchrecclass");
        localOnly=getSearchInfo.getString("localcheck").equals("1");

        mainMenu = (Button)findViewById(R.id.gobackbutton);
        back = (Button)findViewById(R.id.searchagainbutton);
        userLogList = (ListView)findViewById(R.id.userloglist);
        noUserLogs = (TextView)findViewById(R.id.nouserlogstext);



        sendInfo = new Bundle();
        sendInfo.putString("username", userName);
        if(localOnly){
            sendInfo.putString("localcheck", "1");
        }else{
            sendInfo.putString("localcheck", "0");
        }
        sendInfo.putString("searchcheck", "1");
        sendInfo.putString("searchname", searchUserName);
        sendInfo.putString("searchlocation", searchLocation);
        sendInfo.putString("searchdate", searchDate);
        sendInfo.putString("searchresult", searchResultStatus);
        sendInfo.putString("searchsetclass", searchSetClass);
        sendInfo.putString("searchrecclass", searchRecClass);
        sendInfo.putString("frommenu", "0");



        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(UserLogSearchResults.this, MainMenu.class);
                goToMenu.putExtras(sendInfo);
                startActivity(goToMenu);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearch = new Intent(UserLogSearchResults.this, UserLogSearch.class);
                goToSearch.putExtras(sendInfo);
                startActivity(goToSearch);
            }
        });

        nameArray = new ArrayList<>();
        locationArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        recClassArray = new ArrayList<>();
        setClassArray = new ArrayList<>();
        resultsArray = new ArrayList<>();
        finalList = new ArrayList<>();


        userLogItemDBAdapter=UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);

        if(localOnly){
            ArrayList<UserLogItem> allLogs;
            allLogs = userLogItemDBAdapter.getAllLocalUserLogs();
            if(!searchUserName.isEmpty()){
                for(int i=0;i<allLogs.size();i++){
                    if(searchUserName.equals(allLogs.get(i).getUserName())){
                        nameArray.add(allLogs.get(i));
                    }
                }
            }
            if(!searchLocation.isEmpty()){
                for(int i=0;i<allLogs.size();i++){
                    if(searchLocation.equals(allLogs.get(i).getLocation())){
                        locationArray.add(allLogs.get(i));
                    }
                }
            }
            if(!searchDate.isEmpty()){
                for(int i=0;i<allLogs.size();i++){
                    if(searchDate.equals(allLogs.get(i).getDate())){
                        dateArray.add(allLogs.get(i));
                    }
                }
            }
            if(!searchResultStatus.isEmpty()){
                for(int i=0;i<allLogs.size();i++){
                    if(searchResultStatus.equals(allLogs.get(i).getResultStatus())){
                        resultsArray.add(allLogs.get(i));
                    }
                }
            }
            if(!searchRecClass.isEmpty()){
                for(int i=0;i<allLogs.size();i++) {
                    if (searchRecClass.equals(allLogs.get(i).getRecClass())) {
                        recClassArray.add(allLogs.get(i));
                    }
                }
            }
            if(!searchSetClass.isEmpty()) {
                for (int i = 0; i < allLogs.size(); i++) {
                    if (searchSetClass.equals(allLogs.get(i).getSetClass())) {
                        setClassArray.add(allLogs.get(i));
                    }
                }
            }
            finalList = getCommonUserLogs();

            adapter = new UserLogAdapter(this, R.layout.user_log_list, finalList);
            userLogList.setAdapter(adapter);
            if(finalList.size()==0){
                noUserLogs.setText("No Results. Please Edit Search");
                noUserLogs.setVisibility(View.VISIBLE);
            }






        }else{
            //TODO add code for searching cloud DB
        }

        userLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedUserLogID = finalList.get(position).getUserLogID();
                sendInfo.putString("id", selectedUserLogID);
                Intent detailedInfo = new Intent(UserLogSearchResults.this, UserLogDetailedInfo.class);
                detailedInfo.putExtras(sendInfo);
                startActivity(detailedInfo);
            }
        });

    }

    private ArrayList<UserLogItem> getCommonUserLogs(){
        ArrayList<UserLogItem> list = new ArrayList<>();
        if(nameArray.size()>0){
            list=nameArray;
            if(locationArray.size()>0){
                list.retainAll(locationArray);
            }
            if(dateArray.size()>0){
                list.retainAll(dateArray);
            }
            if(resultsArray.size()>0){
                list.retainAll(resultsArray);
            }
            if(recClassArray.size()>0){
                list.retainAll(recClassArray);
            }
            if(setClassArray.size()>0){
                list.retainAll(setClassArray);
            }
        }else if(locationArray.size()>0){
            list=locationArray;
            if(dateArray.size()>0){
                list.retainAll(dateArray);
            }
            if(resultsArray.size()>0){
                list.retainAll(resultsArray);
            }
            if(recClassArray.size()>0){
                list.retainAll(recClassArray);
            }
            if(setClassArray.size()>0){
                list.retainAll(setClassArray);
            }
        }else if(dateArray.size()>0){
            list=dateArray;
            if(resultsArray.size()>0){
                list.retainAll(resultsArray);
            }
            if(recClassArray.size()>0){
                list.retainAll(recClassArray);
            }
            if(setClassArray.size()>0){
                list.retainAll(setClassArray);
            }
        }else if(resultsArray.size()>0){
            list=resultsArray;
            if(recClassArray.size()>0){
                list.retainAll(recClassArray);
            }
            if(setClassArray.size()>0){
                list.retainAll(setClassArray);
            }
        }else if(recClassArray.size()>0){
            list=recClassArray;
            if(setClassArray.size()>0){
                list.retainAll(setClassArray);
            }
        }else if(setClassArray.size()>0){
            list=setClassArray;
        }
        return list;
    }
}
