package com.example.ingredientchecker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button scanButton;
    String barcode;
    JSONObject jsonFoodItem;
    ArrayList<BarcodeObject> arrayListPreviousBarcode;
    ListView listViewPreviousBarcode;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.id_scanButton);
        listViewPreviousBarcode = findViewById(R.id.id_listView);


        arrayListPreviousBarcode = new ArrayList<>();

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("BarcodeSave2.txt")));
            StringBuffer stringBuffer = new StringBuffer();

            String lines;
            while((lines = reader.readLine())!= null){
                stringBuffer.append(lines + "\n");
            }


            ArrayList<String> arrayListTemp =  new ArrayList(Arrays.asList(stringBuffer.toString().split("!")));
            for(int x = 0; x < arrayListTemp.size(); x++) {
                arrayListPreviousBarcode.add(new BarcodeObject(arrayListTemp.get(x).substring(0,arrayListTemp.get(x).indexOf("+")),arrayListTemp.get(x).substring(arrayListTemp.get(x).indexOf("+")+1)));
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setCaptureActivity(CaptureAct.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Scanning Code");
                intentIntegrator.initiateScan();

            }
        });

        try {
            customAdapter = new CustomAdapter(this, R.layout.adapter_custom, arrayListPreviousBarcode);
            listViewPreviousBarcode.setAdapter(customAdapter);

        }catch(Exception e){
            e.printStackTrace();
        }
        listViewPreviousBarcode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToLoad = new Intent(MainActivity.this, InDepthIngredients.class);
                intentToLoad.putExtra("BARCODE", arrayListPreviousBarcode.get(position).getBarcode());
                startActivity(intentToLoad);

            }
        });

    }



    public class CustomAdapter extends ArrayAdapter<BarcodeObject>{
        List<BarcodeObject> list;
        Context context;
        int xmlResource;
        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<BarcodeObject> objects) {
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
            TextView textViewName = adapterView.findViewById(R.id.id_adapter_textViewName);
            TextView textViewBarcode = adapterView.findViewById(R.id.id_adapter_textViewBarcode);

            textViewName.setText(list.get(position).getProductName());
            textViewBarcode.setText(list.get(position).getBarcode());

            return adapterView;
        }
    }






    public class AsyncThread extends AsyncTask<String,Void,Void> {

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

                if(!jsonFoodItem.getJSONObject("product").getString("product_name_en").equals("")) {        //Start of write code
                    String initial = "";
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("BarcodeSave2.txt")));
                        StringBuffer stringBuffer = new StringBuffer();

                        String lines;
                        while ((lines = reader.readLine()) != null) {
                            stringBuffer.append(lines + "\n");
                        }

                        initial = stringBuffer.toString();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String textBarcode = strings[0];
                    String textName = jsonFoodItem.getJSONObject("product").getString("product_name_en");
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("BarcodeSave2.txt", Context.MODE_PRIVATE));
                        if (!initial.contains(textBarcode)) {
                            if(initial != "")
                                writer.write(textBarcode + "+" + textName + "!" + initial);
                            else
                                writer.write(textBarcode + "+" + textName);
                            writer.close();
                            arrayListPreviousBarcode.add(0, new BarcodeObject(textBarcode,textName));
                            customAdapter.notifyDataSetChanged();
                        }
                        else {
                            writer.write(initial);
                            writer.close();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //End Write Code
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try{
                jsonFoodItem = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
            }
            else{

                barcode = intentResult.getContents();
                AsyncThread asyncThread = new AsyncThread();
                asyncThread.execute(barcode);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public class BarcodeObject{
        String barcode;
        String productName;
        public BarcodeObject(String barcode, String productName){
            this.barcode = barcode;
            this.productName = productName;
        }
        public String getBarcode() {
            return barcode;
        }
        public String getProductName() {
            return productName;
        }
        public String toString(){
            return (barcode + "" + productName);
        }
    }
}
