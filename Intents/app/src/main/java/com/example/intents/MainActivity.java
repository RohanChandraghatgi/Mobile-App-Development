package com.example.intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button launchButton;
    TextView result;

    static final int NUMBER_CODE = 1234;
    static final String INTENT_CODE = "SECRET CODE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        launchButton = findViewById(R.id.id_launch);
        result = findViewById(R.id.id_result);

        launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intentToLoad = new Intent(MainActivity.this, NumberActivity.class);
                intentToLoad.putExtra("TEST","This is a test");
                startActivityForResult(intentToLoad,NUMBER_CODE);
                //startActivity(intentToLoad);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NUMBER_CODE && resultCode == RESULT_OK)
            result.setText(data.getStringExtra(INTENT_CODE));
    }
}
