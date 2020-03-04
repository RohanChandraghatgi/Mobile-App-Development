package com.example.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView name;
    RadioGroup radioGroup;
    Button run;
    Button launch;
    int radioButtonSelected = 0;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.id_textView_name);
        radioGroup = findViewById(R.id.id_radioGroup);
        run = findViewById(R.id.id_button_run);
        launch = findViewById(R.id.id_button_launch);
        layout = findViewById(R.id.id_layout);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_radioToast)
                    radioButtonSelected = 1;
                else if(checkedId == R.id.id_radioColor)
                    radioButtonSelected = 2;
                else if(checkedId == R.id.id_radioCase)
                    radioButtonSelected = 3;
            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioButtonSelected == 1) {
                    Toast.makeText(MainActivity.this, "Toast Selected", Toast.LENGTH_SHORT).show();
                }
                else if(radioButtonSelected == 2){
                    layout.setBackgroundColor(Color.rgb((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
                }
                else if(radioButtonSelected == 3){
                    name.setText("ROHAN CHANDRAGHATGI");
                }
            }
        });
        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSend = new Intent();
                if(radioButtonSelected == 1)
                    intentToSend.putExtra("OUTPUT","The Toast Radiobutton was selected");
                else if(radioButtonSelected == 2)
                    intentToSend.putExtra("OUTPUT", "The Change Color Radiobutton was selected");
                else if(radioButtonSelected == 3)
                    intentToSend.putExtra("OUTPUT", "The Uppercase Radiobutton was selected");
                intentToSend.setClass(MainActivity.this, OutputActivity.class);
                startActivity(intentToSend);
            }
        });
    }
}
