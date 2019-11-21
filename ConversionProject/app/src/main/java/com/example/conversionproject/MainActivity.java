package com.example.conversionproject;

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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Spinner spinnerInitial, spinnerFinal;
    TextView textViewFinal;
    Button button;
    ArrayList<String> list;
    double num;
    String selectionInitial, selectionFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_editText);
        spinnerInitial = findViewById(R.id.id_spinner_initial);
        spinnerFinal = findViewById(R.id.id_spinner_final);
        textViewFinal = findViewById(R.id.id_textView_answer);
        button = findViewById(R.id.id_button);

        list = new ArrayList<>();
        list.add("Ice cubes"); //2 cheeze-it
        list.add("Watermelon"); //9 ice cubes
        list.add("Cheez-it"); //
        list.add("Doughnuts");// 3 Ice Cubes

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinnerInitial.setAdapter(arrayAdapter);
        spinnerFinal.setAdapter(arrayAdapter);

        editText.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                num = Double.parseDouble(s.toString());
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

        spinnerInitial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectionInitial = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerFinal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectionFinal = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectionInitial != null && selectionFinal != null){
                    num = Double.parseDouble(editText.getText().toString());
                    if(selectionInitial.equals("Watermelon"))
                        num*=9;
                    else if(selectionInitial.equals("Cheez-it"))
                        num/=2;
                    else if(selectionInitial.equals("Doughnuts")){
                        num*=3;
                    }
                    if(selectionFinal.equals("Watermelon"))
                        num/=9;
                    else if(selectionFinal.equals("Cheez-it"))
                        num*=2;
                    else if(selectionFinal.equals("Doughnuts"))
                        num/=3;
                    textViewFinal.setText(num + "");
                }
            }
        });

    }
}
