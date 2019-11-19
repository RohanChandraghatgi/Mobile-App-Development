package com.example.orientationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.id_layout_text);
        button = findViewById(R.id.id_button);

        //if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        if(button != null)
            button.setText("Landscape Button");



    }
}
