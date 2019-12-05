package com.example.billionairelistview;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Billionaire> list;
    TextView textViewBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.id_ListView);
        textViewBottom = findViewById(R.id.id_textView);
        list = new ArrayList<>();

        list.add(new Billionaire("Jeff Bezos", R.drawable.bezos, 131, "Amazon",55,"Jeff Bezos founded e-commerce colossus Amazon in 1994 out of his garage in Seattle. He remains CEO and owns a nearly 12% stake."));
        list.add(new Billionaire("Bill Gates", R.drawable.gates,96.5, "Microsoft", 64, "To date, Gates has donated $35.8 billion worth of Microsoft stock to the Gates Foundation."));
        list.add(new Billionaire("Warren Buffett", R.drawable.buffet,82.5, "Berkshire Hathaway", 64,  "The son of a U.S. congressman, he first bought stock at age 11 and first filed taxes at age 13."));
        list.add(new Billionaire("Bernard Arnault", R.drawable.arnault, 76, "LVMH", 70, "One of the world's ultimate taste-makers, Bernard Arnault oversees an empire of 70 brands including Louis Vuitton and Sephora."));
        list.add(new Billionaire("Carlos Slim Helu", R.drawable.helu, 64, "telecom", 79,"His son-in-law Fernando Romero designed the Soumaya Museum in Mexico City, home to Slim's extensive, eclectic art collection."));
        list.add(new Billionaire("Amancio Ortega", R.drawable.ortega, 62.7, "Zara", 83, "A pioneer in fast fashion, he cofounded Inditex, known for its Zara fashion chain, with his ex-wife Rosalia Mera (d. 2013) in 1975."));
        list.add(new Billionaire("Larry Ellison", R.drawable.ellison, 62.5, "software",75,"Larry Ellison cofounded software firm Oracle in 1977 to tap into the growing need for customer relationship management databases." ));
        list.add(new Billionaire("Mark Zuckerberg", R.drawable.zuckerberg, 62.3,"Facebook", 35, "Zuckerberg started Facebook at Harvard in 2004 at the age of 19 for students to match names with photos of classmates."));
        list.add(new Billionaire("Michael Bloomberg", R.drawable.bloomberg, 55.5,"Bloomberg LP", 77,"After earning his M.B.A. from Harvard, Bloomberg got a job in 1966 in \"The Cage\" at Salomon Brothers, where he counted out securities by hand." ));
        list.add(new Billionaire("Larry Page", R.drawable.page, 50.8, "Google",46,"Page was CEO until 2001, when Eric Schmidt took over, and then from 2011 until 2015, when he became CEO Google's new parent company Alphabet."));
        list.add(new Billionaire("Charles Koch", R.drawable.ckoch, 50.5, "Koch Industries", 84, "Charles Koch has been chairman and CEO of Koch Industries, America's second largest private company by revenue, since 1967."));
        list.add(new Billionaire("David Koch", R.drawable.dkoch, 50.5, "Koch Industries", 79, "A college basketball star, Koch held MIT's record for most points scored in a game for 46 years. Unfortunately, he passed away in August of 2019"));
        list.add(new Billionaire("Mukesh Ambani", R.drawable.ambani, 50, "petrochemicals, oil & gas", 62, "Mukesh Ambani chairs and runs $88 billion (revenue) oil and gas giant Reliance Industries, among India's most valuable companies."));


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
            netWorth.setText("Net Worth "+list.get(position).getNetWorth() + "billion");
            age.setText("Age: " + list.get(position).getAge());
            imageView.setImageResource(list.get(position).getFace());

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position1);
                }
            });

            adapterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewBottom.setText("Source: " + list.get(position1).getSource());
                }
            });

            return adapterView;
        }

    }

    public class Billionaire{
        private String name;
        private int face;
        private double netWorth;
        private String source;
        private int age;
        private String note;
        public Billionaire(String name, int face, double netWorth, String source, int age, String note){
            this.name = name;
            this.face = face;
            this.netWorth = netWorth;
            this.source = source;
            this.age = age;
            this.note = note;
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

        public String getNote() {
            return note;
        }
    }
}
