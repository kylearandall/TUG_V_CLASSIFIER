package com.example.tug_v_classifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InconclusiveSetClass extends AppCompatActivity {
    private Button a,b,c,d,e;
    private Bundle sendUserName;
    private String userName, location, date, result, factors, otherUnknownText, setClass, resultStatus, fileDir;
    private UserLogItemDBAdapter userLogItemDBAdapter;

    private final String TAG = "InconclusiveSetClass Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconclusive_set_class);

        //Initialize Buttons and set onClick Listeners
        a = (Button)findViewById(R.id.abutton);
        b = (Button)findViewById(R.id.bbutton);
        c = (Button)findViewById(R.id.cbutton);
        d = (Button)findViewById(R.id.dbutton);
        e = (Button)findViewById(R.id.ebutton);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox("A");
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox("B");
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox("C");
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox("D");
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox("E");
            }
        });

        //get Values from Previous activity
        Bundle getValues = getIntent().getExtras();
        userName = getValues.getString("username");
        location = getValues.getString("location");
        date = getValues.getString("date");
        result = getValues.getString("result");
        factors = getValues.getString("factors");
        otherUnknownText = getValues.getString("othertext");
        fileDir=getValues.getString("imagepath");
        resultStatus = "Inconclusive";

        //Initialize DB
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);






        //put username in bundle for sending back to main menu
        sendUserName = new Bundle();
        sendUserName.putString("username", userName);


    }

    private void setClassDialogBox(String setClass){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        String title = getResources().getString(R.string.setDialogboxtitle)+" "+setClass;
        builder.setTitle(title);
        String message = getResources().getString(R.string.inconclusivesetboxline1)+" "+setClass+"\n"+getResources().getString(R.string.inconclusivesetboxline2);
        builder.setMessage(message);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    userLogItemDBAdapter.insertOverride(userName,date,location,result,setClass,resultStatus,fileDir,null,otherUnknownText,factors);
                    classSetBox(setClass);
            }
        });
        builder.show();
    }

    private void classSetBox(String setClass){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.classissetdialogboxtitle);
        String boxMessage = getResources().getString(R.string.classissetdialogboxtext)+" "+setClass;
        builder.setMessage(boxMessage);

        builder.setPositiveButton(R.string.returntomenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent menu = new Intent(InconclusiveSetClass.this, MainMenu.class);
                menu.putExtras(sendUserName);
                startActivity(menu);
            }
        });

        builder.show();
    }


}
