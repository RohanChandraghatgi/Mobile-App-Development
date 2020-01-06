package com.example.jsonpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.id_text);

        JSONObject nba = new JSONObject();
        try {
            nba.put("name", "National Basketball Association");
            nba.put("size", 30);
            nba.put("commissioner", "Adam Silver");
            nba.put("champion", "Toronto Raptors");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject lakers = new JSONObject();
        try {
            lakers.put("name", "Los Angeles Lakers");
            lakers.put("conference", "Western Conference");
            lakers.put("division", "Pacific Division");
            lakers.put("coach", "Frank Vogel");
            lakers.put("arena", "STAPLES Center");
            nba.put("team 1", lakers);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject steve = new JSONObject();
        try {
            steve.put("name", "Steve Ballmer");
            steve.put("age", 63);
            steve.put("height", "6 ft 5 in");
            steve.put("net worth ($bn)", 51.9);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        JSONObject clippers = new JSONObject();
        try {
            clippers.put("name", "Los Angeles Clippers");
            clippers.put("conference", "Western Conference");
            clippers.put("division", "Pacific Division");
            clippers.put("coach", "Doc Rivers");
            clippers.put("arena", "STAPLES Center");
            clippers.put("owner", steve);
            nba.put("team 2", clippers);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject nuggets = new JSONObject();
        try {
            nuggets.put("name", "Denver Nuggets");
            nuggets.put("conference", "Western Conference");
            nuggets.put("division", "Northwest Division");
            nuggets.put("coach", "Michael Malone");
            nuggets.put("arena", "Pepsi Center");
            nba.put("team 3", nuggets);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject luka = new JSONObject();
        try {
            luka.put("name", "Luka Doncic");
            luka.put("age", 20);
            luka.put("height", "6 ft 7 in");
            luka.put("weight", 218);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject kristaps = new JSONObject();
        try {
            kristaps.put("name", "Kristaps Porzingis");
            kristaps.put("age", 24);
            kristaps.put("height", "7 ft 3 in");
            kristaps.put("weight", 240);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject seth = new JSONObject();
        try {
            seth.put("name", "Seth Curry");
            seth.put("age", 29);
            seth.put("height", "6 ft 2 in");
            seth.put("weight", 185);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray players = new JSONArray();
        players.put(luka);
        players.put(kristaps);
        players.put(seth);

        JSONObject mavericks = new JSONObject();
        try {
            mavericks.put("name", "Dallas Mavericks");
            mavericks.put("conference", "Western Conference");
            mavericks.put("division", "Southwest Division");
            mavericks.put("coach", "Rick Carlisle");
            mavericks.put("arena", "American Airlines Travel Center");
            mavericks.put("players", players);
            nba.put("team 4", mavericks);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject rockets = new JSONObject();
        try {
            rockets.put("name", "Houston Rockets");
            rockets.put("conference", "Western Conference");
            rockets.put("division", "Southwest Division");
            rockets.put("coach", "Mike D\'Antoni");
            rockets.put("arena", "Toyota Center");
            nba.put("team 5", rockets);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            textView.setText(nba.toString(8));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
