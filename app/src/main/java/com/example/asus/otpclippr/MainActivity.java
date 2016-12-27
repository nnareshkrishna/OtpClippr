package com.example.asus.otpclippr;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    CheckBox copyClip, createNotif ;
    Button btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        copyClip = (CheckBox)findViewById(R.id.checkBox) ;
        createNotif = (CheckBox)findViewById(R.id.checkBox2) ;
        btn = (Button)findViewById(R.id.button) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = false ;
                res = isMyServiceRunning(readerService.class) ;
                Intent intent = new Intent(MainActivity.this,readerService.class) ;
                intent.putExtra("flag1",copyClip.isChecked());
                intent.putExtra("flag2",createNotif.isChecked()) ;
                startActivity(intent);
            }
        });

    }

    public boolean isMyServiceRunning(Class<?> serviceClass){
        ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE) ;
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                return true ;
            }
        }
        return false ;
    }
}
