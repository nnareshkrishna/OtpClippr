package com.example.asus.otpclippr;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NNK on 06-01-2017.
 */

public class StealthService extends Service{
    String[] msg ;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver,filter) ;
        Log.d("Service","Service started") ;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);

    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction() ;
            if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
                Log.d("Entering check",":)") ;

                final Bundle bundle = intent.getExtras() ;
                try{
                    if(bundle!=null) {
                        SmsMessage smsMessage;

                        if (Build.VERSION.SDK_INT >= 19) { //KITKAT
                            SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                            smsMessage = msgs[0];
                        } else {
                            Object pdus[] = (Object[]) bundle.get("pdus");
                            smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
                        }
                        String message = smsMessage.getMessageBody() ;
                        Log.d("Message!!!",message) ;
                        //Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                        String[] checks = new String[]{"OTP","Transaction","Password","card","txn"} ;
                        msg = message.split(" ") ;
                        for(int i=0;i<checks.length;i++){
                            if(message.contains(checks[i]) || (message.contains(checks[i].toLowerCase()))){
                                checkforOTP(context,message) ;
                                Log.d("Check2","Checking for OTP") ;
                                break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };

    public List<Integer> getIndex(String[] msg, String test){
        List<Integer> res = new ArrayList<>();
        int count = 0;
        for(int i=0; i< msg.length;i++){
            if(msg[i].equals(test)) {
                res.add(i) ;
            }
        }
        return res ;
    }
    public void checkforOTP(Context context ,String message){
        /*int pos = message.indexOf("is") ;
        String msg1 = message.substring(pos,message.length()) ;
        String otp1 = msg1.split(" ")[0];
        String msg2 = message.substring(0,pos) ;
        String[] tempA = msg2.split(" ") ;
        String otp2 = tempA[tempA.length -1] ;*/
        List<Integer> index_pos = getIndex(msg,"is") ;
        for(int j=0; j<index_pos.size();j++) {
            int pos = index_pos.get(j) ;
            String otp1 = msg[pos - 1];
            String otp2 = msg[pos + 1];
            Log.d("MM", "" + pos);
            Log.d("NN", otp1);
            otp1 = otp1.replace(".","") ;
            otp2 = otp2.replace(".","") ;
            Log.d("OO", otp2);
            if (isValid(context, otp1)) {
                Toast.makeText(context, "OTP is" + otp1, Toast.LENGTH_SHORT).show();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("OTP", otp1);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "OTP " + otp1 + "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
            else if (isValid(context, otp2)) {
                Toast.makeText(context, "OTP is " + otp2, Toast.LENGTH_SHORT).show();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("OTP", otp2);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "OTP " + otp2 + "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d("check3", "Wrong call");
            }
        }

    }

    public boolean isValid(Context context,String x){
        x = x.replace(".","") ;
        Log.d("XX",x) ;
        if(x.length()==4 || x.length() ==6){
            if(x.matches("\\d+")){
                Log.d("Final","Success") ;
                //Toast.makeText(context,x,Toast.LENGTH_SHORT).show();
                return  true ;
            }
        }
        return  false ;
    }
}
