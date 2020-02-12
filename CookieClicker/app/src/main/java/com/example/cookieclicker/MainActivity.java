package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView clickText;
    ImageView imageView;
    ConstraintLayout constraintLayout;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.id_imageView);
        constraintLayout = findViewById(R.id.id_layout);
        context = this;

        final ScaleAnimation animationGlass = new ScaleAnimation(.5f, 1.0f, .5f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationGlass.setDuration(400);






        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationGlass);
                clickText = new TextView(context);
                clickText.setId(View.generateViewId());
                clickText.setText("+1");

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                clickText.setLayoutParams(params);

                constraintLayout.addView(clickText);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(clickText.getId(),constraintSet.TOP,constraintLayout.getId(),constraintSet.TOP);
                constraintSet.connect(clickText.getId(),constraintSet.LEFT,constraintLayout.getId(),constraintSet.LEFT);
                constraintSet.connect(clickText.getId(),constraintSet.BOTTOM,constraintLayout.getId(),constraintSet.BOTTOM);
                constraintSet.connect(clickText.getId(),constraintSet.RIGHT,constraintLayout.getId(),constraintSet.RIGHT);

                constraintSet.setHorizontalBias(clickText.getId(),.5f);
                constraintSet.setVerticalBias(clickText.getId(),.5f);

                TranslateAnimation animationClick = new TranslateAnimation(0,0,200,-600);
                animationClick.setDuration(1000);
                constraintSet.applyTo(constraintLayout);
                clickText.startAnimation(animationClick);
                clickText.setVisibility(View.INVISIBLE);

            }
        });
    }
}
