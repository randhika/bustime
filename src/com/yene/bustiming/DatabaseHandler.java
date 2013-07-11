package com.yene.bustiming;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "butStopFave";
 
    // My bus stop ID table name
    private static final String TABLE_LOGS = "mybusStop";
    
  
 
    // logManager Table Columns names
    private static final String KEY_ID = "id"; 
    private static final String KEY_BUS_STOP_ID="stopId";
    private static final String KEY_MSG="direction";
    private static final String KEY_TIME="time";

	private static final String TAG = "DatabaseHandler";
    
     
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG,"DB started...");
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    	 Log.d(TAG,"Table Created...");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LOGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_BUS_STOP_ID + " TEXT, "
                + KEY_MSG + " TEXT, "
        		+ KEY_TIME +" TEXT "+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        
        Log.d(TAG,"Table Created...");
      
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        // Create tables again
        onCreate(db);
    }
    
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addFavouritStop(MyBusStopObject mystop) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_BUS_STOP_ID, mystop.getId());
        values.put(KEY_MSG, mystop.getDirection()); // Contact Type
        values.put(KEY_TIME, mystop.getTime());// time of contact
 
        // Inserting Row
        db.insert(TABLE_LOGS, null, values);
        db.close(); // Closing database connection
    }
  
 
    // Getting single contact LOG
    MyBusStopObject getContact(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_LOGS, new String[] { KEY_ID,KEY_MSG, KEY_TIME,}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        MyBusStopObject contact = new MyBusStopObject(cursor.getString(1),cursor.getString(2));
        // return contact
        return contact;
    }
    
    String getLastContact(){
    	// Select Last Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOGS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
    	if(cursor.moveToLast()){  
    		String lastRow= "Number :"+cursor.getString(1)+" Type: "+cursor.getString(2)+" Time: "+cursor.getString(3);
    		cursor.close();
    	return lastRow;
    	}
    	return "Unable to get Logs Please try later.";
    }
 
    // Getting All Favour Bus Stop
    public List<MyBusStopObject> getAllContacts() {
        List<MyBusStopObject> contactList = new ArrayList<MyBusStopObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOGS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	MyBusStopObject   myFavStop = new MyBusStopObject("", "");
            	myFavStop.setId(cursor.getString(0));
                // Adding contact to list
                contactList.add(myFavStop);
            } while (cursor.moveToNext());
        }
        // return contact list
        db.close();
        return contactList;
    }
    // Updating single contact
    public int updateContact(MyBusStopObject myStop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, myStop.getId());
        values.put(KEY_MSG, myStop.getDirection());
        // updating row
        return db.update(TABLE_LOGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(myStop.getId()) });
    }
 
    // Deleting single contact
    public void deleteContact( MyBusStopObject myStop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGS
        		, KEY_ID + " = ?",
                new String[] { String.valueOf(myStop.getId()) });
        db.close();
    }
    

 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
 
}


