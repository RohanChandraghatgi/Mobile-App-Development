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
    TextView textViewCurrent, textViewTown, textViewCurrentTime, textViewQuote;
    ListView listView;
    ArrayList<FutureWeather> list;

    ImageView imageViewCurrent;

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
        imageViewCurrent = findViewById(R.id.id_imageView_current);
        textViewCurrentTime = findViewById(R.id.id_textView_currentTime);
        textViewQuote = findViewById(R.id.id_textView_quote);

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
                textViewQuote.setText(getQuote(jsonWeather.getJSONArray("weather").getJSONObject(0).getString("icon")));
                Picasso.with(contextC).load("http://openweathermap.org/img/w/" + jsonWeather.getJSONArray("weather").getJSONObject(0).getString("icon")+ ".png").resize(300,300).into(imageViewCurrent);
                SimpleDateFormat sdf = new SimpleDateFormat("M/dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                SimpleDateFormat timeFormatCurrent = new SimpleDateFormat("M/dd h:mm");
                textViewCurrentTime.setText(timeFormatCurrent.format(jsonWeather.getLong("dt")*1000));
                for(int x = 0; x < jsonForecast.getJSONArray("list").length(); x++) {
                    list.add(new FutureWeather(sdf.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(x).getString("dt")) * 1000)), timeFormat.format(new Date(Long.parseLong(jsonForecast.getJSONArray("list").getJSONObject(x).getString("dt")) * 1000)), jsonForecast.getJSONArray("list").getJSONObject(x).getJSONObject("main").getDouble("temp_min"),jsonForecast.getJSONArray("list").getJSONObject(x).getJSONObject("main").getDouble("temp_max"), jsonForecast.getJSONArray("list").getJSONObject(x).getJSONArray("weather").getJSONObject(0).getString("icon")));
                }

                CustomAdapter customAdapter = new CustomAdapter(contextC,R.layout.adapter_custom,list);
                listView.setAdapter(customAdapter);
            }catch(Exception e){
                Log.d("BRUH",e.toString());
            }
        }
        public String getQuote(String icon){
            switch(icon){
                case "01d":
                    return "Some people are so much sunshine to the square inch - Walt Whitman";
                case "01n":
                    return "We all shine on...like the moon and the stars and the sun...we all shine on...come on and on and on... - John Lennon";
                case "02d":
                case "02n":
                    return "You can't make a cloudy day a sunny day, but can embrace it and decide it's going to be a good day after all. - Jane Lynch";
                case "03d":
                case "03n":
                    return "If you count the sunny and the cloudy days of the whole year, you will find that the sunshine predominates. - Ovid";
                case "04d":
                case "04n":
                    return "There are some cloudy days for the mind as well as for the world; and the man who has the most genius is twenty times a day in the clouds. - Laurent Angliviel de la Beaumelle";
                case "09d":
                case "09n":
                    return "Cold feet under a warm blanket, steam over an empty mug--rain splatters on dry window pane--open journals of closed memories... tears of laughter and joy of pain... schmaltz of diametric morning. - Val Uchendu";
                case "10d":
                case "10n":
                    return "Rain,\n" + "Aren’t you my soul’s joyful tears\n" + "only longing for the sky to be happy?";
                case "11d":
                case "11n":
                    return "When the dark clouds accompany us with the furious concert of Thunder, then the liberating rain will finally wipe away the tears from our cheeks. - Sir Kristian Goldmund Aumann";
                case "13d":
                case "13n":
                    return "A lot of people like snow. I find it to be an unnecessary freezing of water. - Carl Reiner";
                case "50d":
                case "50n":
                    return  "The mountain veiled in mist is not a hill; an oak tree in the rain is not a weeping willow. - Khalil Gibran";
            }
            return "";
        }
    }

    public class FutureWeather{
        String date;
        String time;
        double mintemp;
        double maxtemp;
        String image;

        public FutureWeather(String date, String time, double mintemp,double maxtemp, String image) {
            this.date = date;
            this.time = time;
            this.mintemp = mintemp;
            this.maxtemp = maxtemp;
            this.image = image;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public double getMinTemp() {
            return mintemp;
        }

        public double getMaxtemp(){return maxtemp;}

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
            textViewTemp.setText(""+list.get(position).getMinTemp() + "°/ " + list.get(position).getMaxtemp() + "°");
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