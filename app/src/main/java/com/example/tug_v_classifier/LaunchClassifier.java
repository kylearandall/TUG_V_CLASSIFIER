package com.example.tug_v_classifier;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;



import com.google.android.libraries.places.api.model.Place;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class LaunchClassifier extends AppCompatActivity {

    private static final String TAG = "LaunchClassifier: ";

    private Button launch;
    private TextView locationTV;
    private String location;
    private PlacesClient placesClient;
    private ArrayList<String> places;
    private String dateAndTime;
    private Date date;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_classifier);
        locationTV = (TextView)findViewById(R.id.currentlocation);

        launch = (Button)findViewById(R.id.launchbutton);


        places = new ArrayList<>();

        Places.initialize(getApplicationContext(), "AIzaSyCiUDYUKsWDnDfWjNkmuYjpwq4jfBYQBRs");
        placesClient = Places.createClient(this);

        // Use fields to define the data types to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME);

        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.builder(placeFields).build();

        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            placesClient.findCurrentPlace(request).addOnSuccessListener(((response) -> {
                for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                    Log.i(TAG, String.format("Place '%s' has likelihood: %f",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));
                    places.add(placeLikelihood.getPlace().getName());
                    location=places.get(0);
                    locationTV.setText(location);
                }
            })).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    locationTV.setText(getResources().getString(R.string.locationerror));
                    launch.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            locationTV.setText(getResources().getString(R.string.locationpermissiondenied));
            launch.setVisibility(View.INVISIBLE);
        }





        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy: HH:mm:ss");
                date = new Date();
                dateAndTime = format.format(date);
                Bundle userInfo = new Bundle();
                userInfo.putString("location", location);
                userInfo.putString("date", dateAndTime);
                Intent launch = new Intent(LaunchClassifier.this, CameraClassifier.class);
                launch.putExtras(userInfo);
                startActivity(launch);
            }
        });
    }
}
