package com.example.orientationpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView, textViewFob, textView2;
    Spinner spinner;
    Button button;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.id_textView);
        textView2 = findViewById(R.id.textView2);
        spinner = findViewById(R.id.id_spinner);
        button = findViewById(R.id.id_button);
        textViewFob = findViewById(R.id.id_fobText);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            list = new ArrayList<>();
            list.add("Neel");
            list.add("Mehar");
            list.add("Anish");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, list);
            spinner.setAdapter(arrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    textView.setText(list.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    textView.setText("all elements removed");
                    textView2.setText("all elements removed");

                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    arrayAdapter.notifyDataSetChanged();
                }
            });
        }
        else{
           textViewFob.setText("FOB THIS DOES NOT WORK IN LANDSCAPE");
        }
    }
}
