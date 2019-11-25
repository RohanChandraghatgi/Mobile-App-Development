package com.example.listviewdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.id_listView);
        arrayList = new ArrayList<>();
        arrayList.add("Bob");
        arrayList.add("Bill");
        arrayList.add("Jim");
        arrayList.add("Lim");
        arrayList.add("Dog");
        arrayList.add("Cat");
        arrayList.add("Smith");
        arrayList.add("Mehar");
        arrayList.add("Neel");
        arrayList.add("Anish");
        arrayList.add("Eric");
        arrayList.add("Tim");
        arrayList.add("Lit");
        arrayList.add("Gang");
        arrayList.add("Yeet");
        arrayList.add("EEERE");
        arrayList.add("Mange");
        arrayList.add("Spend");
        arrayList.add("Money");
        arrayList.add("Chandler");
        arrayList.add("2");




        CustomAdapter customAdapter = new CustomAdapter(this,R.layout.adapter_custom,arrayList);
        listView.setAdapter(customAdapter);

    }
    public class CustomAdapter extends ArrayAdapter<String>{

        List<String> list;
        Context context;
        int xmlResource;

        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            xmlResource = resource;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(xmlResource,null);
            TextView textView = adapterView.findViewById(R.id.id_adapter_text);
            Button button  = adapterView.findViewById(R.id.id_adapter_button);

            textView.setText("Name: " +list.get(position));
            button.setText("Position: " + position);

            return adapterView;
        }
    }
}
