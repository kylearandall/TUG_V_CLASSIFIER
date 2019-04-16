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

    public static String CREATE_TABLE_USERLOGLOCAL="CREATE TABLE "+TABLE_USERLOGLOCAL+"("+COLUMN_ID+" TEXT PRIMARY KEY, "+
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

    public List<UserLogItem> getAllLocalUserLogs(){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForAllUserLogItems();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    //Methods for getting userlogs based on specific queries

    public Cursor getCursorForSpecificUserName(String userName){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, COLUMN_USERNAME+" LIKE '%"+userName+"%", null, null, null, null, null);
        return cursor;
    }

    public List<UserLogItem> getLogsBySpecificUserName(String userName){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificUserName(userName);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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
    public List<UserLogItem> getLogsBySpecificDate(String date){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificDate(date);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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

    public List<UserLogItem> getLogsBySpecificlocation(String location){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificLocation(location);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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

    public List<UserLogItem> getLogsBySpecificRecClass(String recClass){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificRecClass(recClass);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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

    public List<UserLogItem> getLogsBySpecificSetClass(String setClass){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForSpecificSetClass(setClass);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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

    public List<UserLogItem> getLogsBySpecificResultStatus(String resultStatus){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorforSpecificResultStatus(resultStatus);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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

    public List<UserLogItem> getLogsBySpecificAdminApprovalName(String adminName){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorforSpecificAdminApprovalName(adminName);
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
                localLogs.add(userLogItem);
            }
        }
        cursor.close();
        return localLogs;
    }

    public Cursor getCursorForUserLogsNotUploaded(){
        int notUploaded = 0;
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGLOCAL, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS,COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS}, COLUMN_UPLOADED+" LIKE '%"+notUploaded+"%", null, null, null, null, null);
        return cursor;
    }

    public List<UserLogItem> getLogsNotUploaded(){
        List<UserLogItem> localLogs = new ArrayList<>();
        Cursor cursor = getCursorForUserLogsNotUploaded();
        if(cursor!=null&&cursor.getCount()>0){
            while(cursor.moveToNext()){
                UserLogItem userLogItem = new UserLogItem(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getInt(11));
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
        contentValues.put(COLUMN_ID, userName+"_"+date);
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
        contentValues.put(COLUMN_ID, userName+"_"+date);
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

    public long insert(ContentValues contentValues){
        return mSQLiteDB.insert(TABLE_USERLOGLOCAL, null, contentValues);
    }

    public boolean deleteForUser(String username){
        return mSQLiteDB.delete(TABLE_USERLOGLOCAL, COLUMN_USERNAME+" = "+username, null)>0;
    }

    public boolean uploadedLog(String username, String date){
        String logID = username+"_"+date;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_UPLOADED, 1);
        return mSQLiteDB.update(TABLE_USERLOGLOCAL, contentValues, COLUMN_ID+" = "+logID, null)>0;
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