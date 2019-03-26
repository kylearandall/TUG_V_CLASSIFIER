package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class IncorrectResultLog extends AppCompatActivity {
    private String charResult;
    private Button save;
    private CheckBox night, overcast, rain, snow, hail, other, distoredCameraFeed, cameraOffline, dirtyCamera, cameraPositionedIncorrectly, unusualTireShape, unknown;
    private TextView resultTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_result_log);

        //get result and set variable
        Intent getResult = getIntent();
        Bundle bResult = getResult.getExtras();
        charResult = bResult.getString("result");

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

        //save button setting and on click listener
        save = (Button)findViewById(R.id.savebutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Add function to save all checked boxes to database.

                Bundle result1 = new Bundle();
                result1.putString("result", charResult);
                if(unknown.isChecked()||other.isChecked()) {
                    Intent otherInput = new Intent(IncorrectResultLog.this, OtherUnknownLog.class);
                    otherInput.putExtras(result1);
                    startActivity(otherInput);

                }else{
                    Intent saveFactors = new Intent(IncorrectResultLog.this, IncorrectSetClass.class);
                    saveFactors.putExtras(result1);
                    startActivity(saveFactors);
                }
            }
        });



    }
}
