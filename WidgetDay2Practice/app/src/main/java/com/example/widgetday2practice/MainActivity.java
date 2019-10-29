package com.example.widgetday2practice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.id_radioGroup);
        imageView = findViewById(R.id.id_imageView);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_radioHarry) {
                    imageView.setImageResource(R.drawable.harry);
                    Toast myToast = Toast.makeText(MainActivity.this, "You have selected Harry!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                if(checkedId == R.id.id_radioHermione) {
                    imageView.setImageResource(R.drawable.hermione);
                    Toast myToast = Toast.makeText(MainActivity.this, "You have selected Hermione!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                if(checkedId == R.id.id_radioRon) {
                    imageView.setImageResource(R.drawable.ron);
                    Toast myToast = Toast.makeText(MainActivity.this, "You have selected Ron!", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
    }
}
