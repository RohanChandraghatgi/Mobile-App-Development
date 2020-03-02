package com.example.finalreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ImageView imageView;
    Spinner spinner;
    RadioGroup radioGroup;
    ArrayList<String> list;
    Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.id_editText);
        imageView = findViewById(R.id.id_imageView);
        spinner = findViewById(R.id.id_spinner);
        radioGroup = findViewById((R.id.id_radioGroup));
        go = findViewById(R.id.id_launch);

        list = new ArrayList<>();
        list.add("Neel");
        list.add("Mehar");
        list.add("Anish");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    imageView.setImageResource(R.drawable.neel);
                    imageView.setTag(R.drawable.neel);

                }

                else if(position == 1) {
                    imageView.setImageResource(R.drawable.mehar);
                    imageView.setTag(R.drawable.mehar);

                }
                else {
                    imageView.setImageResource(R.drawable.anish);
                    imageView.setTag(R.drawable.anish);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_radioA) {
                    imageView.setImageResource(R.drawable.neel);
                    imageView.setTag(R.drawable.neel);

                }
                else if(checkedId == R.id.id_radioB) {
                    imageView.setImageResource(R.drawable.mehar);
                    imageView.setTag(R.drawable.mehar);
                }

                else {
                    imageView.setImageResource(R.drawable.anish);
                    imageView.setTag(R.drawable.anish);
                }

            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, editTextOutput.class);
                int x = (Integer)imageView.getTag();
                intent.putExtra("IMAGE", x);
                startActivity(intent);
            }
        });
    }
}
