package com.example.billionairelistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Billionaire> list;
    TextView textViewBottom;
    WebView webView;
    int position2;
    public static final String KEY_list = "KEY_list";
    public static final String KEY_position = "KEY_position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.id_ListView);
        textViewBottom = findViewById(R.id.id_textView);
        list = new ArrayList<>();
        webView = findViewById(R.id.id_webView);



        list.add(new Billionaire("Jeff Bezos", R.drawable.bezos, 131, "Amazon",55, "https://www.forbes.com/profile/jeff-bezos/?list=billionaires#32e8037d1b23"));
        list.add(new Billionaire("Bill Gates", R.drawable.gates,96.5, "Microsoft", 64, "https://www.forbes.com/profile/bill-gates/?list=billionaires#75b98768689f"));
        list.add(new Billionaire("Warren Buffett", R.drawable.buffet,82.5, "Berkshire Hathaway", 64,"https://www.forbes.com/profile/warren-buffett/?list=billionaires#347a6a754639\""));
        list.add(new Billionaire("Bernard Arnault", R.drawable.arnault, 76, "LVMH", 70, "https://www.forbes.com/profile/bernard-arnault/?list=billionaires#765ac25a66fa"));
        list.add(new Billionaire("Carlos Slim Helu", R.drawable.helu, 64, "telecom", 79, "https://www.forbes.com/profile/carlos-slim-helu/?list=billionaires#4bdba27d646b"));
        list.add(new Billionaire("Amancio Ortega", R.drawable.ortega, 62.7, "Zara", 83,"https://www.forbes.com/profile/amancio-ortega/?list=billionaires#548c0210116c"));
        list.add(new Billionaire("Larry Ellison", R.drawable.ellison, 62.5, "software",75,"https://www.forbes.com/profile/larry-ellison/?list=billionaires#76ac858124c2"));
        list.add(new Billionaire("Mark Zuckerberg", R.drawable.zuckerberg, 62.3,"Facebook", 35,"https://www.forbes.com/profile/mark-zuckerberg/?list=billionaires#1aacf1523e06"));
        list.add(new Billionaire("Michael Bloomberg", R.drawable.bloomberg, 55.5,"Bloomberg LP", 77,"https://www.forbes.com/profile/michael-bloomberg/?list=billionaires#231d4c1b1417"));
        list.add(new Billionaire("Larry Page", R.drawable.page, 50.8, "Google",46, "https://www.forbes.com/profile/larry-page/?list=billionaires#788d93847893"));
        list.add(new Billionaire("Charles Koch", R.drawable.ckoch, 50.5, "Koch Industries", 84, "https://www.forbes.com/profile/charles-koch/?list=billionaires#5250f41957d7"));
        list.add(new Billionaire("David Koch", R.drawable.dkoch, 50.5, "Koch Industries", 79,"https://www.forbes.com/profile/david-koch/?list=billionaires#3861879e659b"));
        list.add(new Billionaire("Mukesh Ambani", R.drawable.ambani, 50, "petrochemicals, oil & gas", 62,"https://www.forbes.com/profile/mukesh-ambani/?list=billionaires#6fc1ba29214c"));

        if(savedInstanceState != null){
            position2 = savedInstanceState.getInt(KEY_position);
            list = (ArrayList<Billionaire>) savedInstanceState.getSerializable(KEY_list);
            textViewBottom.setText("Source: " + list.get(position2).getSource());
            if(webView != null)
                webView.loadUrl("" + list.get(position2).getLink());
        }


        CustomAdapter customAdapter = new CustomAdapter(this,R.layout.adapter_custom,list);
        listView.setAdapter(customAdapter);

    }


    public class CustomAdapter extends ArrayAdapter<Billionaire>{
        List<Billionaire> list;
        Context context;
        int xmlResource;

        public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Billionaire> objects) {
            super(context, resource, objects);
            this.context = context;
            xmlResource = resource;
            list = objects;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final int position1 = position;
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(xmlResource,null);
            TextView name = adapterView.findViewById(R.id.id_adapter_textView_name);
            TextView netWorth = adapterView.findViewById(R.id.id_adapter_textView_networth);
            TextView age = adapterView.findViewById(R.id.id_adapter_textView_age);
            ImageView imageView = adapterView.findViewById(R.id.id_adapter_imageView);
            Button buttonDelete = adapterView.findViewById(R.id.id_button_delete);

            name.setText(list.get(position).getName());
            netWorth.setText("Net Worth "+list.get(position).getNetWorth() + " billion");
            age.setText("Age: " + list.get(position).getAge());
            imageView.setImageResource(list.get(position).getFace());

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position1);
                    notifyDataSetChanged();
                    if(position2 > position1)
                        position2--;
                }
            });

            adapterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewBottom.setText("Source: " + list.get(position1).getSource());
                    position2 = position1;
                    if(webView != null) {
                        webView.loadUrl("" + list.get(position2).getLink());
                    }
                }
            });

            return adapterView;
        }

    }


    public class Billionaire implements Serializable {
        private String name;
        private int face;
        private double netWorth;
        private String source;
        private int age;
        private String link;
        public Billionaire(String name, int face, double netWorth, String source, int age, String link){
            this.name = name;
            this.face = face;
            this.netWorth = netWorth;
            this.source = source;
            this.age = age;
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public int getFace() {
            return face;
        }

        public double getNetWorth() {
            return netWorth;
        }

        public String getSource() {
            return source;
        }

        public int getAge() {
            return age;
        }

        public String getLink()
        {
            return link;
        }

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_list,list);
        outState.putInt(KEY_position, position2);
    }
}
