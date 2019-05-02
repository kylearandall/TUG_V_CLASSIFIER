package com.example.tug_v_classifier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class UserLogDetailedInfo extends AppCompatActivity {
    private UserLogItem userLogItem;
    private UserLogItemDBAdapter userLogItemDBAdapter;
    private TextView userNameTV, locationTV, dateTV, resultsStatusTV, setClassTV, recClassTV, adminTV, factorsTV, othertextTV, uploadedText;
    private Bundle sendInfoBack;
    private boolean localChecked, uploaded;
    private Button seePics, goBack;
    private TableRow recClassRow, adminRow, factorsRow, otherTextRow;
    private String userName, userLogID, userLogDate, userLogUserName, location, date, resultStatus, setClass, recClass, admin, factorsString, otherText, fileDir;
    private ArrayList<String> factors;
    DynamoDBMapper dynamoDBMapper;
    ArrayList<UserLogItem> allLogs, userLogsUploaded;
    private UserLogUploadedDBAdapter userLogUploadedDBAdapter;


    final static String TAG = "User Log Detailed Info: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_detailed_info);
        userLogItemDBAdapter = UserLogItemDBAdapter.getUserLogItemDBAdapterInstance(this);
        userLogUploadedDBAdapter = UserLogUploadedDBAdapter.getUserLogItemDBAdapterInstance(this);
        Bundle getInfo = getIntent().getExtras();
        userLogID = getInfo.getString("id");
        userName = getInfo.getString("username");
        userLogDate = getInfo.getString("date");
        localChecked = getInfo.getString("localcheck").equals("1");
        uploaded = true;

        userNameTV = (TextView) findViewById(R.id.username);
        locationTV = (TextView) findViewById(R.id.location);
        dateTV = (TextView) findViewById(R.id.date);
        resultsStatusTV = (TextView) findViewById(R.id.resultstatus);
        setClassTV = (TextView) findViewById(R.id.setclass);
        recClassTV = (TextView) findViewById(R.id.recclass);
        adminTV = (TextView) findViewById(R.id.admin);
        factorsTV = (TextView) findViewById(R.id.factors);
        othertextTV = (TextView) findViewById(R.id.othertext);
        uploadedText = (TextView) findViewById(R.id.notuploadedtext);
        seePics = (Button)findViewById(R.id.displaypicsbutton);
        goBack = (Button)findViewById(R.id.searchagainbutton);
        recClassRow = (TableRow)findViewById(R.id.recclassrow);
        adminRow = (TableRow)findViewById(R.id.adminrow);
        factorsRow = (TableRow)findViewById(R.id.factorsrow);
        otherTextRow = (TableRow)findViewById(R.id.othertextrow);


        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        factors = new ArrayList<>();
        userLogsUploaded = new ArrayList<>();

        sendInfoBack = new Bundle();
        sendInfoBack.putString("username", userName);
        if (localChecked) {
            sendInfoBack.putString("localcheck", "1");
        } else {
            sendInfoBack.putString("localcheck", "0");
        }
        sendInfoBack.putString("frommenu", "0");

        allLogs = new ArrayList<>();
        if (localChecked) {
            allLogs = userLogItemDBAdapter.getAllLocalUserLogs();
            userLogsUploaded = userLogUploadedDBAdapter.getAllUploadedUserLogs();
            for (int i = 0; i < allLogs.size(); i++) {
                if (userLogID.equals(allLogs.get(i).getUserLogID())) {
                    userLogItem = allLogs.get(i);
                }
            }
            setText();
        } else {
            new getCloudInfo().execute();
        }

        seePics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (localChecked) {
                        showImagesBox();
                    } else {
                        showCloudImages();
                    }
                }
            });

            goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goBack = new Intent(UserLogDetailedInfo.this, UserLog.class);
                    goBack.putExtras(sendInfoBack);
                    startActivity(goBack);
                }
            });

        }

    private void getFactorsArrayFromString() throws JSONException {
        JSONObject json = new JSONObject(factorsString);
        JSONArray jsonArray= json.optJSONArray("factors");
        for(int i=0;i<jsonArray.length();i++){
            factors.add(jsonArray.optString(i));
        }


    }
    private String factorList(){
        if(factors!=null&&factors.size()>0){
            StringBuilder stringBuilder = new StringBuilder();
            for(String factor:factors){
                stringBuilder.append(factor+"\n");
            }
            return stringBuilder.toString();
        }else{
            return "";
        }
    }

    private void setText(){

        if (localChecked && allLogs.size() != userLogsUploaded.size()) {
            uploadedText.setVisibility(View.VISIBLE);
        } else {
            uploadedText.setVisibility(View.INVISIBLE);
        }

        userLogUserName = userLogItem.getUserName();
        userNameTV.setText(userLogUserName);
        location = userLogItem.getLocation();
        locationTV.setText(location);
        date = userLogItem.getDate();
        dateTV.setText(date);
        resultStatus = userLogItem.getResultStatus();
        resultsStatusTV.setText(resultStatus);
        setClass = userLogItem.getSetClass();
        setClassTV.setText(setClass);

        if (!resultStatus.equals("Correct")) {
            recClass = userLogItem.getRecClass();
            recClassTV.setText(recClass);
            recClassRow.setVisibility(View.VISIBLE);
            if (resultStatus.equals("Incorrect")) {
                admin = userLogItem.getAdminApprovedName();
                adminTV.setText(admin);
                adminRow.setVisibility(View.VISIBLE);
            }
            factorsString = userLogItem.getFactors();
            try {
                getFactorsArrayFromString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            factorsTV.setText(factorList());
            factorsRow.setVisibility(View.VISIBLE);
            if (userLogItem.getOtherUnknownText() != null) {
                otherText = userLogItem.getOtherUnknownText();
                othertextTV.setText(otherText);
                otherTextRow.setVisibility(View.VISIBLE);
            }
        }
        fileDir = userLogItem.getPictureStrings();

    }



    private void showImagesBox(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View imageViewer = layoutInflater.inflate(R.layout.view_images_box, null);

        final AlertDialog box = new AlertDialog.Builder(this).create();

        final int[] currentPicNumber = {0};

        Button next = (Button)imageViewer.findViewById(R.id.nextbutton);
        Button previous = (Button)imageViewer.findViewById(R.id.backbutton);
        Button close = (Button)imageViewer.findViewById(R.id.closebutton);
        ImageView currentImage = (ImageView)imageViewer.findViewById(R.id.currentImage);
        TextView imageText = (TextView)imageViewer.findViewById(R.id.imagecounter);
        currentImage.setImageBitmap(loadBitmapFromStorage(currentPicNumber[0]));
        imageText.setText("Image ("+(currentPicNumber[0]+1)+"/5)");
        previous.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPicNumber[0]++;
                currentImage.setImageBitmap(loadBitmapFromStorage(currentPicNumber[0]));
                imageText.setText("Image ("+(currentPicNumber[0]+1)+"/5)");
                previous.setVisibility(View.VISIBLE);
                if(currentPicNumber[0]==4){
                    next.setVisibility(View.INVISIBLE);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPicNumber[0]--;
                currentImage.setImageBitmap(loadBitmapFromStorage(currentPicNumber[0]));
                imageText.setText("Image ("+(currentPicNumber[0]+1)+"/5)");
                next.setVisibility(View.VISIBLE);
                if(currentPicNumber[0]==0){
                    previous.setVisibility(View.INVISIBLE);
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box.dismiss();
            }
        });


        box.setView(imageViewer);
        box.show();
    }

    private Bitmap loadBitmapFromStorage(int position){
        Bitmap bitmap=null;
        try{
            File pic = new File(fileDir, position+".jpg");

        bitmap = BitmapFactory.decodeStream(new FileInputStream(pic));

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error Loading Picture", Toast.LENGTH_SHORT).show();
        }
        return bitmap;
    }

    private void showCloudImages(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View imageViewer = layoutInflater.inflate(R.layout.view_images_box, null);

        final AlertDialog box = new AlertDialog.Builder(this).create();

        final int[] currentPicNumber = {0};

        Button next = (Button)imageViewer.findViewById(R.id.nextbutton);
        Button previous = (Button)imageViewer.findViewById(R.id.backbutton);
        Button close = (Button)imageViewer.findViewById(R.id.closebutton);
        ImageView currentImage = (ImageView)imageViewer.findViewById(R.id.currentImage);
        TextView imageText = (TextView)imageViewer.findViewById(R.id.imagecounter);
        String imagePath = "https://s3.amazonaws.com/tugapp-userfiles-mobilehub-1952717818/user-log-photos/"+fileDir+currentPicNumber[0]+".jpg";
        currentImage.setImageURI(Uri.parse(imagePath));
        imageText.setText("Image ("+(currentPicNumber[0]+1)+"/5)");
        previous.setVisibility(View.INVISIBLE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPicNumber[0]++;
                String imagePath = "https://s3.amazonaws.com/tugapp-userfiles-mobilehub-1952717818/user-log-photos/"+fileDir+currentPicNumber[0]+".jpg";
                currentImage.setImageURI(Uri.parse(imagePath));
                imageText.setText("Image ("+(currentPicNumber[0]+1)+"/5)");
                previous.setVisibility(View.VISIBLE);
                if(currentPicNumber[0]==4){
                    next.setVisibility(View.INVISIBLE);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPicNumber[0]--;
                String imagePath = "https://s3.amazonaws.com/tugapp-userfiles-mobilehub-1952717818/user-log-photos/"+fileDir+currentPicNumber[0]+".jpg";
                currentImage.setImageURI(Uri.parse(imagePath));
                imageText.setText("Image ("+(currentPicNumber[0]+1)+"/5)");
                next.setVisibility(View.VISIBLE);
                if(currentPicNumber[0]==0){
                    previous.setVisibility(View.INVISIBLE);
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box.dismiss();
            }
        });


        box.setView(imageViewer);
        box.show();
    }

    private class getCloudInfo extends AsyncTask<Void, Void, Void>{

        ProgressDialog pd;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(UserLogDetailedInfo.this);
            pd.setTitle("Getting Info");
            pd.setMessage("Please wait!");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PaginatedList<UserLogsDO> cloudDOs = dynamoDBMapper.scan(UserLogsDO.class, new DynamoDBScanExpression());
            for (UserLogsDO log : cloudDOs) {
                UserLogItem cloudItem = new UserLogItem(log.getUserlogid(), log.getUsername(), log.getDate(), log.getLocation(), log.getRecClass(), log.getSetClass(), log.getResultStatus(), log.getUserlogid(), log.getAdminApprovedName(), log.getOtherUnknownText(), log.getFactors(), 1);
                allLogs.add(cloudItem);
            }
            for (int i = 0; i < allLogs.size(); i++) {
                if (userLogID.equals(allLogs.get(i).getUserLogID())) {
                    userLogItem = allLogs.get(i);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setText();
            pd.dismiss();
        }
    }

    }


