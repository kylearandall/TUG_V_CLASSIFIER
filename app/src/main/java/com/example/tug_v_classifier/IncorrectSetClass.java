package com.example.tug_v_classifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class IncorrectSetClass extends AppCompatActivity {

    private Button a,b,c,d,e;
    private String charResult;
    TextView resultDispaly;


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

        Intent getResult = getIntent();
        Bundle bResult =getResult.getExtras();
        charResult = bResult.getString("result");
        String resultString = getResources().getString(R.string.resultsheader)+" "+charResult;

        resultDispaly.setText(resultString);

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
        String overrideBoxMessage = getResources().getString(R.string.overrideboxline1)+" "+result+"\n"+getResources().getString(R.string.overrideboxline2)+" "+overrideClass+"\n"+getResources().getString(R.string.overrideboxline3);
        builder.setMessage(overrideBoxMessage);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                startActivity(returnToMenu);
            }
        });
        builder.show();
    }

    private void correctClassDialogBox(String correctClass){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.correctclassboxtitle);
        String correctClassMessage = correctClass+" "+getResources().getString(R.string.correctclassboxline1)+"\n"+getResources().getString(R.string.correctclassboxline2);
        builder.setMessage(correctClassMessage);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                classSetDialogBox(correctClass);
            }
        });
        builder.show();
    }




}
