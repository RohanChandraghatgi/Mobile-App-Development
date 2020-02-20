package com.example.cookieclicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int xTouch;
    int yTouch;
    TextView clickText, dropletText, dropletsPerSecondText;
    ImageView imageView;
    ConstraintLayout constraintLayout;
    Context context;
    Button storeButton;
    private int droplets = 0, dropletsPerSecond = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.id_imageView);
        constraintLayout = findViewById(R.id.id_layout);
        dropletText = findViewById(R.id.id_dropletTextView);
        dropletsPerSecondText = findViewById(R.id.id_dropletPerSecondTextView);
        context = this;
        storeButton = findViewById(R.id.id_storeButton);

        configureStoreButton();

        final ScaleAnimation animationGlass = new ScaleAnimation(.75f, 1.0f, .75f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationGlass.setDuration(400);




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationGlass);
                droplets++;

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

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                constraintSet.setHorizontalBias(clickText.getId(),(float)xTouch/width);
                constraintSet.setVerticalBias(clickText.getId(),(float)yTouch/height);


                constraintSet.applyTo(constraintLayout);

                //TranslateAnimation animationClick = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,200,Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,200);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
                //animationClick.setDuration(2000);
                alphaAnimation.setDuration(2000);

                AnimationSet animationSet = new AnimationSet(false);

                //animationSet.addAnimation(animationClick);
                animationSet.addAnimation(alphaAnimation);


                clickText.startAnimation(animationSet);
                clickText.setVisibility(View.INVISIBLE);

                dropletText.setText(droplets + " droplets");
            }
        });

    }

    public class Item{
        int dropletsPerSecond;
        int image;
        public Item(int dropletsPerSecond, int image){
            this.dropletsPerSecond = dropletsPerSecond;
            this.image = image;
        }
        public int getDropletsPerSecond(){
            return dropletsPerSecond;
        }
        public int getImage(){
            return image;
        }
    }

    private void configureStoreButton(){
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("DROPLETS_PER_SECOND",dropletsPerSecond);

                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                dropletsPerSecond = data.getIntExtra("DROPLETS_PER_SECOND",0);
                Log.d("ROHAN",dropletsPerSecond + "");
            }
        }
    }
}
