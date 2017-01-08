package com.example.asus.otpclippr;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    CheckBox copyClip, createNotif ;
    Button btn ;
    Button stop ;
    private BroadcastReceiver br ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*readerService.bindListener(new smsListener(){
            @Override
            public void messageReceived(String messageText) {
                Log.d("Message??",messageText) ;

            }
        }) ;*/
        setContentView(R.layout.main);
        Log.d("Main Activity","AWS") ;
        /*copyClip = (CheckBox)findViewById(R.id.checkBox) ;
        createNotif = (CheckBox)findViewById(R.id.checkBox2) ;*/
        btn = (Button)findViewById(R.id.button) ;
        stop = (Button)findViewById(R.id.button2) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean res = false ;
                //res = isMyServiceRunning(readerService.class) ;
                Intent intent = new Intent("some string") ;
                intent.setClass(MainActivity.this,StealthService.class) ;
                //intent.setAction("android.provider.Telephony.SMS_RECEIVED") ;
                /*intent.putExtra("flag1",copyClip.isChecked());
                intent.putExtra("flag2",createNotif.isChecked()) ;*/
                //Log.d("Checkpoint1","calling broadcast") ;
                startService(intent) ;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StealthService service = new StealthService() ;
                service.onDestroy();
                Toast.makeText(MainActivity.this,"Process stopeed",Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*public boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE) ;
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                return true ;
            }
        }
        return false ;
    }*/
}
