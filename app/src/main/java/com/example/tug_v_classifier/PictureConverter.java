package com.example.tug_v_classifier;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class PictureConverter extends Service {
    private Bitmap currentBitMap;
    private boolean isConverterOn;
    private String currentPictureString;
    private ArrayList<String> pictureStrings;
    private ArrayList<Bitmap> bitmaps;
    BroadcastReceiver myBR1, myBR2;
    private final String TAG = "PictureConverter Service";

    class PictureConverterServiceBinder extends Binder {
        public PictureConverter getService() {
            return PictureConverter.this;
        }
    }

    private IBinder myBinder = new PictureConverterServiceBinder();

    @Override
    public void onCreate() {
        isConverterOn = true;
        pictureStrings = new ArrayList<>();
        IntentFilter filter = new IntentFilter("android.intent.action.SENDTO");

        myBR1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                currentPictureString = intent.getStringExtra("picture");
                pictureStrings.add(currentPictureString);
                Log.i(TAG, "picture received.");
            }
        };
        this.registerReceiver(myBR1,filter);

        IntentFilter filter2 = new IntentFilter("android.intent.action.ANSWER");
        myBR2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                for(int i=0;i<pictureStrings.size(); i++){
                    Intent sendPicsToResults = new Intent("android.intent.action.SEND");
                    sendPicsToResults.putExtra("picture", pictureStrings.get(i));
                    sendBroadcast(sendPicsToResults);
                }
            }
        };

        this.registerReceiver(myBR2, filter2);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(myBR1);
        unregisterReceiver(myBR2);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "In onBind...");
        return myBinder;
    }
}
