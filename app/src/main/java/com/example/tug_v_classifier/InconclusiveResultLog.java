package com.example.tug_v_classifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class InconclusiveResultLog extends AppCompatActivity {
    private Button save;
    private CheckBox night, overcast, rain, snow, hail, other, distoredCameraFeed, cameraOffline, dirtyCamera, cameraPositionedIncorrectly, unusualTireShape, unknown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconclusive_result_log);

        night = (CheckBox) findViewById(R.id.nightbox);
        overcast = (CheckBox) findViewById(R.id.overcastbox);
        rain = (CheckBox) findViewById(R.id.rainbox);
        snow = (CheckBox) findViewById(R.id.snowbox);
        hail = (CheckBox) findViewById(R.id.hailbox);
        other = (CheckBox) findViewById(R.id.otherbox);
        distoredCameraFeed = (CheckBox) findViewById(R.id.distortedcambox);
        cameraOffline = (CheckBox) findViewById(R.id.camofflinebox);
        dirtyCamera = (CheckBox) findViewById(R.id.dirtycambox);
        cameraPositionedIncorrectly = (CheckBox) findViewById(R.id.incorrectcamposbox);
        unusualTireShape = (CheckBox) findViewById(R.id.unusualtirebox);
        unknown = (CheckBox) findViewById(R.id.unknownbox);

        //save button setting and on click listener
        save = (Button) findViewById(R.id.savebutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Add function to save all checked boxes to database.

                if (unknown.isChecked() || other.isChecked()) {
                    otherUnknownBox();

                } else {
                    Intent saveFactors = new Intent(InconclusiveResultLog.this, InconclusiveSetClass.class);
                    startActivity(saveFactors);
                }
            }
        });

    }

    private void otherUnknownBox() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.unknownotherboxtitle);
        builder.setMessage(R.string.unknownotherbottext);
        EditText boxInput = new EditText(this);
        builder.setView(boxInput);

        builder.setPositiveButton(R.string.continuebutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (boxInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill in Form", Toast.LENGTH_SHORT).show();
                    builder.show();
                } else {
                    Intent otherInput = new Intent(InconclusiveResultLog.this, InconclusiveSetClass.class);
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
}