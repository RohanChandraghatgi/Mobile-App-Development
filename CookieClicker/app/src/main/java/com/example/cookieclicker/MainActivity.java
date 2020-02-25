package com.example.cookieclicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {
    TextView clickText, dropletText, dropletsPerSecondText;
    ImageView imageViewGlass, imageViewFaucet;
    ConstraintLayout constraintLayout;
    Button storeButton;
    AtomicInteger droplets;
    int dropletsPerSecond = 0;
    MyThread myThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewGlass = findViewById(R.id.id_imageView);
        constraintLayout = findViewById(R.id.id_layout);
        dropletText = findViewById(R.id.id_dropletTextView);
        dropletsPerSecondText = findViewById(R.id.id_dropletPerSecondTextView);
        storeButton = findViewById(R.id.id_storeButton);
        droplets = new AtomicInteger();

        constraintLayout.setBackgroundColor(Color.BLACK);
        configureStoreButton();

        final ImageView backgroundOne = findViewById(R.id.background_one);
        final ImageView backgroundTwo = findViewById(R.id.background_two);
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float height = backgroundOne.getHeight();
                final float translationY = height * progress;
                backgroundOne.setTranslationY(translationY);
                backgroundTwo.setTranslationY(translationY - height);
            }
        });
        animator.start();



        final ScaleAnimation animationGlass = new ScaleAnimation(.75f, 1.0f, .75f,1.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animationGlass.setDuration(400);

        myThread = new MyThread();
        myThread.start();


        imageViewGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animationGlass);
                droplets.incrementAndGet();

                clickText = new TextView(MainActivity.this);
                clickText.setId(View.generateViewId());
                clickText.setText("+1");
                clickText.setTextColor(Color.WHITE);

                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                clickText.setLayoutParams(params);

                constraintLayout.addView(clickText);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);

                constraintSet.connect(clickText.getId(),constraintSet.TOP,constraintLayout.getId(),constraintSet.TOP);
                constraintSet.connect(clickText.getId(),constraintSet.LEFT,constraintLayout.getId(),constraintSet.LEFT);
                constraintSet.connect(clickText.getId(),constraintSet.BOTTOM,constraintLayout.getId(),constraintSet.BOTTOM);
                constraintSet.connect(clickText.getId(),constraintSet.RIGHT,constraintLayout.getId(),constraintSet.RIGHT);


                constraintSet.setHorizontalBias(clickText.getId(),(float)(Math.random()*.5+.25));
                constraintSet.setVerticalBias(clickText.getId(),.5f);


                constraintSet.applyTo(constraintLayout);

                TranslateAnimation animationClick = new TranslateAnimation(0,0,0,-600);
                AlphaAnimation animationFade = new AlphaAnimation(1.0f,0.0f);
                animationClick.setDuration(2000);
                animationFade.setDuration(2000);

                AnimationSet animationSet = new AnimationSet(false);

                animationSet.addAnimation(animationClick);
                animationSet.addAnimation(animationFade);

                clickText.startAnimation(animationSet);
                clickText.setVisibility(View.INVISIBLE);

                dropletText.setText(droplets.get() + " droplets");
            }
        });

    }

    private void configureStoreButton(){
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("DROPLETS_PER_SECOND",dropletsPerSecond);
                intent.putExtra("DROPLETS",droplets.get());
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                droplets.set(data.getIntExtra("DROPLETS", 0));
                dropletText.setText(droplets.get() + " droplets");
                while(data.getIntExtra("DROPLETS_PER_SECOND",0) > dropletsPerSecond){
                    imageViewFaucet = new ImageView(MainActivity.this);
                    imageViewFaucet.setId(View.generateViewId());
                    Picasso.get().load(R.drawable.faucet).resize(175,175).into(imageViewFaucet);

                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    imageViewFaucet.setLayoutParams(params);


                    constraintLayout.addView(imageViewFaucet);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    constraintSet.connect(imageViewFaucet.getId(),constraintSet.TOP,constraintLayout.getId(),constraintSet.TOP);
                    constraintSet.connect(imageViewFaucet.getId(),constraintSet.LEFT,constraintLayout.getId(),constraintSet.LEFT);
                    constraintSet.connect(imageViewFaucet.getId(),constraintSet.BOTTOM,constraintLayout.getId(),constraintSet.BOTTOM);
                    constraintSet.connect(imageViewFaucet.getId(),constraintSet.RIGHT,constraintLayout.getId(),constraintSet.RIGHT);


                    constraintSet.setHorizontalBias(imageViewFaucet.getId(),(float)(Math.random()*.5+.25));
                    constraintSet.setVerticalBias(imageViewFaucet.getId(),(float)(Math.random()*.1+.85));


                    constraintSet.applyTo(constraintLayout);
                    dropletsPerSecond++;
                }
                dropletsPerSecondText.setText(dropletsPerSecond + " droplets/second");
            }
        }
    }
    public class MyThread extends Thread{
        public void run(){
            int x = 0;
            while(x==0) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("error :(");
                }
                droplets.addAndGet(dropletsPerSecond);
                dropletText.post(new Runnable() {
                    @Override
                    public void run() {
                        dropletText.setText(droplets + " droplets");
                    }
                });
            }
        }
    }
}
