package com.example.writingandreadingdatademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button save, read;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_editText);
        save = findViewById(R.id.id_button_save);
        read = findViewById(R.id.id_button_read);
        textView = findViewById(R.id.id_textView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String initial = "";
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("My File.txt")));
                    StringBuffer stringBuffer = new StringBuffer();

                    String lines;
                    while((lines = reader.readLine())!= null){
                        stringBuffer.append(lines + "\n");
                    }

                    initial= stringBuffer.toString();
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            String text = editText.getText().toString();
            try{
                OutputStreamWriter writer = new OutputStreamWriter(openFileOutput("My File.txt", Context.MODE_PRIVATE));
                if(!initial.contains(text))
                    writer.write(text + initial);
                else
                    writer.write(initial);
                writer.close();
                editText.setText("");
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("My File.txt")));
                    StringBuffer stringBuffer = new StringBuffer();

                    String lines;
                    while((lines = reader.readLine())!= null){
                        stringBuffer.append(lines + "\n");
                    }

                    textView.setText(stringBuffer.toString());
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
