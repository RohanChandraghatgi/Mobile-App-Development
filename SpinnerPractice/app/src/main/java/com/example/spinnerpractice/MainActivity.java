package com.example.spinnerpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner prefix, finalList;
    EditText name;
    Button add;
    TextView total;
    ArrayList<String> list, dataBase;
    String currentName, currentPrefix;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefix = findViewById(R.id.id_spinner_prefix);
        name = findViewById(R.id.id_editText);
        add = findViewById(R.id.id_button_add);
        total = findViewById(R.id.id_textView_total);
        finalList = findViewById(R.id.id_spinner_list);


        list = new ArrayList<>();
        list.add("Mr.");
        list.add("Ms.");
        list.add("Dr.");
        list.add("Mrs.");
        list.add("Lord");

        dataBase = new ArrayList<>();

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        prefix.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2;
        arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dataBase);
        finalList.setAdapter(arrayAdapter2);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total.setText(currentPrefix + currentName);
                dataBase.add(currentPrefix + currentName);


            }
        });

        prefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPrefix = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentName = s + "";
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
