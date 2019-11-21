package com.example.lifecycledemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "abcdef";

    TextView textView;
    Button button;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "Create");



        button = findViewById(R.id.id_button);
        textView =  findViewById(R.id.id_textView);

        if (savedInstanceState != null){
            counter = savedInstanceState.getInt(KEY, 0);
            textView.setText("Count:" + counter);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textView.setText("Count:" + counter);
            }
        });

    }//onCreate

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY,counter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG","Pause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG","Restart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG","Resume");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG","Start");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG","Stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG","Destroy");

    }
}//Main Activity
