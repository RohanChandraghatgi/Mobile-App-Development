package com.example.finalreview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class editTextOutput extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_output);
        imageView = findViewById(R.id.id_imageView_Output);
        int image = getIntent().getIntExtra("IMAGE", 0);
        imageView.setImageResource(image);
    }
}
