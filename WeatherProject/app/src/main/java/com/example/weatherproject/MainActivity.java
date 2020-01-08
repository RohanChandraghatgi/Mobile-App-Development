package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    JSONObject jsonWeather;
    JSONObject jsonForecast;
    Button search;
    String zip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_editText);
        search = findViewById(R.id.id_button_search);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                zip = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncThread asyncThread = new AsyncThread();
                asyncThread.execute(zip);
            }
        });



    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {   //AsyncTask sending zip code


        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL urlForecast = new URL("https://api.openweathermap.org/data/2.5/forecast?appid=d165cb431bba386b434067ab6b0be227&units=imperial&zip=" + strings[0] + ",us");
                URLConnection urlConnectionForecast = urlForecast.openConnection();
                InputStream inputStreamForecast = urlConnectionForecast.getInputStream();
                String json = "";
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamForecast));
                while ((line = bufferedReader.readLine()) != null)
                    json += line + "\n";
                bufferedReader.close();
                jsonForecast = new JSONObject(json);

                URL urlWeather = new URL("https://api.openweathermap.org/data/2.5/weather?appid=d165cb431bba386b434067ab6b0be227&units=imperial&zip=" + strings[0] + ",us");
                URLConnection urlConnectionWeather = urlWeather.openConnection();
                InputStream inputStreamWeather = urlConnectionWeather.getInputStream();
                line = null;
                json = "";
                bufferedReader = new BufferedReader(new InputStreamReader(inputStreamWeather));
                while ((line = bufferedReader.readLine()) != null)
                    json += line + "\n";
                bufferedReader.close();
                jsonWeather = new JSONObject(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try{
                Log.d("TAG",jsonForecast.getJSONObject("city").getString("name"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}