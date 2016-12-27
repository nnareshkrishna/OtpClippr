package com.example.asus.otpclippr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ASUS on 27-12-2016.
 */

public class readerService extends BroadcastReceiver {
    SmsManager smsManager = SmsManager.getDefault() ;
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras() ;
        try{
            if(bundle!=null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
