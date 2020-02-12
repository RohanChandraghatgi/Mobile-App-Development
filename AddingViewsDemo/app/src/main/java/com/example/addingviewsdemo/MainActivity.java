package com.example.addingviewsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = findViewById(R.id.id_layout);

        textView = new TextView(this);
        textView.setId(View.generateViewId());
        textView.setText("Hello Everyone");

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);

        constraintLayout.addView(textView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(textView.getId(),constraintSet.TOP,constraintLayout.getId(),constraintSet.TOP);
        constraintSet.connect(textView.getId(),constraintSet.LEFT,constraintLayout.getId(),constraintSet.LEFT);
        constraintSet.connect(textView.getId(),constraintSet.BOTTOM,constraintLayout.getId(),constraintSet.BOTTOM);
        constraintSet.connect(textView.getId(),constraintSet.RIGHT,constraintLayout.getId(),constraintSet.RIGHT);

        constraintSet.setHorizontalBias(textView.getId(),.5f);
        constraintSet.setVerticalBias(textView.getId(),.5f);

        constraintSet.applyTo(constraintLayout);

    }
}
