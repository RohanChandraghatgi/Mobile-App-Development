package com.example.practicedaywidgets;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Switch switch1, switch2, switch3;
    TextView textColor, textVerify, textCheck, textSize;
    EditText verify, check;
    SeekBar seekBar;
    Button verifyButton, checkButton;
    boolean checked1, checked2, checked3;
    ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch1 = findViewById(R.id.id_switch1);
        switch2 = findViewById(R.id.id_switch2);
        switch3 = findViewById(R.id.id_switch3);
        textColor = findViewById(R.id.id_textView_color);
        textVerify = findViewById(R.id.id_textView_verify);
        textCheck = findViewById(R.id.id_textView_check);
        textSize = findViewById(R.id.id_textView_textSize);
        verify = findViewById(R.id.id_editText_verify);
        check = findViewById(R.id.id_editText_check);
        seekBar = findViewById(R.id.id_seekBar);
        verifyButton = findViewById(R.id.id_button_verify);
        checkButton = findViewById(R.id.id_button_check);

        switch1.setOnCheckedChangeListener(this);
        switch2.setOnCheckedChangeListener(this);
        switch3.setOnCheckedChangeListener(this);

        arr.add("rohangc123@gmail.com");
        arr.add("neelshah400@gmail.com");
        arr.add("mehariscool@gmail.com");
        arr.add("anishisgay@gmail.com");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verify.getText().toString().indexOf("@") < verify.getText().toString().indexOf(".com") && verify.getText().toString().contains("@") && verify.getText().toString().contains(".com"))
                    textVerify.setText("Verified");
                else
                    textVerify.setText("Not Verified");
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textCheck.setText("Not In Database");
                for(int x = 0; x < arr.size(); x++)
                    if(check.getText().toString().equals(arr.get(x)))
                        textCheck.setText("In Database");
            }
        });
    }
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView == switch1 && isChecked)
            checked1 = true;
        else if(buttonView == switch1)
            checked1 = false;
            Log.d("buttonView", ""+(buttonView == switch1));

        if(buttonView == switch2 && isChecked)
            checked2 = true;
        else if(buttonView == switch2)
            checked2 = false;

        if(buttonView == switch3 && isChecked)
            checked3 = true;
        else if(buttonView == switch3)
            checked3 = false;


        if(checked1 && checked2 && checked3)
            textColor.setTextColor(Color.BLUE);

        else if(checked1 && checked3)
            textColor.setTextColor(Color.RED);

        else if(checked3)
            textColor.setTextColor(Color.GREEN);

        else
            textColor.setTextColor(Color.BLACK);

    }
}
