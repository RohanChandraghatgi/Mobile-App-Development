package com.example.textmessengerproject;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.provider.LiveFolders.INTENT;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver receiver;
    SmsManager smsManager = SmsManager.getDefault();
    Handler handler = new Handler();
    String receivedNumber;
    int state = 0;
    ArrayList<Integer> arrayList;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},0);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},0);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},0);

        arrayList = new ArrayList<>();
        textView = findViewById(R.id.id_textView);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle  = intent.getExtras();
                Object[] pdus = (Object[])bundle.get("pdus");
                SmsMessage[] list = new SmsMessage[pdus.length];

                for(int x = 0; x < pdus.length; x++)
                    list[x] = SmsMessage.createFromPdu((byte[])pdus[x],intent.getStringExtra("format"));

                receivedNumber = list[0].getOriginatingAddress();
                String message = list[0].getMessageBody().toLowerCase();
                if(message.contains("are") && message.contains("you") && message.contains("?") && (message.contains("why")||message.contains("where")) && arrayList.size() == 0){
                    state = 1;
                    arrayList.add(state);
                }
                else{
                    Log.d("ROHAN",arrayList.toString());
                    if(arrayList.get(arrayList.size()-1)==11){
                        if((message.contains("do") || message.contains("shoot"))) {
                            state = 5;
                            arrayList.add(state);
                        }
                        else if(message.contains("no")){
                            state = 10;
                            arrayList.add(10);
                        }
                    }
                    else if((message.contains("how") || message.contains("what")) && message.contains("happen")){
                        state = 5;
                        arrayList.add(state);
                    }
                    else if((message.contains("time")||message.contains("when"))&&(message.contains("here")||message.contains("work"))) {
                        state = 2;
                        arrayList.add(state);
                    }
                    else if(message.contains("how") && message.contains("get") &&(message.contains("there")||message.contains("vet"))) {
                        state = 3;
                        arrayList.add(state);
                    }
                    else if(((message.contains(" i ") || message.contains("me")) && message.contains("believe")) || message.contains("excuse")){
                        state = 4;
                        arrayList.add(state);
                    }
                    else if((message.contains("okay")||message.contains("hope")) && arrayList.contains(5)){
                        state = 10;
                        arrayList.add(state);
                    }
                    else if((message.contains("okay")||message.contains("hope")) && !arrayList.contains(5)) {
                        state = 11;
                        arrayList.add(state);
                    }
                    else
                        state = -1;
                        arrayList.add(state);
                }
                switch(state){
                    case 1:
                        textView.setText("Boss Detected: Executing Excuse");
                        sendMessage("Sorry, my dog ate my keys and I'm taking him to the vet.",5500);
                        break;
                    case 2:
                        textView.setText("Time Question Detected: Executing ETA");
                        sendMessage("I'm not sure. I think it may take me a couple hours at most. I'll check in with you later if I don't think I will make it.",6000);
                        break;
                    case 3:
                        textView.setText("Question of Sincerity Detected: Executing Explanation");
                        sendMessage("My neighbor offered to drive us",2000);
                        break;
                    case 4:
                        textView.setText("Question of Sincerity Detected: Executing Guilt-trip Explanation");
                        sendMessage("Look man I don't know what to tell you. My dog is really suffering here. I'm not afraid to go to upper management or HR if you keep harassing me like this.",11000);
                        break;
                    case 5:
                        textView.setText("Awaiting Explanation Detected: Executing Explanation");
                        sendMessage("I placed my keys on the table next to his bowl and began pouring him food, but he was really excited and jumped up to grab the food", 9000);
                        sendMessage("I guess he couldn't see the keys and ended up swallowing them",13000);
                        break;
                    case 10:
                        textView.setText("Submission Detected: Executing Resolution");
                        sendMessage("Thank you for being so understanding. I really appreciate it.", 3000);

                        break;
                    case 11:
                        textView.setText("Submission Before Explanation Detected: Executing Question");
                        sendMessage("You don't want to hear what happened?",5000);
                        break;
                    default:
                        textView.setText("Confusion Detected: Executing Reiteration");
                        sendMessage("Look I get it. Today was a really important day. But I gotta give priority to my dog; he's all I have...",4000);
                        break;
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
