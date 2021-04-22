package com.example.se328_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_data";
    public static final String COL1 = "userId";
    public static final String COL2 = "firstName";
    public static final String COL3 = "lastName";
    public static final String COL4 = "phoneNumber";
    public static final String COL5 = "emailAddress";
    public int lastId = 0;


    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (userId INTEGER PRIMARY KEY, " +
                " firstName TEXT, lastName TEXT,phoneNumber TEXT,emailAddress TEXT)";
        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(int id, String fName, String lName,String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, fName);
        contentValues.put(COL3, lName);
        contentValues.put(COL4, phone);
        contentValues.put(COL5, email);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    public Boolean checkUser(String ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+ " WHERE userId = ?", new String[]{ID});

        if(data.getCount() <= 0){
            return false;
        }
        else{
            return true;
        }
    }

    public Integer Delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "userId = ?", new String[] {id});
    }

    public Cursor getSpecificResult(String searchBy, String value){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+ " WHERE " + searchBy + " = ?", new String[]{value});
        if (data != null)
            data.moveToFirst();
        return data;
    }

    // Return everything inside a specific table
    public Cursor getListContents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void update(String id, String searchBy, String value){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE " + TABLE_NAME + " SET " + searchBy + " = '" + value + "' WHERE userId = " + id;

        db.execSQL(strSQL);
    }
}