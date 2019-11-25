package com.example.listviewpractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Song> playList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.id_listView);
        playList = new ArrayList<>();
        playList.add(new Song("Stargazing", R.drawable.astroworld, "Travis Scott"));
        playList.add(new Song("Hot", R.drawable.youngthug, "Young Thug"));
        playList.add(new Song("Highest In the Room", R.drawable.highestintheroom, "Travis Scott"));

        CustomAdapter customAdapter = new CustomAdapter(this,R.layout.adapter_custom,playList);
        listView.setAdapter(customAdapter);

    }
    public class CustomAdapter extends ArrayAdapter<Song> {
        List<Song> list;
        Context context;
        int xmlResource;
        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
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
            TextView song = adapterView.findViewById(R.id.id_adapter_textViewSong);
            ImageView imageView = adapterView.findViewById(R.id.id_adapter_imageView);
            TextView artist = adapterView.findViewById(R.id.id_adapter_textViewArtist);
            Button button = adapterView.findViewById(R.id.id_adapter_button);

            song.setText("Song: " + list.get(position).getName());
            imageView.setImageResource(list.get(position).getImage());
            artist.setText("Artist: " + list.get(position).getArtist());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Playing " + list.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            });

            return adapterView;

        }
    }

    public class Song{
        private String name;
        private int image;
        private String artist;
        public Song (String name, int image, String artist){
            this.name = name;
            this.image = image;
            this.artist = artist;
        }

        public String getName() {
            return name;
        }

        public int getImage() {
            return image;
        }

        public String getArtist() {
            return artist;
        }
    }
}
