package com.example.widgetquiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button play;
    ImageView imageView;
    TextView textTotal, textResult;
    int selection = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.id_radioGroup);
        play = findViewById(R.id.id_button_play);
        imageView = findViewById(R.id.id_imageView);
        textTotal = findViewById(R.id.id_textView_total);
        textResult = findViewById(R.id.id_textView2_result);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                imageView.setImageResource(R.drawable.cpuselection);
                textTotal.setText("Total");
                textResult.setText("Result");
                if(checkedId == R.id.id_radioButton_one)
                    selection = 1;
                else
                    selection = 2;
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rand = (int)(Math.random()*2)+1;

                if(selection != 0) {
                    if(rand == 1)
                        imageView.setImageResource(R.drawable.one);
                    else if(rand == 2)
                        imageView.setImageResource(R.drawable.two);


                    int total = selection + rand;
                    textTotal.setText(total + "");
                    if(total%2 == 0){
                        textResult.setText("You Win! :)");
                    }
                    else
                        textResult.setText("You Lose! :(");
                }
                else
                    Toast.makeText(MainActivity.this, "Please make a selection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
