package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class Main2Activity extends AppCompatActivity {
    Button clickerButton,buttonPurchase;
    private int dropletsPerSecond;
    AtomicInteger droplets;
    TextView textViewShopDroplets;
    TextView textViewFaucetCost;
    MyThread myThreadShop;
    int cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        clickerButton = findViewById(R.id.id_buttonClicker);
        buttonPurchase = findViewById(R.id.id_buttonPurchase);
        textViewShopDroplets = findViewById(R.id.id_textView_ShopDroplets);
        textViewFaucetCost = findViewById(R.id.id_textView_FaucetCost2);

        droplets = new AtomicInteger();


        Intent intent = getIntent();
        dropletsPerSecond = intent.getIntExtra("DROPLETS_PER_SECOND",0);
        droplets.set(intent.getIntExtra("DROPLETS",0));
        textViewShopDroplets.setText("Droplets: " + droplets.get());

        cost = (int)(20*(1.0+(double)(dropletsPerSecond)/3));
        textViewFaucetCost.setText("Faucet: " + cost + " droplets");

        myThreadShop = new MyThread();
        myThreadShop.start();
        configureClickerButton();

        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(droplets.get() >= cost) {
                    dropletsPerSecond += 1;
                    droplets.set((droplets.get()-cost));
                    cost = (int)(20*(1.0+(double)(dropletsPerSecond)/3));
                    textViewFaucetCost.setText("Faucet: " + cost + " droplets");
                }
                else{
                    Toast.makeText(Main2Activity.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void configureClickerButton(){
        clickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Main2Activity.this,MainActivity.class);
                intent1.putExtra("DROPLETS_PER_SECOND",dropletsPerSecond);
                intent1.putExtra("DROPLETS", droplets.get());
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
    }
    public class MyThread extends Thread{
        public void run(){
            while(true) {
                try {
                    Thread.sleep(1000);
                    droplets.addAndGet(dropletsPerSecond);
                    textViewShopDroplets.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewShopDroplets.setText("Droplets: " + droplets);
                        }
                    });

                } catch (Exception e) {
                    System.out.println("error :(");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
