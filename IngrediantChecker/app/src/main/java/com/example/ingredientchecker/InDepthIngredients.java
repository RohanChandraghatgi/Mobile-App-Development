package com.example.ingredientchecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InDepthIngredients extends AppCompatActivity {
    Button backButton;
    TextView textViewProductName, textViewBarcode, textViewIngredients, textViewAllergens, textViewAdditives;
    JSONObject jsonFoodItem;
    JSONObject jsonAdditives;
    Spinner spinner;
    WebView webView;

    ArrayAdapter<String> adapter;

    ArrayList<String> additives = new ArrayList<>();
    ArrayList<String> additivesURL = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_depth_ingredients);
        backButton = findViewById(R.id.id_indepth_button_Back);
        textViewProductName = findViewById(R.id.id_indepth_textView_ProductName);
        textViewBarcode = findViewById(R.id.id_indepth_textView_Barcode);
        textViewIngredients = findViewById(R.id.id_indepth_textView_Ingredients);
        textViewBarcode = findViewById(R.id.id_indepth_textView_Barcode);
        textViewAllergens = findViewById(R.id.id_indepth_textView_Allergens);
        textViewAdditives = findViewById(R.id.id_indepth_textView_Additives);
        spinner = findViewById(R.id.id_spinner);
        webView = findViewById(R.id.id_webView);

        InDepthIngredients.AsyncThread asyncThread = new InDepthIngredients.AsyncThread();
        asyncThread.execute(getIntent().getStringExtra("BARCODE"));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, additives);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#DCFFFD"));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                Typeface typeface = ResourcesCompat.getFont(InDepthIngredients.this, R.font.open_sans_light);
                ((TextView) parent.getChildAt(0)).setTypeface(typeface);

                webView.loadUrl(additivesURL.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }    public class AsyncThread extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {

            try{
                URL urlItem = new URL("http://world.openfoodfacts.org/api/v0/product/" + strings[0] + ".json");
                URLConnection urlConnectionItem = urlItem.openConnection();
                InputStream inputStreamItem = urlConnectionItem.getInputStream();
                String json = "";
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamItem));
                while((line = bufferedReader.readLine())!= null)
                    json+= line + "\n";
                bufferedReader.close();
                jsonFoodItem = new JSONObject(json);

                URL urlItemAdditives = new URL("https://world.openfoodfacts.org/additives.json");
                URLConnection urlConnectionItemAdditives = urlItemAdditives.openConnection();
                InputStream inputStreamItemAdditives = urlConnectionItemAdditives.getInputStream();
                json = "";
                line = null;
                bufferedReader = new BufferedReader(new InputStreamReader(inputStreamItemAdditives));
                while((line = bufferedReader.readLine())!= null)
                    json+= line + "\n";
                bufferedReader.close();
                jsonAdditives = new JSONObject(json);
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try{
                textViewProductName.setText(jsonFoodItem.getJSONObject("product").getString("product_name_en"));
                textViewBarcode.setText(getIntent().getStringExtra("BARCODE"));
                textViewIngredients.setText("Ingredients: " +  jsonFoodItem.getJSONObject("product").getString("ingredients_text_en_imported"));
                String totalAllergens = "";
            for(int x = 0; x < jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").length(); x++) {
                if(x == 0 && jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").length() > 1)
                    totalAllergens = "and " + jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").getString(x).substring(3);
                else if(x == 0 && jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").length() == 1)
                    totalAllergens = jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").getString(x).substring(3);
                else
                    totalAllergens = jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").getString(x).substring(3) + ", " + totalAllergens;
                }
            if(jsonFoodItem.getJSONObject("product").getJSONArray("allergens_tags").length() == 0)
                totalAllergens = "none";
            textViewAllergens.setText("Allergens: " + totalAllergens);
            int count = 0;
            Boolean finished = false;
            while(count < jsonAdditives.getJSONArray("tags").length() && !finished){
                for(int x = 0; x < jsonFoodItem.getJSONObject("product").getJSONArray("additives_tags").length(); x++) {
                    if (jsonFoodItem.getJSONObject("product").getJSONArray("additives_tags").getString(x).equals(jsonAdditives.getJSONArray("tags").getJSONObject(count).getString("id"))) {
                        additives.add(jsonAdditives.getJSONArray("tags").getJSONObject(count).getString("name"));
                        additivesURL.add(jsonAdditives.getJSONArray("tags").getJSONObject(count).getString("url"));
                    }
                }
                    if(additives.size() == jsonFoodItem.getJSONObject("product").getJSONArray("additives_tags").length())
                        finished = true;
                count++;
            }
            adapter.notifyDataSetChanged();
            String totalAdditives = "";
                for(int x = 0; x < jsonFoodItem.getJSONObject("product").getJSONArray("additives_tags").length(); x++) {
                    if(x == 0 && jsonFoodItem.getJSONObject("product").getJSONArray("additives_tags").length() > 1)
                        totalAdditives = "and " + additives.get(x);
                    else if(x == 0 && jsonFoodItem.getJSONObject("product").getJSONArray("additives_tags").length() == 1)
                        totalAdditives = additives.get(x);
                    else
                        totalAdditives = additives.get(x) + ", " + totalAdditives;
                }
            if(additives.size() > 0)
                textViewAdditives.setText("Additives: " + totalAdditives);
            else
                textViewAdditives.setText("Additives: " + "none");



            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
