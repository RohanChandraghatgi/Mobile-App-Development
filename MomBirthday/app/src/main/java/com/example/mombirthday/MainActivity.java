package com.example.mombirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

import static android.provider.LiveFolders.INTENT;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver receiver;
    SmsManager smsManager = SmsManager.getDefault();
    Handler handler = new Handler();
    String receivedNumber;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.id_textView);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},0);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},0);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},0);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] list = new SmsMessage[pdus.length];

                for (int x = 0; x < pdus.length; x++)
                    list[x] = SmsMessage.createFromPdu((byte[]) pdus[x], intent.getStringExtra("format"));

                receivedNumber = list[0].getOriginatingAddress();
                String message = list[0].getMessageBody().toLowerCase();
                Log.d("BRUH", message);
                if(message.contains("birthday pictures")) {
                    textView.setText("received");
                    sendMessage("https://photos.app.goo.gl/aezry3w59oPHbjjz9", 20);
                    sendMessage("Thanks for being the best mom in the world- Rohan",1000);
                }
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter messageFilter = new IntentFilter();
        messageFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver,messageFilter);
    }
    public void sendMessage(final String text, int time){
        handler.postDelayed(     new Runnable() {
            @Override
            public void run() {
                smsManager.sendTextMessage(receivedNumber,null,text,null,null);
                textView.setText("Waiting for response");
            }
        },time);
    }
}
