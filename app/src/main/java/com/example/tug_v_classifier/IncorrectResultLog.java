package com.example.tug_v_classifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IncorrectResultLog extends AppCompatActivity {
    private String charResult, userName, location, date, adminUserName, factors, otherUnknownText, fileDir;
    private ArrayList<String> factorList;
    private Button save;
    private CheckBox night, overcast, rain, snow, hail, other, distoredCameraFeed, cameraOffline, dirtyCamera, cameraPositionedIncorrectly, unusualTireShape, unknown;
    private TextView resultTitle;
    private Bundle sendValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_result_log);

        //get Values from previous activity
        Bundle getValues = getIntent().getExtras();
        userName = getValues.getString("username");
        location = getValues.getString("location");
        date = getValues.getString("date");
        charResult = getValues.getString("result");
        adminUserName = getValues.getString("admin");
        fileDir = getValues.getString("imagepath");

       //set text to display recommended class
        resultTitle = (TextView)findViewById(R.id.classchar);
        String displayText=getResources().getString(R.string.resultsheader)+" "+charResult;
        resultTitle.setText(displayText);

        //set checkboxes
        night = (CheckBox)findViewById(R.id.nightbox);
        overcast = (CheckBox)findViewById(R.id.overcastbox);
        rain = (CheckBox)findViewById(R.id.rainbox);
        snow = (CheckBox)findViewById(R.id.snowbox);
        hail = (CheckBox)findViewById(R.id.hailbox);
        other = (CheckBox)findViewById(R.id.otherbox);
        distoredCameraFeed = (CheckBox)findViewById(R.id.distortedcambox);
        cameraOffline = (CheckBox)findViewById(R.id.camofflinebox);
        dirtyCamera = (CheckBox)findViewById(R.id.dirtycambox);
        cameraPositionedIncorrectly = (CheckBox)findViewById(R.id.incorrectcamposbox);
        unusualTireShape = (CheckBox)findViewById(R.id.unusualtirebox);
        unknown = (CheckBox)findViewById(R.id.unknownbox);

        //Put User Log Values in Bundle to be Sent to next Activity
        sendValues = new Bundle();
        sendValues.putString("username", userName);
        sendValues.putString("location", location);
        sendValues.putString("date", date);
        sendValues.putString("result", charResult);
        sendValues.putString("admin", adminUserName);
        sendValues.putString("imagepath", fileDir);



        factorList = new ArrayList<>();

        //save button setting and on click listener
        save = (Button)findViewById(R.id.savebutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFactorArray();
                if(factorList.size()==0){
                    Toast.makeText(getApplicationContext(), "Please select which factors may have contributed to Inconclusive Result", Toast.LENGTH_LONG).show();
                }else{
                    factors = factorArrayToString();
                    sendValues.putString("factors", factors);
                    if(unknown.isChecked()||other.isChecked()) {
                        otherUnknownBox();
                    }else{
                        otherUnknownText="";
                        sendValues.putString("othertext",otherUnknownText);
                        Intent saveFactors = new Intent(IncorrectResultLog.this, IncorrectSetClass.class);
                        saveFactors.putExtras(sendValues);
                        startActivity(saveFactors);
                    }

                }


            }
        });
    }

    private void otherUnknownBox(){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.unknownotherboxtitle);
        builder.setMessage(R.string.unknownotherbottext);
        EditText boxInput = new EditText(this);
        builder.setView(boxInput);

        builder.setPositiveButton(R.string.continuebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(boxInput.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please Fill in Form", Toast.LENGTH_SHORT).show();
                }else{
                    otherUnknownText=boxInput.getText().toString();
                    sendValues.putString("othertext", otherUnknownText);
                    Intent otherInput = new Intent(IncorrectResultLog.this, IncorrectSetClass.class);
                    otherInput.putExtras(sendValues);
                    startActivity(otherInput);
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }

    private void getFactorArray(){
        if(night.isChecked()){
            factorList.add("Night");
        }
        if(overcast.isChecked()){
            factorList.add("Overcast");
        }
        if(rain.isChecked()){
            factorList.add("Rain");
        }
        if(snow.isChecked()){
            factorList.add("Snow");
        }
        if(hail.isChecked()){
            factorList.add("Hail");
        }
        if(distoredCameraFeed.isChecked()){
            factorList.add("Distorted Camera Feed");
        }
        if(cameraOffline.isChecked()){
            factorList.add("Camera Offline");
        }
        if(dirtyCamera.isChecked()){
            factorList.add("Dirty Camera");
        }
        if(cameraPositionedIncorrectly.isChecked()){
            factorList.add("Camera Positioned Incorrectly");
        }
        if(unusualTireShape.isChecked()){
            factorList.add("Unusual Tire Shape");
        }
        if(other.isChecked()){
            factorList.add("Other");
        }
        if(unknown.isChecked()){
            factorList.add("Unknown");
        }
    }

    private String factorArrayToString(){
        JSONObject json = new JSONObject();
        try {
            json.put("factors", new JSONArray(factorList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
