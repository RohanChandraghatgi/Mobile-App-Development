package com.example.stringtokenizer;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.StringTokenizer;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text1, text2, text3, text4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = findViewById(R.id.textView_first_id);
        text2 = findViewById(R.id.textView_second_id);
        text3 = findViewById(R.id.textView_third_id);
        text4 = findViewById(R.id.textView_fourth_id);
        String punctuationSentence = "The tall man's son's dog watched the frisbee as it flew across the meadow!";
        StringTokenizer tokenizer = new StringTokenizer(punctuationSentence,"'!.,;", false);


    }
}
