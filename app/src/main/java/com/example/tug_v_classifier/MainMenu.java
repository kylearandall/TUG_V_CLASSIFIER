package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    Button classifier, userLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        classifier = (Button)findViewById(R.id.IDbutton);
        userLog = (Button)findViewById(R.id.userlogbutton);

        classifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lClass = new Intent(MainMenu.this, LaunchClassifier.class);
                startActivity(lClass);
            }
        });


    }
}
