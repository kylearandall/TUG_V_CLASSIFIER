package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

public class LogIn extends AppCompatActivity {

    private Button signIn, test;
    private EditText userName, password;
    private String name;
    private UserLogItemDBAdapter userLogItemDBAdapter;

    private final String TAG = "LogIn Activity: ";



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

        final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                Log.i(TAG, "Login Secussful!");
                Bundle sendName = new Bundle();
                sendName.putString("username", name);

                Intent login = new Intent(LogIn.this, MainMenu.class);
                login.putExtras(sendName);
                startActivity(login);

            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, String.valueOf(password.getText()), null);

                authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                authenticationContinuation.continueTask();

            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

            }

            @Override
            public void authenticationChallenge(ChallengeContinuation continuation) {

            }

            @Override
            public void onFailure(Exception exception) {
                Log.i(TAG, "LogIn Failed: "+exception.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "Login Failed. Please Check User Name and Password.", Toast.LENGTH_SHORT).show();


            }
        };






        signIn = (Button)findViewById(R.id.signinbutton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString();


                CognitoSettings cognitoSettings = new CognitoSettings(LogIn.this);

                CognitoUser thisUser = cognitoSettings.getUserPool().getUser(String.valueOf(name));

                thisUser.getSessionInBackground(authenticationHandler);

            }
        });
    }
}
