package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "user_db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE = "create table "+TableContent.TableEntry.TABLE_NAME+"("+ TableContent.TableEntry.USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TableContent.TableEntry.FIRST_NAME +" TEXT,"+ TableContent.TableEntry.LAST_NAME +" TEXT,"+ TableContent.TableEntry.EMAIL + " TEXT,"+ TableContent.TableEntry.PHONE_NUMBER +" TEXT);";
    public static final String DROP_TABLE ="drop table if exists "+ TableContent.TableEntry.TABLE_NAME;
    public UserDBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.v("DBOperation","Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        Log.v("DBOperation","Table Droped");
        onCreate(db);
        Log.v("DBOperation","Table Re-Created");
    }
    public void insertUser(String first_name,String last_name,String email,String phone_number,SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(TableContent.TableEntry.FIRST_NAME,first_name);
        values.put(TableContent.TableEntry.LAST_NAME,last_name);
        values.put(TableContent.TableEntry.EMAIL,email);
        values.put(TableContent.TableEntry.PHONE_NUMBER,phone_number);

        db.insert(TableContent.TableEntry.TABLE_NAME,null,values);
        Log.v("DBOperation","Insertion Operation Done.");
    }
    public Cursor viewUser(SQLiteDatabase db){

        String[] projection = {TableContent.TableEntry.FIRST_NAME, TableContent.TableEntry.LAST_NAME, TableContent.TableEntry.EMAIL, TableContent.TableEntry.PHONE_NUMBER};
        Cursor cursor = db.query(TableContent.TableEntry.TABLE_NAME,projection,null,null,null,null,null);
        return cursor;
    }


}
