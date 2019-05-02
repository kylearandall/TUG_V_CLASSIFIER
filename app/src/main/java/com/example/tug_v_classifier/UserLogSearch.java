package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class UserLogSearch extends AppCompatActivity {
    private Spinner resultStatusSpinner, setClassSpinner, recClassSpinner;
    private String userName, searchUserName, searchLocation, searchDate, selectedResultStatus, selectedSetClass, selectedRecClass;
    private Button search, goBack;
    private TextView userNameInput, locationInput, dateInput;
    private Switch localSwitch;
    private boolean localOnly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_search);

        search = (Button)findViewById(R.id.searchbutton);
        goBack = (Button)findViewById(R.id.searchagainbutton);
        Bundle getInfo = getIntent().getExtras();
        userName = getInfo.getString("username");

        userNameInput = (TextView)findViewById(R.id.searchnameinput);
        locationInput = (TextView)findViewById(R.id.locationsearchinput);
        dateInput = (TextView)findViewById(R.id.datesearchinput);
        localSwitch = (Switch)findViewById(R.id.localswitch);
        localOnly=false;

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendInfoBack = new Bundle();
                sendInfoBack.putString("username", userName);
                sendInfoBack.putString("localcheck","0");
                sendInfoBack.putString("frommenu","0");
                Intent goBack = new Intent(UserLogSearch.this, UserLog.class);
                goBack.putExtras(sendInfoBack);
                startActivity(goBack);
            }
        });

        localSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    localOnly=true;
                }else{
                    localOnly=false;
                }
            }
        });


        resultStatusSpinner = (Spinner)findViewById(R.id.resultstatusspinner);
        ArrayAdapter<CharSequence> rsAdapter = ArrayAdapter.createFromResource(this, R.array.resultstatusspinner, android.R.layout.simple_spinner_dropdown_item);
        rsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultStatusSpinner.setAdapter(rsAdapter);

        resultStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    selectedResultStatus="";
                }else{
                    selectedResultStatus=parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedResultStatus="";

            }
        });

        setClassSpinner = (Spinner)findViewById(R.id.setclassspinner);
        ArrayAdapter<CharSequence> scAdapter = ArrayAdapter.createFromResource(this, R.array.setclassspinner, android.R.layout.simple_spinner_dropdown_item);
        scAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setClassSpinner.setAdapter(scAdapter);

        setClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    selectedSetClass="";
                }else{
                    selectedSetClass=parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedSetClass="";

            }
        });

        recClassSpinner = (Spinner)findViewById(R.id.recclassspinner);
        ArrayAdapter<CharSequence> rcAdapter = ArrayAdapter.createFromResource(this, R.array.recclassspinner, android.R.layout.simple_spinner_dropdown_item);
        rcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recClassSpinner.setAdapter(rcAdapter);

        recClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    selectedRecClass="";
                }else{
                    selectedRecClass=parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedRecClass="";

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameInput.getText().toString().isEmpty()){
                    searchUserName="";
                }else{
                    searchUserName=userNameInput.getText().toString();
                }
                if(locationInput.getText().toString().isEmpty()){
                    searchLocation="";
                }else{
                    searchLocation=locationInput.getText().toString();
                }
                if(dateInput.getText().toString().isEmpty()){
                    searchDate="";
                }else{
                    searchDate=dateInput.getText().toString();
                }

                Bundle searchInfo = new Bundle();
                searchInfo.putString("username", userName);
                searchInfo.putString("searchname", searchUserName);
                searchInfo.putString("searchlocation", searchLocation);
                searchInfo.putString("searchdate", searchDate);
                searchInfo.putString("searchresult", selectedResultStatus);
                searchInfo.putString("searchsetclass", selectedSetClass);
                searchInfo.putString("searchrecclass", selectedRecClass);
                if(localOnly){
                    searchInfo.putString("localonly","1");
                }else{
                    searchInfo.putString("localonly","0");
                }

                Intent sendSearchInfo = new Intent(UserLogSearch.this, UserLogSearchResults.class);
                sendSearchInfo.putExtras(searchInfo);
                startActivity(sendSearchInfo);

            }
        });
    }
}
