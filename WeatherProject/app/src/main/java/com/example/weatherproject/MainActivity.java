package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_editText);

        AsyncThread asyncThread = new AsyncThread();
        asyncThread.execute();

    }

    public class AsyncThread extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?appid=d165cb431bba386b434067ab6b0be227&units=imperial&zip=08852,us");
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                String json = "";
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = bufferedReader.readLine()) != null)
                    json += line + "\n";
                //bufferedReader.close();
                JSONObject jsonObject = new JSONObject(json);
                Log.d("TAG", jsonObject.toString(2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

    }

}