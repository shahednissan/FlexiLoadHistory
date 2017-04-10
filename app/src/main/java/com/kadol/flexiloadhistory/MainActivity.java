package com.kadol.flexiloadhistory;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

    ArrayList<FlexiLoadUnitCell> arrayList=new ArrayList<>();

    private final String READ_SMS= Manifest.permission.READ_SMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(getApplicationContext(),READ_SMS)!= PackageManager.PERMISSION_GRANTED){
            //The permission is not granted yet so asked for the permission.
            ActivityCompat.requestPermissions(this,new String[]{READ_SMS},123);

        }else{
            //The permission has been granted already
            readSms();
        }

        ListView listView=(ListView) findViewById(R.id.list);

        FlexiLoadListAdapter adapter=new FlexiLoadListAdapter(this,arrayList);

        listView.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Check whether the permission is granted or not
        switch(requestCode){
            case 123:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //User granted the permission
                    Toast.makeText(getApplicationContext(),"You have granted the permission",Toast.LENGTH_LONG).show();
                    readSms();
                }else{
                    //User denied the permission
                    Toast.makeText(getApplicationContext(),"Without this permission this app will not work",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void readSms() {

        Cursor cursor=getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                String msg;
                String phoneNumber=cursor.getString(2);

                if(phoneNumber.equals("FlexiLoad")){

                    String body=cursor.getString(12);
                    String taka=getTakaAmountDone(body);

                    Long timeMili=cursor.getLong(4);
                    String date=getDateFormatDone(timeMili);
                    Log.v("Watch for me",date);

                    String time=getTimeFormatDone(timeMili);
                    Log.v("Watch for me",time);

                    arrayList.add(new FlexiLoadUnitCell(date,time,taka));
                }

            }while(cursor.moveToNext());
        }

    }

    private String getDateFormatDone(long time) {

        Date date = new Date(time);

        java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("EEE, dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private String getTimeFormatDone(long time) {

        Date date = new Date(time);

        java.text.SimpleDateFormat dateFormat=new java.text.SimpleDateFormat("h:mm:ss a");
        return dateFormat.format(date);
    }

    private String getTakaAmountDone(String body){

        String taka="";
        if(body.contains("TK")){
            int startIndex=body.indexOf("TK");
            int endIndex=body.indexOf(".");
            taka=body.substring(startIndex,endIndex);
        }

        return taka;
    }

}
