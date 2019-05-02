package com.example.tug_v_classifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class UserLogUploadedDBAdapter {

    private static final String TAG = UserLogItemDBAdapter.class.getSimpleName();

    public static final String DB_NAME="userlogUploadedDB.db";
    public static final int DB_VERSION=1;

    public static final String TABLE_USERLOGUPLOADED="table_userloguploaded";
    public static final String COLUMN_ID = "user_id"; //data type: String NONNULL
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

    public static String CREATE_TABLE_USERLOGUPLOADED="CREATE TABLE "+TABLE_USERLOGUPLOADED+"("+COLUMN_ID+" TEXT PRIMARY KEY, "+
            COLUMN_USERNAME+" TEXT NOT NULL, "+COLUMN_DATE+" TEXT NOT NULL, "+COLUMN_LOCATION+" TEXT NOT NULL, "+
            COLUMN_RECCLASS+" TEXT NOT NULL, "+COLUMN_SETCLASS+" TEXT NOT NULL, "+COLUMN_RESULTSTATUS+" TEXT NOT NULL, "+
            COLUMN_PICTURESTRINGS+" TEXT NOT NULL, "+COLUMN_ADMINDAPPROVEDNAME+" TEXT, "+COLUMN_OTHERUNKNOWNTEXT+" TEXT, "+COLUMN_FACTORS+" TEXT, "+COLUMN_UPLOADED+" INTEGER NOT NULL )";

    private Context context;
    private SQLiteDatabase mSQLiteDB;
    private static UserLogUploadedDBAdapter userLogUploadedDBAdapterInstance;

    private UserLogUploadedDBAdapter (Context context){
        this.context=context;
        mSQLiteDB=new UserLogUploadedDBHelper(this.context, DB_NAME, null, DB_VERSION).getReadableDatabase();
    }

    public static UserLogUploadedDBAdapter getUserLogItemDBAdapterInstance(Context context){
        if(userLogUploadedDBAdapterInstance==null){
            userLogUploadedDBAdapterInstance= new UserLogUploadedDBAdapter(context);
        }
        return userLogUploadedDBAdapterInstance;
    }

    public Cursor getCursorForAllUserLogItems(){
        Cursor cursor=mSQLiteDB.query(TABLE_USERLOGUPLOADED, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_DATE, COLUMN_LOCATION, COLUMN_RECCLASS, COLUMN_SETCLASS, COLUMN_RESULTSTATUS, COLUMN_PICTURESTRINGS, COLUMN_ADMINDAPPROVEDNAME, COLUMN_OTHERUNKNOWNTEXT, COLUMN_FACTORS, COLUMN_UPLOADED}, null, null, null, null, null, null);
        return  cursor;
    }

    public ArrayList<UserLogItem> getAllUploadedUserLogs(){
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
        mSQLiteDB.execSQL("delete from "+TABLE_USERLOGUPLOADED);
    }

    public Cursor getCount(){
        Cursor cursor=mSQLiteDB.rawQuery("SELECT COUNT(*) FROM"+TABLE_USERLOGUPLOADED, null);
        return cursor;
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
        return mSQLiteDB.insert(TABLE_USERLOGUPLOADED, null, contentValues)>0;
    }

    private static class UserLogUploadedDBHelper extends SQLiteOpenHelper {

        public UserLogUploadedDBHelper( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
            db.execSQL(CREATE_TABLE_USERLOGUPLOADED);
            Log.i(TAG, "Inside onCreate");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
