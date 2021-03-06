package com.example.tug_v_classifier;

import android.app.Dialog;
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

import java.util.ArrayList;

public class Results extends AppCompatActivity {
    TextView result;
    String sResult;
    String charResult, date, location, resultStatus, userName, fileDir;
    Button set, incorrect, scanAgain;
    private UserLogItemDBAdapter userLogItemDBAdapter;

    private final String TAG = "Results Activity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        set = (Button)findViewById(R.id.setbutton);
        incorrect = (Button)findViewById(R.id.incorrectbutton);
        scanAgain = (Button)findViewById(R.id.scanagainbutton);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);



        result = (TextView)findViewById(R.id.resulttv);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.getString("result")!=null){
            sResult = bundle.getString("result");
            getResultCharacter(sResult);
        }else{
            charResult = bundle.getString("recResult");

        }
        result.setText(charResult);
        userName = bundle.getString("username");
        location = bundle.getString("location");
        date = bundle.getString("date");
        fileDir = bundle.getString("imagepath");
        String buttonText = getResources().getString(R.string.settowingclass)+": "+charResult;

        set.setText(buttonText);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox(getResources().getString(R.string.setDialogboxtitle) + " " + charResult, getResources().getString(R.string.setDialogboxmessage));

            }
        });

        scanAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanAgainDialogBox();
            }
        });

        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incorrectDialogBox();
            }
        });


    }

    private void getResultCharacter(String res){
        switch (res){
            case "cata":
                charResult="A";
                break;
            case "catb":
                charResult="B";
                break;
            case "catc":
                charResult="C";
                break;
            case "catd":
                charResult="D";
                break;
            case "cate":
                charResult="E";
                break;
        }
    }

    private void setClassDialogBox(String title, String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userLogItemDBAdapter.insertCorrect(userName, date, location, charResult, fileDir);
                classIsSetDialogBox();
            }
        });
        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderSingle.show();

    }

    private void classIsSetDialogBox(){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle(R.string.classissetdialogboxtitle);
        String boxMessage = getResources().getString(R.string.classissetdialogboxtext)+" "+charResult;
        builderSingle.setMessage(boxMessage);

        builderSingle.setPositiveButton(R.string.returntomenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle sendUserName = new Bundle();
                sendUserName.putString("username", userName);
                Intent menu = new Intent(Results.this, MainMenu.class);
                menu.putExtras(sendUserName);
                startActivity(menu);
            }
        });

        builderSingle.show();
    }

    private void scanAgainDialogBox(){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle(R.string.scanagaintitle);
        builderSingle.setMessage(R.string.scanagaintext);

        builderSingle.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent stopPicConverter = new Intent(Results.this, PictureConverter.class);
                stopService(stopPicConverter);
                Bundle sendUserName = new Bundle();
                sendUserName.putString("username", userName);
                Intent backToScan = new Intent(Results.this, LaunchClassifier.class);
                backToScan.putExtras(sendUserName);
                startActivity(backToScan);
            }
        });

        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builderSingle.show();

    }

    private void incorrectDialogBox(){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle(R.string.incorrectdialogtitle);
        builderSingle.setMessage(R.string.incorretdialogtext);

        builderSingle.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resultStatus = "Incorrect";
                Bundle bundle = new Bundle();
                bundle.putString("recResult", charResult);
                bundle.putString("resultStatus", resultStatus);
                bundle.putString("date", date);
                bundle.putString("location", location);
                bundle.putString("username", userName);
                bundle.putString("imagepath", fileDir);
                Intent adminLogIn = new Intent(Results.this, AdminOverrideLogIn.class);
                adminLogIn.putExtras(bundle);
                startActivity(adminLogIn);

            }
        });
        builderSingle.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderSingle.show();
    }





}
