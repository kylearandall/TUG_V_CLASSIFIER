package com.example.tug_v_classifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserLogItemDBAdapter {

    private static final String TAG = UserLogItemDBAdapter.class.getSimpleName();

    public static final String DB_NAME="userloglocal.db";
    public static final int DB_VERSION=1;

    public static final String TABLE_USERLOGLOCAL="table_userloglocal";
    public static final String COLUMN_ID = "id"; //data type: String NONNULL
    public static final String COLUMN_USERNAME = "username"; //data type: String NONNULL
    public static final String COLUMN_DATE = "date"; //data type: String NONNULL
    public static final String COLUMN_LOCATION = "location"; //data type: String NONNULL
    public static final String COLUMN_RECCLASS = "recclass"; //data type: String NONNULL
    public static final String COLUMN_SETCLASS = "setclass"; //data type: String NONNULL
    public static final String COLUMN_RESULTSTATUS = "resultstatus"; //data type: String NONNULL
    public static final String COLUMN_PICTURESTRINGS = "picturestrings"; //data type: String NONNULL
    public static final String COLUMN_ADMINDAPPROVEDNAME = "adminapprovedname"; //data type: String
    public static final String COLUMN_OTHERUNKNOWNTEXT = "otherunknowntext"; //data type: String
    public static final String COLUMN_FACTORS = "factors"; //data type: String
    public static final String COLUMN_UPLOADED = "uploaded"; //data type: Int (0 or 1) NONNULL

    public static String CREATE_TABLE_USERLOGLOCAL="CREATE TABLE "+TABLE_USERLOGLOCAL+" ("+COLUMN_ID+" TEXT PRIMARY KEY, "+
            COLUMN_USERNAME+" TEXT NOT NULL, "+COLUMN_DATE+" TEXT NOT NULL, "+COLUMN_LOCATION+" TEXT NOT NULL, "+
            COLUMN_RECCLASS+" TEXT NOT NULL, "+COLUMN_SETCLASS+" TEXT NOT NULL, "+COLUMN_RESULTSTATUS+" TEXT NOT NULL, "+
            COLUMN_PICTURESTRINGS+" TEXT NOT NULL, "+COLUMN_ADMINDAPPROVEDNAME+" TEXT, "+COLUMN_OTHERUNKNOWNTEXT+" TEXT, "+COLUMN_FACTORS+" TEXT, "+COLUMN_UPLOADED+" INTEGER NOT NULL )";

    private Context context;
    private SQLiteDatabase mSQLiteDB;
    private static UserLogItemDBAdapter userLogItemDBAdapterInstance;

    private UserLogItemDBAdapter(Context context){
        this.context=context;
        mSQLiteDB=new UserLogItemDBHelper(this.context, DB_NAME, null, DB_VERSION).getReadableDatabase();
    }

    public static UserLogItemDBAdapter getUserLogItemDBAdapterInstance(Context context){
        if(userLogItemDBAdapterInstance==null){
            userLogItemDBAdapterInstance= new UserLogItemDBAdapter(context);
        }
        return userLogItemDBAdapterInstance;
    }

    //methods for getting all user logs

    public Cursor getCursorForAllUserLogItems(){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, null, null, null, null, null, null);
        return  cursor;
    }

    public ArrayList<UserLogItem> getAllLocalUserLogs(){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForAllUserLogItems();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public void delteAll(){
        mSQLiteDB.execSQL("delete from "+TABLE_USERLOGLOCAL);
    }

    //Methods for getting userlogs based on specific queries

    public Cursor getCursorForSpecificUserLogID(String userLogID){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_ID+" = '%"+userLogID+"%'", null, null, null, null, null);
        return cursor;
    }

    public UserLogItem getSpecificUserLogByID(String userLogID){ 
        Cursor cursor = getCursorForSpecificUserLogID(userLogID);
        ArrayList<UserLogItem> localLogs =new ArrayList<>();
       if(cursor!=null&&cursor.getCount()>0){
           UserLogItem userLogItem = new UserLogItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
           localLogs.add(userLogItem);
       }cursor.close();
        return localLogs.get(0);
    }

    public Cursor getCursorForSpecificUserName(String userName){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_USERNAME+" LIKE '%"+userName+"%", null, null, null, null, null);
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsBySpecificUserName(String userName){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificUserName(userName);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorForSpecificDate(String date){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_DATE+" LIKE '%"+date+"%", null, null, null, null, null);
        return cursor;
    }
    public ArrayList<UserLogItem> getLogsBySpecificDate(String date){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificDate(date);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorForSpecificLocation(String location){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_LOCATION+" LIKE '%"+location+"%", null, null, null, null, null);
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsBySpecificlocation(String location){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificLocation(location);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorForSpecificRecClass(String recClass){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_RECCLASS+" LIKE '%"+recClass+"%", null, null, null, null, null);
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsBySpecificRecClass(String recClass){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificRecClass(recClass);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorForSpecificSetClass(String setClass){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_SETCLASS+" LIKE '%"+setClass+"%", null, null, null, null, null);
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsBySpecificSetClass(String setClass){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificSetClass(setClass);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorforSpecificResultStatus(String resultStatus){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_RESULTSTATUS+" LIKE '%"+resultStatus+"%", null, null, null, null, null);
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsBySpecificResultStatus(String resultStatus){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorforSpecificResultStatus(resultStatus);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorforSpecificAdminApprovalName(String adminName){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_ADMINDAPPROVEDNAME+" LIKE '%"+adminName+"%", null, null, null, null, null);
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsBySpecificAdminApprovalName(String adminName){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorforSpecificAdminApprovalName(adminName);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorForUserLogsNotUploaded(){
        int notUploaded = 0;
        Cursor cursor = mSQLiteDB.query(TABLE_USERLOGLOCAL,new String[]{COLUMN_ID,COLUMN_USERNAME,COLUMN_DATE,COLUMN_LOCATION,COLUMN_RECCLASS,COLUMN_SETCLASS,COLUMN_RESULTSTATUS,COLUMN_PICTURESTRINGS,COLUMN_ADMINDAPPROVEDNAME,COLUMN_OTHERUNKNOWNTEXT,COLUMN_FACTORS,COLUMN_UPLOADED}, COLUMN_UPLOADED+" LIKE '%"+notUploaded+"%'",null,null,null,null );
        return cursor;
    }

    public ArrayList<UserLogItem> getLogsNotUploaded(){
        ArrayList<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForUserLogsNotUploaded();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    // method for getting count of user logs
    public Cursor getCount(){
        Cursor cursor=mSQLiteDB.rawQuery("SELECT COUNT(*) FROM"+TABLE_USERLOGLOCAL, null);
        return cursor;
    }

    //method for inserting correctly classified user log

    public boolean insertCorrect(String userName, String date, String location, String towClass, String pictureStrings){
        ContentValues contentValues = new ContentValues();
        String userNameNoSpaces = userName.replaceAll("\\s+","");
        String dateNoSpaces=date.replaceAll("\\s+","");
        String dateNoSpecialChar=dateNoSpaces.replaceAll(":","");
        String dateNoDash = dateNoSpecialChar.replace("-","");
        String userID = userNameNoSpaces+dateNoDash;
        contentValues.put(COLUMN_ID, userID);
        contentValues.put(COLUMN_USERNAME, userName);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_RECCLASS, towClass);
        contentValues.put(COLUMN_SETCLASS, towClass);
        contentValues.put(COLUMN_RESULTSTATUS, "Correct");
        contentValues.put(COLUMN_PICTURESTRINGS, pictureStrings);
        contentValues.put(COLUMN_UPLOADED, 0);
        return mSQLiteDB.insert(TABLE_USERLOGLOCAL, null, contentValues)>0;
    }

    //method for inserting incorrectly classified or inconclusive user log

    public boolean insertOverride(String userName, String date, String location, String recClass, String setClass, String resultStatus, String pictureStrings, String adminUserName, String otherUnknown, String factors){
        ContentValues contentValues = new ContentValues();
        String userNameNoSpaces = userName.replaceAll("\\s+","");
        String dateNoSpaces=date.replaceAll("\\s+","");
        String dateNoSpecialChar=dateNoSpaces.replaceAll(":","");
        String dateNoDash = dateNoSpecialChar.replace("-","");
        String userID = userNameNoSpaces+dateNoDash;
        contentValues.put(COLUMN_ID, userID);
        contentValues.put(COLUMN_USERNAME, userName);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_RECCLASS, recClass);
        contentValues.put(COLUMN_SETCLASS, setClass);
        contentValues.put(COLUMN_RESULTSTATUS, resultStatus);
        contentValues.put(COLUMN_PICTURESTRINGS, pictureStrings);
        contentValues.put(COLUMN_ADMINDAPPROVEDNAME, adminUserName);
        contentValues.put(COLUMN_OTHERUNKNOWNTEXT, otherUnknown);
        contentValues.put(COLUMN_FACTORS, factors);
        contentValues.put(COLUMN_UPLOADED, 0);
        return mSQLiteDB.insert(TABLE_USERLOGLOCAL, null, contentValues)>0;
    }

    public boolean reinsertUserLog(UserLogItem userLogItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, userLogItem.getUserLogID());
        contentValues.put(COLUMN_USERNAME, userLogItem.getUserName());
        contentValues.put(COLUMN_DATE, userLogItem.getDate());
        contentValues.put(COLUMN_LOCATION, userLogItem.getLocation());
        contentValues.put(COLUMN_RECCLASS, userLogItem.getRecClass());
        contentValues.put(COLUMN_SETCLASS, userLogItem.getSetClass());
        contentValues.put(COLUMN_RESULTSTATUS, userLogItem.getResultStatus());
        contentValues.put(COLUMN_PICTURESTRINGS, userLogItem.getPictureStrings());
        contentValues.put(COLUMN_ADMINDAPPROVEDNAME, userLogItem.getAdminApprovedName());
        contentValues.put(COLUMN_UPLOADED, 1);
        return mSQLiteDB.insert(TABLE_USERLOGLOCAL, null, contentValues)>0;
    }


    public long insert(ContentValues contentValues){
        return mSQLiteDB.insert(TABLE_USERLOGLOCAL, null, contentValues);
    }

    public boolean deleteForUser(String username){
        return mSQLiteDB.delete(TABLE_USERLOGLOCAL, COLUMN_USERNAME+" = "+username, null)>0;
    }

    public boolean deleteUserLog(UserLogItem userLogItem){
        return mSQLiteDB.delete(TABLE_USERLOGLOCAL, COLUMN_ID+" = "+userLogItem.getUserLogID(), null)>0;
    }

    public void uploadedLog(String logID){
        mSQLiteDB.execSQL("UPDATE "+TABLE_USERLOGLOCAL+" SET "+COLUMN_UPLOADED+ " = 1 WHERE "+COLUMN_ID+" = "+logID);

    }


    private static class UserLogItemDBHelper extends SQLiteOpenHelper{

        public UserLogItemDBHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
            Log.i(TAG, "Inside onConfigure");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USERLOGLOCAL);
            Log.i(TAG, "Inside onCreate");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }




}
