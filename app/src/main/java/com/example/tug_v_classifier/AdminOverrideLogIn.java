package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AdminOverrideLogIn extends AppCompatActivity {
    private Button adminLogIn, goBack;
    private TextView adminUserName, adminPassword;
    private String charResult, userName, admineUserName, location, date, adminPasswordInput, fileDir;
    private Bundle sendValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_override_log_in);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        charResult = bundle.getString("recResult");
        userName = bundle.getString("username");
        location = bundle.getString("location");
        date = bundle.getString("date");
        fileDir=bundle.getString("imagepath");

        adminUserName = (TextView)findViewById(R.id.adminusername);
        adminPassword = (TextView)findViewById(R.id.adminpassword);

        sendValues = new Bundle();

        adminLogIn = (Button)findViewById(R.id.adminloginbutton);
        adminLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adminUserName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill in Admin User Name and Password", Toast.LENGTH_SHORT).show();
                }else{
                    admineUserName=adminUserName.getText().toString();
                    //adminPasswordInput=adminPassword.getText().toString();
                    sendValues.putString("username", userName);
                    sendValues.putString("location", location);
                    sendValues.putString("date", date);
                    sendValues.putString("result", charResult);
                    sendValues.putString("admin", admineUserName);
                    sendValues.putString("imagepath", fileDir);
                    Intent adminLogIn = new Intent(AdminOverrideLogIn.this, IncorrectResultLog.class);
                    adminLogIn.putExtras(sendValues);
                    startActivity(adminLogIn);
                }
            }
        });


        goBack = (Button)findViewById(R.id.goback);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle backBundle = new Bundle();
                backBundle.putString("recResult", charResult);
                backBundle.putString("username", userName);
                backBundle.putString("location", location);
                backBundle.putString("date", date);
                Intent goBack = new Intent(AdminOverrideLogIn.this, Results.class);
                goBack.putExtras(backBundle);
                startActivity(goBack);
            }
        });
    }
}
