package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    JSONObject jsonWeather;
    JSONObject jsonForecast;
    Button search;
    String zip;
    TextView textViewCurrent, textViewTown;
    ListView listView;
    ArrayList<FutureWeather> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_editText);
        search = findViewById(R.id.id_button_search);
        listView = findViewById(R.id.id_ListView);

        textViewCurrent = findViewById(R.id.id_textView_current);
        textViewTown = findViewById(R.id.id_textView_town);

        //Long myTimeAsLong = 1578711600L*1000-18000;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
       // Log.d("TAG", (sdf.format(new Date(myTimeAsLong))));
        list = new ArrayList<>();

        try {
//i idiot cant use dt for everything
            list.add(new FutureWeather(sdf.format(new Date(jsonForecast.getJSONObject("0").getString("dt"))),timeFormat.format(jsonForecast.getJSONObject("0").getString("dt")),Integer.parseInt(jsonForecast.getJSONObject("0").getString("temp"))));
            list.add(new FutureWeather(sdf.format(new Date(jsonForecast.getJSONObject("1").getString("dt"))),timeFormat.format(jsonForecast.getJSONObject("1").getString("dt")),Integer.parseInt(jsonForecast.getJSONObject("1").getString("temp"))));
            list.add(new FutureWeather(sdf.format(new Date(jsonForecast.getJSONObject("2").getString("dt"))),timeFormat.format(jsonForecast.getJSONObject("2").getString("dt")),Integer.parseInt(jsonForecast.getJSONObject("2").getString("temp"))));
            list.add(new FutureWeather(sdf.format(new Date(jsonForecast.getJSONObject("3").getString("dt"))),timeFormat.format(jsonForecast.getJSONObject("3").getString("dt")),Integer.parseInt(jsonForecast.getJSONObject("3").getString("temp"))));
            list.add(new FutureWeather(sdf.format(new Date(jsonForecast.getJSONObject("4").getString("dt"))),timeFormat.format(jsonForecast.getJSONObject("4").getString("dt")),Integer.parseInt(jsonForecast.getJSONObject("4").getString("temp"))));
            Log.d("CHAN", list.get(0).getDate() + " "+ list.get(0).getTemp() + " " + list.get(0).getTime());

        }catch (Exception e){
            e.printStackTrace();
        }


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
                textViewCurrent.setText((jsonWeather.getJSONObject("main").getString("temp") + " °F"));
                textViewTown.setText(jsonWeather.getString("name"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public class FutureWeather{
        String date;
        String time;
        int temp;
        //int image;

        public FutureWeather(String date, String time, int temp) {
            this.date = date;
            this.time = time;
            this.temp = temp;
          //  this.image = image;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public int getTemp() {
            return temp;
        }

        //public int getImage() {
        //    return image;
        //}
    }
}