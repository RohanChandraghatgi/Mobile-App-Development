package com.example.gpacalculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView name, gpa;
    Button launch;
    EditText enterName;

    static final int NUMBER_CODE = 1234;
    static final String INTENT_CODE = "SECRET CODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.id_name);
        gpa = findViewById(R.id.id_GPA);
        launch = findViewById(R.id.id_launch);
        enterName = findViewById(R.id.id_enterName);

        enterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToLoad = new Intent(MainActivity.this,GPAActivity.class);
                intentToLoad.putExtra("NAME",name.getText());
                startActivityForResult(intentToLoad,NUMBER_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NUMBER_CODE && resultCode == RESULT_OK)
            gpa.setText(""+data.getDoubleExtra(INTENT_CODE,0));
    }
}
