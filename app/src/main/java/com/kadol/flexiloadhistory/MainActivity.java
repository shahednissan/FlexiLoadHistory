package com.kadol.flexiloadhistory;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static DatabaseHelper dh;

    ArrayList<FlexiLoadUnitCell> arrayList=new ArrayList<>();

    private final String READ_SMS= Manifest.permission.READ_SMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dh=new DatabaseHelper(this);

//        long time=12345689;
//        String taka="TK 70";
//        dh.insert(time,taka);

        if(ContextCompat.checkSelfPermission(getApplicationContext(),READ_SMS)!= PackageManager.PERMISSION_GRANTED){
            //The permission is not granted yet so asked for the permission.
            ActivityCompat.requestPermissions(this,new String[]{READ_SMS},123);

        }else{

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            boolean previouslyStarted = prefs.getBoolean(getString(R.string.isThisTheFirstTime), false);
            if(!previouslyStarted) {
                readSms();
                SharedPreferences.Editor edit = prefs.edit();
                edit.putBoolean(getString(R.string.isThisTheFirstTime), Boolean.TRUE);
                edit.commit();
            }
            //The permission has been granted already
            populateListView();
        }

//        ListView listView=(ListView) findViewById(R.id.list);
//
//        FlexiLoadListAdapter adapter=new FlexiLoadListAdapter(this,arrayList);
//
//        listView.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Check whether the permission is granted or not
        switch(requestCode){
            case 123:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readSms();
                }else{
                    //User denied the permission
                    Toast.makeText(getApplicationContext(),"Without this permission this app will not work",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void readSms() {

        dh.onDelete();

        Cursor cursor=getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                String msg;
                String phoneNumber=cursor.getString(2);

                if(phoneNumber.equals("FlexiLoad")){
                    int operator=1;

                    String body=cursor.getString(12);
                    int taka=getGpTakaAmountDone(body);

                    Long timeMili=cursor.getLong(4);
                    String date=getDateFormatDone(timeMili);
                    Log.v("Watch for me",date);

                    String time=getTimeFormatDone(timeMili);
                    Log.v("Watch for me",time);

                    dh.insert(time,date,operator,taka);
                }

            }while(cursor.moveToNext());
        }
        cursor.close();

    }

    public void populateListView(){

        arrayList=dh.sendData();

        ListView listView=(ListView) findViewById(R.id.list);

        FlexiLoadListAdapter adapter=new FlexiLoadListAdapter(this,arrayList);

        listView.setAdapter(adapter);
    }

    private static String getDateFormatDone(long time) {

        Date date = new Date(time);

        java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("EEE, dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private static String getTimeFormatDone(long time) {

        Date date = new Date(time);

        java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("h:mm:ss a");
        return dateFormat.format(date);
    }

    private static int getGpTakaAmountDone(String body){

        int taka=0;
        if(body.contains("TK")){
            int startIndex=body.indexOf("TK")+3;
            int endIndex=body.indexOf(".");
            String takaMsg=body.substring(startIndex,endIndex);
            taka=Integer.parseInt(takaMsg);
        }

        return taka;
    }

    private static int getRobiTakaAmountDone(String body){

        int taka=0;
        if(body.contains("TK")){
            int startIndex=body.indexOf("TK")+3;
            int endIndex=body.indexOf(".");
            String takaMsg=body.substring(startIndex,endIndex);
            taka=Integer.parseInt(takaMsg);
        }

        return taka;
    }

    private static int getTeletalkTakaAmountDone(String body){

        int taka=0;
        if(body.contains("TK")){
            int startIndex=body.indexOf("TK")+3;
            int endIndex=body.indexOf(".");
            String takaMsg=body.substring(startIndex,endIndex);
            taka=Integer.parseInt(takaMsg);
        }

        return taka;
    }

    private static int getBlinkTakaAmountDone(String body){

        int taka=0;
        if(body.contains("TK")){
            int startIndex=body.indexOf("TK")+3;
            int endIndex=body.indexOf(".");
            String takaMsg=body.substring(startIndex,endIndex);
            taka=Integer.parseInt(takaMsg);
        }

        return taka;
    }

}
