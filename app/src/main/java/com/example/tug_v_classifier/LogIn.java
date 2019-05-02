package com.example.tug_v_classifier;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.mobile.auth.ui.AuthUIConfiguration;
import com.amazonaws.mobile.auth.ui.SignInUI;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

import static com.amazonaws.mobile.client.UserState.GUEST;
import static com.amazonaws.mobile.client.UserState.SIGNED_IN;
import static com.amazonaws.mobile.client.UserState.SIGNED_OUT;
import static com.amazonaws.mobile.client.UserState.SIGNED_OUT_FEDERATED_TOKENS_INVALID;
import static com.amazonaws.mobile.client.UserState.SIGNED_OUT_USER_POOLS_TOKENS_INVALID;

public class LogIn extends AppCompatActivity {

    private Button signIn, test, test2, createUser;
    private EditText userName, password;
    private String name;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private UserLogUploadedDBAdapter userLogUploadedDBAdapter;
    private AWSAppSyncClient mAWSAppSyncClient;


    private final String TAG = "LogIn Activity: ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);




        userName = (EditText)findViewById(R.id.usernameinpnut);
        password = (EditText)findViewById(R.id.passwordinput);
        test = (Button)findViewById(R.id.deletebutton);
        test2 = (Button)findViewById(R.id.deletebutton2);
        createUser = (Button)findViewById(R.id.createuserbutton);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        userLogUploadedDBAdapter = UserLogUploadedDBAdapter.getUserLogItemDBAdapterInstance(this);
        CognitoSettings cognitoSettings = new CognitoSettings(LogIn.this);
        Bundle sendName = new Bundle();
        Intent loginActivity = new Intent(LogIn.this, MainMenu.class);

       /* AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AuthUIConfiguration config= new AuthUIConfiguration.Builder()
                        .userPools(true)
                        .logoResId(R.drawable.textron)
                        .backgroundColor(Color.parseColor("#F8923B"))
                        .canCancel(false)
                        .build();
                SignInUI signin = (SignInUI) AWSMobileClient.getInstance().getClient(
                        LogIn.this,
                        SignInUI.class);
                Log.i(TAG, "signing IN: "+AWSMobileClient.getInstance().getUsername());
                signin.login(
                        LogIn.this, MainMenu.class).authUIConfiguration(config).execute();
            }
        }).execute();*/









        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogItemDBAdapter.delteAll();
            }
        });

        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogUploadedDBAdapter.delteAll();
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goCreateUser = new Intent(LogIn.this, CreateUser.class);
                startActivity(goCreateUser);
            }
        });

        final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
            @Override
            public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
                Log.i(TAG, "Login Secussful!");
                sendName.putString("username", name);
                loginActivity.putExtras(sendName);
                startActivity(loginActivity);

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
                sendName.putString("username", name);
                loginActivity.putExtras(sendName);
                startActivity(loginActivity);

                /*CognitoUser thisUser = cognitoSettings.getUserPool().getUser(String.valueOf(name));

                thisUser.getSessionInBackground(authenticationHandler);*/

            }
        });
    }
}
