package com.example.tug_v_classifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class UserLogSearchResults extends AppCompatActivity {
    private String userName, searchUserName, searchLocation, searchDate, searchRecClass, searchSetClass, searchResultStatus;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private ArrayList<UserLogItem> nameArray, locationArray, dateArray, recClassArray, setClassArray, resultsArray;
    private boolean localOnly;


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
        localOnly=getSearchInfo.getString("localonly").equals("1");

        nameArray = new ArrayList<>();
        locationArray = new ArrayList<>();
        dateArray = new ArrayList<>();
        recClassArray = new ArrayList<>();
        setClassArray = new ArrayList<>();
        resultsArray = new ArrayList<>();

        userLogItemDBAdapter=UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);

        if(localOnly){
            if(!searchUserName.isEmpty()){
                nameArray = userLogItemDBAdapter.getLogsBySpecificUserName(searchUserName);
            }
            if(!searchLocation.isEmpty()){
                locationArray=userLogItemDBAdapter.getLogsBySpecificlocation(searchLocation);
            }
            if(!searchDate.isEmpty()){
                dateArray=userLogItemDBAdapter.getLogsBySpecificDate(searchDate);
            }
            if(!searchResultStatus.isEmpty()){
                resultsArray=userLogItemDBAdapter.getLogsBySpecificResultStatus(searchResultStatus);
            }
            if(!searchRecClass.isEmpty()){
                recClassArray=userLogItemDBAdapter.getLogsBySpecificRecClass(searchRecClass);
            }
            if(!searchSetClass.isEmpty()){
                setClassArray=userLogItemDBAdapter.getLogsBySpecificSetClass(searchSetClass);
            }


        }

    }
}
