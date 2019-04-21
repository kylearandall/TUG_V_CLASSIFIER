package com.example.tug_v_classifier;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.ArrayList;

public class uploadLogsToCloud extends Service{

    private final String TAG = "UploadLogsToCloud: ";
    private ArrayList<UserLogItem> logsToUpload;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private DynamoDBAccess dynamoDBAccess;
    private boolean awsConnected;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service Started");
        awsConnected =false;
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        ArrayList<UserLogItem> allLogs = userLogItemDBAdapter.getAllLocalUserLogs();

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d(TAG, "AWSMobileClient is instantiated and you are connected to AWS!");
                awsConnected=true;
            }
        }).execute();

        logsToUpload = new ArrayList<>();
        for(int i =0; i<allLogs.size();i++){
            if(allLogs.get(i).isUploaded()==0){
                logsToUpload.add(allLogs.get(i));
            }
        }
        new DynamoDBAsyncTask().execute();
    }

    private class DynamoDBAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "Uploaded Started");
            dynamoDBAccess=DynamoDBAccess.getInstance(uploadLogsToCloud.this);
            for(int i=0;i<logsToUpload.size();i++){
                Document newDoc = dynamoDBAccess.addUserLogToCloud(logsToUpload.get(i));
            }
            Log.i(TAG, "Upload Finished!");
            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "User Log Upload Complete");
            if(awsConnected){
                for(int i= 0;i<logsToUpload.size(); i++){
                    Log.i(TAG, "Starting Image Upload for Log "+(i+1)+" out of "+logsToUpload.size());
                    for(int j=0;j<5;j++){
                        Log.i(TAG, "Uploading image "+(j+1)+" of 5 for Log "+(i+1)+" out of "+logsToUpload.size());
                        uploadImage(logsToUpload.get(i), j);

                    }
                    userLogItemDBAdapter.uploadedLog(logsToUpload.get(i).getUserLogID());
                }
            }else{
                Log.i(TAG, "Error: Picture Upload Skipped. AWS Not Connected");
                stopSelf();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void uploadImage(UserLogItem userLog, int i){
        Log.i(TAG, "Picture Upload Start");
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver uploadObserver = transferUtility.upload("user-log-photos/"+userLog.getUserLogID()+i+".jpg",new File(userLog.getPictureStrings(),i+".jpg"));


        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if(TransferState.COMPLETED == state){
                    if(i==4){
                        Log.d(TAG, "Picture Upload Completed");
                        Log.d(TAG, "Service Ending");
                        stopSelf();

                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal)*100;
                int percentDone = (int)percentDonef;

                Log.d(TAG, "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");

            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d(TAG, "Error uploading pic. ID: "+id);

            }
        });


    }

}
