package com.example.tug_v_classifier;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;

public class VerifyUser extends AppCompatActivity {
    EditText code;
    Button verify;
    String userName;

    private final String TAG = "Verify User: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);

        code = (EditText)findViewById(R.id.verificationcodeinput);
        verify = (Button)findViewById(R.id.verifybutton);

        Bundle getUserName = getIntent().getExtras();
        userName = getUserName.getString("username");

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmTask().execute(String.valueOf(code.getText()), userName);
            }
        });
    }

    private class ConfirmTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            final String[] result = new String[1];

            final GenericHandler confirmationCallback = new GenericHandler() {
                @Override
                public void onSuccess() {
                    result[0]="Succeeded!";
                    Intent goToMenu = new Intent(VerifyUser.this, LogIn.class);
                    startActivity(goToMenu);
                }

                @Override
                public void onFailure(Exception exception) {
                    result[0]="Failed: "+exception.getMessage();

                }
            };

            CognitoSettings cognitoSettings = new CognitoSettings(VerifyUser.this);

            CognitoUser thisUser = cognitoSettings.getUserPool().getUser(strings[1]);
            thisUser.confirmSignUp(strings[0], false, confirmationCallback);
            return result[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "Confirmation result: "+s);
        }
    }
}
