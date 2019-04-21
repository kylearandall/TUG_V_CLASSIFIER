package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import org.w3c.dom.Text;

public class CreateUser extends AppCompatActivity {
    private EditText userNameTV, emailTV, passwordTV;
    private Button register;

    private final String TAG= "CreateUser: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        userNameTV = (EditText) findViewById(R.id.usernameinput);
        emailTV=(EditText)findViewById(R.id.emailinput);
        passwordTV=(EditText)findViewById(R.id.passwordinput);
        register = (Button)findViewById(R.id.registerbutton);

        final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

        final SignUpHandler signupCallback=new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                Log.i(TAG, "Sign up success... is confirmed: "+signUpConfirmationState);

                if(!signUpConfirmationState){
                    Log.i(TAG, "sign up success... not confirmed. verification code sent to "+
                            cognitoUserCodeDeliveryDetails.getDestination());
                }else{
                    Log.i(TAG, "sign up success... confirmed");
                }

                String userName = userNameTV.getText().toString();
                Bundle sendUserName = new Bundle();
                sendUserName.putString("username", userName);
                Intent goToMenu = new Intent(CreateUser.this, VerifyUser.class);
                goToMenu.putExtras(sendUserName);
                startActivity(goToMenu);
            }

            @Override
            public void onFailure(Exception exception) {

                Log.i(TAG, "sign up failure: "+exception.getLocalizedMessage());

            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userAttributes.addAttribute("username", String.valueOf(userNameTV.getText()));
                userAttributes.addAttribute("email", String.valueOf(emailTV.getText()));

                CognitoSettings cognitoSettings = new CognitoSettings(CreateUser.this);
                cognitoSettings.getUserPool().signUpInBackground(String.valueOf(userNameTV.getText()),
                        String.valueOf(passwordTV.getText()), userAttributes, null, signupCallback);
            }
        });



    }
}
