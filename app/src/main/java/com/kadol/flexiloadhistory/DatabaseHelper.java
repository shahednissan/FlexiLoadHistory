package com.kadol.flexiloadhistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Nafi on 4/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="BalanceDatabase.db";
    public static final String BALANCE_TABLE_NAME="messages";
    public static final String BALANCE_COLUMN_ID="id";
    public static final String BALANCE_COLUMN_TIME="time";
    public static final String BALANCE_COLUMN_TAKA="taka";
    public static final String BALANCE_COLUMN_DATE="date";
    public static final String BALANCE_COLUMN_OPERATOR="operator";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+BALANCE_TABLE_NAME+" ("+
                BALANCE_COLUMN_ID+" integer primary key, "+
                BALANCE_COLUMN_TIME+" text, "+
                BALANCE_COLUMN_DATE+" text, "+
                BALANCE_COLUMN_OPERATOR+" integer, "+
                BALANCE_COLUMN_TAKA+" integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onDelete(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+BALANCE_TABLE_NAME);
        onCreate(db);
    }

    public void insert(String time, String date, int operator, int taka){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(BALANCE_COLUMN_TIME,time);
        contentValues.put(BALANCE_COLUMN_DATE,date);
        contentValues.put(BALANCE_COLUMN_OPERATOR,operator);
        contentValues.put(BALANCE_COLUMN_TAKA,taka);


        db.insert(BALANCE_TABLE_NAME,null,contentValues);
    }

    public ArrayList<FlexiLoadUnitCell> sendData(){
        ArrayList<FlexiLoadUnitCell> arrayList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from "+BALANCE_TABLE_NAME,null);

        res.moveToFirst();

        while(res.isAfterLast()==false){

            //int id=res.getInt(res.getColumnIndex(DatabaseHelper.BALANCE_COLUMN_ID));
            String time=res.getString(res.getColumnIndex(DatabaseHelper.BALANCE_COLUMN_TIME));
            String date=res.getString(res.getColumnIndex(DatabaseHelper.BALANCE_COLUMN_DATE));
            int taka=res.getInt(res.getColumnIndex(DatabaseHelper.BALANCE_COLUMN_TAKA));
            int operator=res.getInt(res.getColumnIndex(DatabaseHelper.BALANCE_COLUMN_OPERATOR));

            arrayList.add(0,new FlexiLoadUnitCell(date,time,taka,operator));

            res.moveToNext();
        }
        res.close();

        return arrayList;
    }
}
