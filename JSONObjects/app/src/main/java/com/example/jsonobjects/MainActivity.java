package com.example.jsonobjects;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textView.findViewById(R.id.id_text);

        JSONObject schoolInfo = new JSONObject();
        try {
            schoolInfo.put("id", 123456);
            schoolInfo.put("name", "Steve");
        }catch (JSONException e){
            e.printStackTrace();
        }
        JSONObject compsci = new JSONObject();
        try {
            compsci.put("grade","A");
            compsci.put("raw", 96);
            compsci.put("name", "AP Computer Science");
            schoolInfo.put("Block 1", compsci);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject bio = new JSONObject();
        try {
            bio.put("grade","A");
            bio.put("raw", 100);
            bio.put("name", "AP Biology");
            schoolInfo.put("Block 2", bio);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("TAG",schoolInfo.toString());
        Log.d("TAG", compsci.toString());
        Log.d("TAG", bio.toString());

        try {
            Log.d("TAG", schoolInfo.getJSONObject("Block 1").getString("name"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
