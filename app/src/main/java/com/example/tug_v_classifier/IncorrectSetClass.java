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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class IncorrectSetClass extends AppCompatActivity {

    private Button a,b,c,d,e;
    private String charResult;
    private TextView resultDispaly;
    private Bundle sendUserName;
    private String userName, location, date, factors, otherUnknownText, resultStatus, fileDir, adminUserName;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private final String TAG = "InconclusiveSetClass Activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_set_class);

        a = (Button)findViewById(R.id.abutton);
        b = (Button)findViewById(R.id.bbutton);
        c = (Button)findViewById(R.id.cbutton);
        d = (Button)findViewById(R.id.dbutton);
        e = (Button)findViewById(R.id.ebutton);

        resultDispaly = (TextView)findViewById(R.id.classchar);

        //get Values from previous activity
        Intent getResult = getIntent();
        Bundle bResult =getResult.getExtras();
        charResult = bResult.getString("result");
        userName = bResult.getString("username");
        location = bResult.getString("location");
        date = bResult.getString("date");
        adminUserName = bResult.getString("admin");
        factors = bResult.getString("factors");
        otherUnknownText = bResult.getString("othertext");
        fileDir = bResult.getString("imagepath");
        resultStatus = "Incorrect";

        //set text view with identified class
        String resultString = getResources().getString(R.string.resultsheader)+" "+charResult;
        resultDispaly.setText(resultString);

        //Initialize DB
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);






        //put username in bundle for sending back to main menu
        sendUserName = new Bundle();
        sendUserName.putString("username", userName);


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(charResult.equals("A")){
                    correctClassDialogBox(charResult);
                }else{
                    overrideDialogBox(charResult, "A");
                }
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(charResult.equals("B")){
                    correctClassDialogBox(charResult);
                }else{
                    overrideDialogBox(charResult, "B");
                }
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(charResult.equals("C")){
                    correctClassDialogBox(charResult);
                }else{
                    overrideDialogBox(charResult, "C");
                }
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(charResult.equals("D")){
                    correctClassDialogBox(charResult);
                }else{
                    overrideDialogBox(charResult, "D");
                }
            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(charResult.equals("E")){
                    correctClassDialogBox(charResult);
                }else{
                    overrideDialogBox(charResult, "E");
                }
            }
        });


    }

    private void overrideDialogBox(String result, String overrideClass){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.overrideboxtitle);
        String overrideBoxMessage = getResources().getString(R.string.overrideboxline1)+" "+result+"\n\n"+getResources().getString(R.string.overrideboxline2)+" "+overrideClass+"\n\n"+getResources().getString(R.string.overrideboxline3);
        builder.setMessage(overrideBoxMessage);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    userLogItemDBAdapter.insertOverride(userName,date,location,charResult,overrideClass,resultStatus,fileDir,adminUserName,otherUnknownText,factors);
                    classSetDialogBox(overrideClass);


            }
        });
        builder.show();
    }

    private void classSetDialogBox(String setClass){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.classissetdialogboxtitle);
        String classSetMessage = getResources().getString(R.string.classissetdialogboxtext)+" "+setClass;
        builder.setMessage(classSetMessage);
        builder.setPositiveButton(R.string.returntomenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent returnToMenu = new Intent(IncorrectSetClass.this, MainMenu.class);
                returnToMenu.putExtras(sendUserName);
                startActivity(returnToMenu);
            }
        });
        builder.show();
    }

    private void correctClassDialogBox(String correctClass){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.correctclassboxtitle);
        String correctClassMessage = correctClass+" "+getResources().getString(R.string.correctclassboxline1)+"\n\n"+getResources().getString(R.string.correctclassboxline2);
        builder.setMessage(correctClassMessage);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    userLogItemDBAdapter.insertOverride(userName,date,location,charResult,correctClass,resultStatus,fileDir,adminUserName,otherUnknownText,factors);
                    classSetDialogBox(correctClass);

            }
        });
        builder.show();
    }


}
