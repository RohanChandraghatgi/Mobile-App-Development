package com.example.widgetquizone;

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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView text3;
    EditText editText1, editText2;
    Button ok;
    Switch aSwitch;
    ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text3 = findViewById(R.id.id_textView_Check);
        editText1 = findViewById(R.id.id_editText_Pass);
        editText2 = findViewById(R.id.id_editText_Reenter);
        ok = findViewById(R.id.id_button_ok);
        aSwitch = findViewById(R.id.id_switch);
        aSwitch.setEnabled(false);

        list.add("test");
        list.add("123");
        list.add("password");
        list.add("abc");


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(list.contains(s.toString())) {
                    text3.setTextColor(Color.RED);
                    text3.setText("password already used");
                }
                else {
                    text3.setTextColor(Color.GREEN);
                    text3.setText("password not used previously");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText1.getText().toString().equals(editText2.getText().toString()) && !list.contains(editText1.getText().toString())) {
                    aSwitch.setText("Match");
                    aSwitch.setChecked(true);
                }
                else {
                    aSwitch.setText("Doesn't Match");
                    aSwitch.setChecked(false);
                }
            }
        });
    }
}
