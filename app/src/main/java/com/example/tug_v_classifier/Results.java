package com.example.tug_v_classifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Results extends AppCompatActivity {
    TextView result;
    String sResult;
    String charResult;
    Button set, incorrect, scanAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        set = (Button)findViewById(R.id.setbutton);
        incorrect = (Button)findViewById(R.id.incorrectbutton);
        scanAgain = (Button)findViewById(R.id.scanagainbutton);


        result = (TextView)findViewById(R.id.resulttv);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        sResult = bundle.getString("result");
        getResultCharacter(sResult);
        result.setText(charResult);
        String buttonText = getResources().getString(R.string.settowingclass)+": "+charResult;

        set.setText(buttonText);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClassDialogBox(getResources().getString(R.string.setDialogboxtitle)+" "+charResult,getResources().getString(R.string.setDialogboxmessage));
            }
        });

        scanAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanAgainDialogBox();
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
            case "Inconclusive":
                charResult="Inconclusive";
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
                Intent menu = new Intent(Results.this, MainMenu.class);
                //TODO Add code to write usage to database.
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
                Intent backToScan = new Intent(Results.this, LaunchClassifier.class);
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


}
