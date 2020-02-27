package com.example.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NumberActivity extends AppCompatActivity {
    Button closeButton;
    EditText enterText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        closeButton = findViewById(R.id.id_close);
        enterText = findViewById(R.id.id_editText);

        Toast.makeText(this, getIntent().getStringExtra("TEST"), Toast.LENGTH_SHORT).show();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendInfoBack = new Intent();
                sendInfoBack.putExtra(MainActivity.INTENT_CODE,enterText.getText().toString());
                setResult(RESULT_OK, sendInfoBack);

                finish();
            }
        });
    }
}
