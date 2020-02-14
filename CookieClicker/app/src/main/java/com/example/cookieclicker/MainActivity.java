package com.example.cookieclicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
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

    TextView clickText, dropletText, dropletPerSecondText, faucetCostText;
    ImageView imageView;
    ConstraintLayout constraintLayout;
    Context context;
    Button storeButton, purchaseButton;
    public int droplets = 0, dropletsPerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.id_imageView);
        constraintLayout = findViewById(R.id.id_layout);
        dropletText = findViewById(R.id.id_dropletTextView);
        dropletPerSecondText = findViewById(R.id.id_dropletPerSecondTextView);
        context = this;
        storeButton = findViewById(R.id.id_storeButton);
        purchaseButton = findViewById(R.id.id_buttonPurchase);


        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageView.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.INVISIBLE);
                    dropletText.setVisibility(View.INVISIBLE);
                    dropletPerSecondText.setVisibility(View.INVISIBLE);
                    storeButton.setText("Clicker");
                }
                else{
                    imageView.setVisibility(View.VISIBLE);
                    dropletText.setVisibility(View.VISIBLE);
                    dropletPerSecondText.setVisibility(View.VISIBLE);
                    storeButton.setText("Store");
                }
            }
        });

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

                constraintSet.setHorizontalBias(clickText.getId(),.5f);
                constraintSet.setVerticalBias(clickText.getId(),.5f);

                constraintSet.applyTo(constraintLayout);

                TranslateAnimation animationClick = new TranslateAnimation(0,0,200,-600);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
                animationClick.setDuration(2000);
                alphaAnimation.setDuration(2000);

                AnimationSet animationSet = new AnimationSet(false);

                animationSet.addAnimation(animationClick);
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
}
