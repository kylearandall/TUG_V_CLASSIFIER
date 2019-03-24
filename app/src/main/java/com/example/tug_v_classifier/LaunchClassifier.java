package com.example.tug_v_classifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchClassifier extends AppCompatActivity {

    Button launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_classifier);

        launch = (Button)findViewById(R.id.launchbutton);

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launch = new Intent(LaunchClassifier.this, CameraClassifier.class);
                startActivity(launch);
            }
        });
    }
}
