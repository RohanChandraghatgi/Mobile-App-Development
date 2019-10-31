package com.example.widgetdaytwopractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioGroupVolume, radioGroupGame;
    ImageView imageView;
    Button play;
    TextView selection,  score;
    int scorePlay = 0;
    int scoreCPU = 0;
    String choice, cpu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroupVolume = findViewById(R.id.id_radioGroup_Volume);
        radioGroupGame = findViewById(R.id.id_radioGroup_Game);
        imageView = findViewById(R.id.id_imageView);
        play = findViewById(R.id.id_button);
        selection = findViewById(R.id.id_textView_Selection);
        score = findViewById(R.id.id_textView_Score);

        radioGroupVolume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_radioButton_100)
                    Toast.makeText(MainActivity.this, "WARNING: Volume may damage hearing", Toast.LENGTH_SHORT).show();
            }
        });

        radioGroupGame.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.id_radioButton_Rock)
                    choice = "rock";
                else if(checkedId == R.id.id_radioButton_Paper)
                    choice = "paper";
                else if(checkedId == R.id.id_radioButton_Scissor)
                    choice = "scissor";
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rand = (int)(Math.random()*3);
                if(rand == 0){
                    cpu = "rock";
                    imageView.setImageResource(R.drawable.rock);
                }
                else if(rand == 1){
                    cpu = "paper";
                    imageView.setImageResource(R.drawable.paper);
                }
                else{
                    cpu = "scissor";
                    imageView.setImageResource(R.drawable.scissors);
                }

                if(choice != null){
                    if(choice.equals("rock")){
                        if(cpu.equals("paper")) {
                            scoreCPU++;
                            selection.setText("You Lose!");
                        }
                        else if(cpu.equals("scissor")) {
                            scorePlay++;
                            selection.setText("You Win!");
                        }
                        else{
                            selection.setText("Tie!");
                        }
                    }
                    if(choice.equals("paper")){
                        if(cpu.equals("scissor")) {
                            scoreCPU++;
                            selection.setText("You Lose!");
                        }
                        else if(cpu.equals("rock")) {
                            scorePlay++;
                            selection.setText("You Win!");
                        }
                        else{
                            selection.setText("Tie!");
                        }
                    }
                    if(choice.equals("scissor")){
                        if(cpu.equals("rock")) {
                            scoreCPU++;
                            selection.setText("You Lose!");
                        }
                        else if(cpu.equals("paper")) {
                            scorePlay++;
                            selection.setText("You Win!");
                        }
                        else{
                            selection.setText("Tie!");
                        }
                    }
                }
                else{
                    selection.setText("Make a selection");
                }

                score.setText("Player: " + scorePlay + " CPU: " + scoreCPU);
            }
        });
    }
}
