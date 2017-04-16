package com.kadol.flexiloadhistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nafi on 4/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="BalanceDatabase.db";
    public static final String BALANCE_TABLE_NAME="messages";
    public static final String BALANCE_COLUMN_ID="id";
    public static final String BALANCE_COLUMN_TIME="time";
    public static final String BALANCE_COLUMN_TAKA="taka";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+BALANCE_TABLE_NAME+" ("+BALANCE_COLUMN_ID+" integer primary key, "+
                BALANCE_COLUMN_TIME+" integer, "+BALANCE_COLUMN_TAKA+" text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDelete(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+BALANCE_TABLE_NAME);
        onCreate(db);
    }

    public void insert(Long time, String taka){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(BALANCE_COLUMN_TIME,time);
        contentValues.put(BALANCE_COLUMN_TAKA,time);

        db.insert(BALANCE_TABLE_NAME,null,contentValues);
    }
}
