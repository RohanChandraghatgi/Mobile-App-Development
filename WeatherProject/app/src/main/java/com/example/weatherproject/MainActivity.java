package com.example.weatherproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    JSONObject jsonWeather;
    JSONObject jsonForecast;
    Button search;
    String zip;
    TextView textViewCurrent, textViewTown;
    ListView listView;
    ArrayList<FutureWeather> list;

    Context contextC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_editText);
        search = findViewById(R.id.id_button_search);
        listView = findViewById(R.id.id_ListView);
        contextC = this;

        textViewCurrent = findViewById(R.id.id_textView_current);
        textViewTown = findViewById(R.id.id_textView_town);
        list = new ArrayList<>();



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
                list.clear();
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
                SimpleDateFormat sdf = new SimpleDateFormat("M/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                for(int x = 0; x < jsonForecast.getJSONArray("list").length(); x++) {
                    list.add(new FutureWeather(sdf.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(x).getString("dt")) * 1000)), timeFormat.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(x).getString("dt")) * 1000)), (int) Math.round(jsonForecast.getJSONArray("list").getJSONObject(x).getJSONObject("main").getDouble("temp")), jsonForecast.getJSONArray("list").getJSONObject(x).getJSONArray("weather").getJSONObject(0).getString("icon")));
                }
                //list.add(new FutureWeather(sdf.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(1).getString("dt"))*1000)),timeFormat.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(1).getString("dt"))*1000)),(int)Math.round(jsonForecast.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp")),jsonForecast.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("icon")));
                //list.add(new FutureWeather(sdf.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(2).getString("dt"))*1000)),timeFormat.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(2).getString("dt"))*1000)),(int)Math.round(jsonForecast.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp")),jsonForecast.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("icon")));
                //list.add(new FutureWeather(sdf.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(3).getString("dt"))*1000)),timeFormat.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(3).getString("dt"))*1000)),(int)Math.round(jsonForecast.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp")),jsonForecast.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("icon")));
                //list.add(new FutureWeather(sdf.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(4).getString("dt"))*1000)),timeFormat.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(4).getString("dt"))*1000)),(int)Math.round(jsonForecast.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp")),jsonForecast.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("icon")));

                Log.d("CHAN", list.get(0).getDate() + " " + list.get(0).getTime() + " " + list.get(0).getTemp() + " "  + list.get(0).getImage());
                Log.d("CHAN", list.get(1).getDate() + " " + list.get(1).getTime() + " " + list.get(1).getTemp() + " "  + list.get(1).getImage());
                Log.d("CHAN", list.get(2).getDate() + " " + list.get(2).getTime() + " " + list.get(2).getTemp() + " "  + list.get(2).getImage());
                Log.d("CHAN", list.get(3).getDate() + " " + list.get(3).getTime() + " " + list.get(3).getTemp() + " "  + list.get(3).getImage());
                Log.d("CHAN", list.get(4).getDate() + " " + list.get(4).getTime() + " " + list.get(4).getTemp() + " "  + list.get(4).getImage());

                CustomAdapter customAdapter = new CustomAdapter(contextC,R.layout.adapter_custom,list);
                listView.setAdapter(customAdapter);
            }catch(Exception e){
                Log.d("BRUH",e.toString());
            }
        }
    }
    public class FutureWeather{
        String date;
        String time;
        int temp;
        String image;

        public FutureWeather(String date, String time, int temp, String image) {
            this.date = date;
            this.time = time;
            this.temp = temp;
            this.image = image;
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

        public String getImage() {
            return image;
        }
    }
    public class CustomAdapter extends ArrayAdapter<FutureWeather> {
        List<FutureWeather> list;
        Context context;
        int xmlResource;
        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<FutureWeather> objects) {
            super(context, resource, objects);
            this.context = context;
            xmlResource = resource;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(xmlResource,null);
            TextView textViewDate = adapterView.findViewById(R.id.id_adapter_date);
            TextView textViewTime = adapterView.findViewById(R.id.id_adapter_time);
            TextView textViewTemp = adapterView.findViewById(R.id.id_adapter_temp);
            ImageView imageView = adapterView.findViewById(R.id.id_adapter_image);

            textViewDate.setText(list.get(position).getDate());
            textViewTime.setText(list.get(position).getTime());
            textViewTemp.setText(""+list.get(position).getTemp());
            try {
                String iconUrl = "http://openweathermap.org/img/w/" + list.get(position).getImage() + ".png";
                Picasso.with(context).load(iconUrl).resize(150,150).into(imageView);
            }catch(Exception e){
                e.printStackTrace();
            }
            return adapterView;

        }
    }
}