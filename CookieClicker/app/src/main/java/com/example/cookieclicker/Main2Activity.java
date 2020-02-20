package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {
    Button clickerButton,buttonPurchase;
    private int dropletsPerSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        clickerButton = findViewById(R.id.id_buttonClicker);
        buttonPurchase = findViewById(R.id.id_buttonPurchase);

        Intent intent = getIntent();
        dropletsPerSecond = intent.getIntExtra("DROPLETS_PER_SECOND",0);


        configureClickerButton();

        buttonPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropletsPerSecond+=5;
            }
        });
    }
    private void configureClickerButton(){
        clickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Main2Activity.this,MainActivity.class);
                intent1.putExtra("DROPLETS_PER_SECOND",dropletsPerSecond);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
    }
}
