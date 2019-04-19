package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {

    private Button signIn, test;
    private EditText userName, password;
    private String name;
    private UserLogItemDBAdapter userLogItemDBAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userName = (EditText)findViewById(R.id.usernameinpnut);
        password = (EditText)findViewById(R.id.passwordinput);
        test = (Button)findViewById(R.id.deletebutton);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogItemDBAdapter.delteAll();
            }
        });




        signIn = (Button)findViewById(R.id.signinbutton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString();
                Bundle sendName = new Bundle();
                sendName.putString("username", name);
                Intent login = new Intent(LogIn.this, MainMenu.class);
                login.putExtras(sendName);
                startActivity(login);
            }
        });
    }
}
