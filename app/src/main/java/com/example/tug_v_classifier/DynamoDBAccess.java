package com.example.tug_v_classifier;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.PutItemOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.ScanOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Search;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.ArrayList;
import java.util.List;

public class DynamoDBAccess {
    private final String TAG = "DynamoDBAccess: ";

    private final String COGNITO_IDENTITY_POOL_ID = "us-east-2:a3ae2aa8-1f17-4a27-9ffc-7a19ba199b3d";
    private final Regions COGNITO_IDENTITY_POOL_REGION = Regions.US_EAST_2;
    private final String DYNAMODB_TABLE = "user";
    private Context context;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;

    private static volatile DynamoDBAccess instance;

    private DynamoDBAccess(Context context){
        this.context=context;
        credentialsProvider = new CognitoCachingCredentialsProvider(context, COGNITO_IDENTITY_POOL_ID, COGNITO_IDENTITY_POOL_REGION);

        dbClient = new AmazonDynamoDBClient(credentialsProvider);

        dbClient.setRegion(Region.getRegion(Regions.US_EAST_2));

        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);

    }

    public static synchronized DynamoDBAccess getInstance(Context context){
        if(instance==null){
            instance=new DynamoDBAccess(context);
        }
        return instance;
    }

    public List<Document> getAllUserLogsFromCloud() {
        ScanOperationConfig scanOperationConfig = new ScanOperationConfig();
        ArrayList<String> attributeList = new ArrayList<>();
        attributeList.add("id");
        attributeList.add("userName");
        attributeList.add("date");
        attributeList.add("location");
        attributeList.add("recClass");
        attributeList.add("setClass");
        attributeList.add("resultStatus");
        attributeList.add("adminApprovedName");
        attributeList.add("otherUnknownText");
        attributeList.add("factors");
        scanOperationConfig.withAttributesToGet(attributeList);
        Search searchResult = dbTable.scan(scanOperationConfig);
        return searchResult.getAllResults();
    }

    public Document addUserLogToCloud(UserLogItem userLogItem){
        Document doc = new Document();
        doc.put("id", userLogItem.getUserLogID());
        doc.put("userName", userLogItem.getUserName());
        doc.put("date", userLogItem.getDate());
        doc.put("location", userLogItem.getLocation());
        doc.put("recClass", userLogItem.getRecClass());
        doc.put("setClass", userLogItem.getSetClass());
        doc.put("resultStatus", userLogItem.getResultStatus());
        if(userLogItem.getAdminApprovedName()!=null){
            doc.put("adminApprovedName", userLogItem.getAdminApprovedName());
        }else{
            doc.put("adminApprovedName", "n/a");
        }
        if(userLogItem.getOtherUnknownText()!=null){
            if(!userLogItem.getOtherUnknownText().isEmpty()){
                doc.put("otherUnknownText", userLogItem.getOtherUnknownText());
            }
        }else{
            doc.put("otherUnknownText", "n/a");
        }
        if(userLogItem.getFactors()!=null){
            doc.put("factors", userLogItem.getFactors());
        }else{
            doc.put("factors", "n/a");
        }

        PutItemOperationConfig putItemOperationConfig = new PutItemOperationConfig();
        putItemOperationConfig.withReturnValues(ReturnValue.ALL_OLD);

        Document result = dbTable.putItem(doc, putItemOperationConfig);
        return result;

    }


}
