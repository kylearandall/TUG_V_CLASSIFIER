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
    private String userName, location, date, result, factors, otherUnknownText, setClass, resultStatus, pictureString;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private ArrayList<String> pictureList;
    private BroadcastReceiver myBR;
    private final String TAG = "InconclusiveSetClass Activity";
    private boolean loadComplete;

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
        resultStatus = "Inconclusive";

        //get Picture String from Service
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        loadComplete = false;
        pictureList = new ArrayList<>();

        myBR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String currentPictureString = intent.getStringExtra("picture");
                pictureList.add(currentPictureString);
                Log.i(TAG, "picture received.");
                if(pictureList.size()==20){
                    loadComplete=true;
                }
            }
        };

        IntentFilter filter = new IntentFilter("android.intent.action.SEND");
        registerReceiver(myBR,filter);

        Intent startPicMovement = new Intent("android.intent.action.ANSWER");
        this.sendBroadcast(startPicMovement);

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
                if(loadComplete) {
                    pictureString = pictureArraytoString();
                    userLogItemDBAdapter.insertOverride(userName,date,location,result,setClass,resultStatus,pictureString,null,otherUnknownText,factors);
                    classSetBox(setClass);
                }else{
                    Toast.makeText(getApplicationContext(), "Pictures still loading. Please Try Again", Toast.LENGTH_SHORT).show();
                }
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
                unregisterReceiver(myBR);
                Intent menu = new Intent(InconclusiveSetClass.this, MainMenu.class);
                menu.putExtras(sendUserName);
                startActivity(menu);
            }
        });

        builder.show();
    }

    private String pictureArraytoString(){
        JSONObject json = new JSONObject();
        try {
            json.put("pictures", new JSONArray(pictureList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

}
