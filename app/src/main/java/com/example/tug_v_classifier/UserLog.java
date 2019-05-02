package com.example.tug_v_classifier;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.ArrayList;

public class UserLog extends AppCompatActivity {
    private ListView userLogList;
    private Button mainMenu, search;
    private String selectedUserLogID, selectedUserLogDate;
    private ArrayList<UserLogItem> userLogItemsListLocal, userLogItemsListCloud, userLogsUploaded, userLogsToGoUp, userLogsDisplay;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private UserLogUploadedDBAdapter userLogUploadedDBAdapter;
    private String userName;
    private Switch localSwitch;
    private TextView userLogEmpty;
    private boolean localChecked, fromMenu;
    private UserLogAdapter adapter;
    private int uploadCounter, totalUploads;
    DynamoDBMapper dynamoDBMapper;


    final static String TAG = "User Log Activity: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        Bundle getUserName = getIntent().getExtras();
        userName=getUserName.getString("username");
        mainMenu=(Button)findViewById(R.id.searchagainbutton);
        search = (Button)findViewById(R.id.searchbutton);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        userLogUploadedDBAdapter = UserLogUploadedDBAdapter.getUserLogItemDBAdapterInstance(this);
        localSwitch=(Switch)findViewById(R.id.localswitch);
        userLogEmpty=(TextView)findViewById(R.id.nouserlogstext);
        localChecked = getUserName.getString("localcheck").equals("1");
        fromMenu = getUserName.getString("frommenu").equals("1");
        if(localChecked){
            localSwitch.setChecked(true);
        }
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();



        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle sendName = new Bundle();
                sendName.putString("username", userName);
                Intent goBack = new Intent(UserLog.this, MainMenu.class);
                goBack.putExtras(sendName);
                startActivity(goBack);
            }
        });
        userLogList = (ListView)findViewById(R.id.userloglist);
        userLogItemsListLocal = new ArrayList<>();
        userLogItemsListCloud = new ArrayList<>();
        userLogsUploaded = new ArrayList<>();
        userLogsToGoUp = new ArrayList<>();
        userLogsDisplay = new ArrayList<>();

        userLogItemsListLocal = userLogItemDBAdapter.getAllLocalUserLogs();
        userLogsUploaded = userLogUploadedDBAdapter.getAllUploadedUserLogs();
        new Thread(new Runnable() {
            @Override
            public void run() {
                PaginatedList<UserLogsDO> cloudDOs = dynamoDBMapper.scan(UserLogsDO.class, new DynamoDBScanExpression());
                for (UserLogsDO log : cloudDOs){
                    UserLogItem cloudItem = new UserLogItem(log.getUserlogid(), log.getUsername(), log.getDate(), log.getLocation(), log.getRecClass(), log.getSetClass(), log.getResultStatus(), log.getUserlogid(), log.getAdminApprovedName(), log.getOtherUnknownText(), log.getFactors(), 1);
                    userLogItemsListCloud.add(cloudItem);
                }
            }
        }).start();






        if(fromMenu){
            int localNum = userLogItemsListLocal.size();
            int uploadNum = userLogsUploaded.size();
            if(localNum>uploadNum){

                totalUploads = localNum-uploadNum;
                userLogsToGoUp.addAll(userLogItemsListLocal);
                userLogsToGoUp.removeAll(userLogsUploaded);
                showUploadBox();
            }
        }




        adapter = new UserLogAdapter(this, R.layout.user_log_list, userLogsDisplay);



        if(localChecked){
            showlocalUserLogs();
        }else{
            showCloudUserLogs();
        }

        userLogList.setAdapter(adapter);

        localSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    localChecked=true;
                    showlocalUserLogs();

                }else{
                    localChecked=false;
                    showCloudUserLogs();
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle searchInfo = new Bundle();
                searchInfo.putString("username", userName);
                Intent goToSearch = new Intent(UserLog.this, UserLogSearch.class);
                goToSearch.putExtras(searchInfo);
                startActivity(goToSearch);
            }
        });





        userLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(localChecked){
                    selectedUserLogID = userLogItemsListLocal.get(position).getUserLogID();
                    selectedUserLogDate = userLogItemsListLocal.get(position).getDate();
                }else{
                    selectedUserLogID = userLogItemsListCloud.get(position).getUserLogID();
                    selectedUserLogDate = userLogItemsListCloud.get(position).getDate();
                }
                Bundle userLogID = new Bundle();
                userLogID.putString("id", selectedUserLogID);
                userLogID.putString("date", selectedUserLogDate);
                userLogID.putString("username", userName);
                if(localChecked){
                    userLogID.putString("localcheck","1");
                }else{
                    userLogID.putString("localcheck","0");
                }
                Intent detailedInfo = new Intent(UserLog.this, UserLogDetailedInfo.class);
                detailedInfo.putExtras(userLogID);
                startActivity(detailedInfo);
            }
        });

    }

    private void showlocalUserLogs(){
        //method for getting local DB values
        userLogsDisplay.clear();
        userLogEmpty.setVisibility(View.INVISIBLE);
        if(userLogItemsListLocal.size()==0){
            userLogEmpty.setText("No Local User Logs");
            userLogEmpty.setVisibility(View.VISIBLE);
        }else{
            userLogsDisplay.addAll(userLogItemsListLocal);
        }
        adapter.notifyDataSetChanged();

    }

    private void showCloudUserLogs(){
        userLogEmpty.setVisibility(View.INVISIBLE);
        userLogsDisplay.clear();
        if(userLogItemsListCloud.size()==0){
            userLogEmpty.setText("No User Logs in Cloud");
            userLogEmpty.setVisibility(View.VISIBLE);
        }else{
            userLogsDisplay.addAll(userLogItemsListCloud);
        }
        adapter.notifyDataSetChanged();
    }

    private void showUploadBox(){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.uploadboxtitle);
        builder.setMessage(R.string.uploadboxmessage);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayList <UserLogsDO> uploadDOs = uploadLogInfo();
                new AsyncSender().execute();
                uploadAllImages();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    private void uploadAllImages(){

        for(int i=0;i<userLogsToGoUp.size();i++){
            for(int j=0; j<5;j++){
                uploadImages(userLogsToGoUp.get(i), j);
            }


        }


    }




    private void uploadImages(UserLogItem userLogItem, int i){


        Log.i(TAG, "Picture Upload Start");
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver uploadObserver = transferUtility.upload("user-log-photos/"+userLogItem.getUserLogID()+i+".jpg",new File(userLogItem.getPictureStrings(),i+".jpg"));

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if(TransferState.COMPLETED == state){
                    userLogUploadedDBAdapter.reinsertUserLog(userLogItem);

                    if(i==4){
                        Log.d(TAG, "Picture Upload Completed");
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

    private ArrayList<UserLogsDO> uploadLogInfo(){
        ArrayList <UserLogsDO> cloudLogsArray = new ArrayList<>();
        for(UserLogItem userLog : userLogsToGoUp){
            UserLogsDO cloudLog = new UserLogsDO();

            cloudLog.setUserlogid(userLog.getUserLogID());
            cloudLog.setUsername(userLog.getUserName());
            cloudLog.setDate(userLog.getDate());
            cloudLog.setLocation(userLog.getLocation());
            cloudLog.setResultStatus(userLog.getResultStatus());
            cloudLog.setRecClass(userLog.getRecClass());
            cloudLog.setSetClass(userLog.getSetClass());
            if(userLog.getAdminApprovedName()!=null){
                cloudLog.setAdminApprovedName(userLog.getAdminApprovedName());
            }else{
                cloudLog.setAdminApprovedName("N/A");
            }
            if(userLog.getOtherUnknownText()!=null||!userLog.getOtherUnknownText().isEmpty()){
                cloudLog.setOtherUnknownText(userLog.getOtherUnknownText());
            }else{
                cloudLog.setOtherUnknownText("N/A");
            }
            if(userLog.getFactors()!=null){
                cloudLog.setFactors(userLog.getFactors());
            }else{
                cloudLog.setFactors("N/A");
            }
            cloudLogsArray.add(cloudLog);
        }
        return cloudLogsArray;
    }

    private final class AsyncSender extends AsyncTask<Void, Void, Void>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UserLog.this);
            pd.setTitle("Uploading User Logs");
            pd.setMessage("Please wait,  User Logs are being upload to cloud database.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList <UserLogsDO> uploadDOs = uploadLogInfo();
            for (UserLogsDO log : uploadDOs){
                dynamoDBMapper.save(log);
            }
            uploadAllImages();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
        }
    }


}
