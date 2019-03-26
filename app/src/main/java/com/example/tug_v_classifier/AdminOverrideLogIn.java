package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AdminOverrideLogIn extends AppCompatActivity {
    private Button adminLogIn, goBack;
    private TextView adminUserName, adminPassword;
    String charResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_override_log_in);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        charResult = bundle.getString("recResult");

        adminLogIn = (Button)findViewById(R.id.adminloginbutton);
        adminLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle logInBundle = new Bundle();
                logInBundle.putString("result", charResult);
                Intent adminLogIn = new Intent(AdminOverrideLogIn.this, IncorrectResultLog.class);
                adminLogIn.putExtras(logInBundle);
                startActivity(adminLogIn);
            }
        });


        goBack = (Button)findViewById(R.id.goback);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle backBundle = new Bundle();
                backBundle.putString("recResult", charResult);
                Intent goBack = new Intent(AdminOverrideLogIn.this, Results.class);
                goBack.putExtras(backBundle);
                startActivity(goBack);
            }
        });
    }
}
