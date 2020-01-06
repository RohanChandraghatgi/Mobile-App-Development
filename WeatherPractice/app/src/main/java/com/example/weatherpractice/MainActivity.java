package com.example.weatherpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncThread myThread = new AsyncThread();
        myThread.execute();

        for(int i = 0; i < 100; i++)
            Log.d("TAG","UI Thread" + i);
    }
    public class AsyncThread extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i = 0; i < 100; i++)
                Log.d("TAG","Background Thread" + i);
            return null;
        }
    }
}
