package com.example.widgetdayonepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textSlider, textDisplayColor;
    Switch aSwitch;
    EditText editText;
    Button testButton;
    SeekBar slide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textSlider = findViewById(R.id.id_textView_slider);
        textDisplayColor = findViewById(R.id.id_textView_Color);
        aSwitch = findViewById(R.id.id_switch);
        editText = findViewById(R.id.id_editText);
        testButton = findViewById(R.id.id_testButton);
        slide = findViewById(R.id.id_seekBar);
        aSwitch.setText("Slider Control");

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                slide.setEnabled(isChecked);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equalsIgnoreCase("red")){
                    textDisplayColor.setTextColor(Color.RED);
                }
                else if(s.toString().equalsIgnoreCase("blue")){
                    textDisplayColor.setTextColor(Color.BLUE);
                }
                else if(s.toString().equalsIgnoreCase("green")){
                    textDisplayColor.setTextColor(Color.GREEN);
                }
                else
                    textDisplayColor.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        slide.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int oldProgress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                testButton.setWidth(testButton.getWidth() + (progress-oldProgress)*10);
                oldProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
