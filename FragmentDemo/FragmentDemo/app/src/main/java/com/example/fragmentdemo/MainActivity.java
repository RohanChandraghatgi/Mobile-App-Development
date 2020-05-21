package com.example.fragmentdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements TopFragment.SendInfo {

    Button replaceButton;
    TextView textView;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceButton = findViewById(R.id.button);
        textView = findViewById(R.id.id_textView_fragmentDemo);

        fragmentManager = getSupportFragmentManager();

        //Begin first transaction
        fragmentTransaction = fragmentManager.beginTransaction();

        //Create bottom fragment and add to layout on bottom of XML
        BottonFragment bottonFragment = new BottonFragment();
        fragmentTransaction.add(R.id.id_bottom,bottonFragment);

        //commit the transaction (end)
        fragmentTransaction.commit();

        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction = fragmentManager.beginTransaction();
                TopFragment topFragment = new TopFragment();
                fragmentTransaction.replace(R.id.id_bottom,topFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void update(String str) {
        textView.setText(str);
    }
}
