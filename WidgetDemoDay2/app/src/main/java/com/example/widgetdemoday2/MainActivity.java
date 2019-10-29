package com.example.widgetdemoday2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView selectionText;
    RadioGroup radioGroup;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectionText = findViewById(R.id.id_textView);
        radioGroup = findViewById(R.id.id_radioGroup);
        imageView = findViewById(R.id.id_leftImage);

        imageView.setImageResource(R.drawable.airplane);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_radioA)
                    selectionText.setText("A");
                if(checkedId == R.id.id_radioB)
                    selectionText.setText("B");
                if(checkedId == R.id.id_radioC) {
                    selectionText.setText("C");
                    Toast myToast = Toast.makeText(MainActivity.this,"C is the best", Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
    }
}
