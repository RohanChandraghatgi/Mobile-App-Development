package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OutputActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        textView = findViewById(R.id.id_textView_output);

        textView.setText(getIntent().getStringExtra("OUTPUT"));
    }
}
