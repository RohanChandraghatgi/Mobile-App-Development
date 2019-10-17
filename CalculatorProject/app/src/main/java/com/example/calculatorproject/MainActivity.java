package com.example.calculatorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static java.lang.Math.PI;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,button0,buttonPlus,buttonMinus,buttonTimes, buttonDivide,buttonEquals,buttonClear, button2nd, buttonCE;
    TextView text1;
    String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button_first_id);
        button2 = findViewById(R.id.button_second_id);
        button3 = findViewById(R.id.button_third_id);
        button4 = findViewById(R.id.button_fourth_id);
        button5 = findViewById(R.id.button_fifth_id);
        button6 = findViewById(R.id.button_sixth_id);
        button7 = findViewById(R.id.button_seventh_id);
        button8 = findViewById(R.id.button_eighth_id);
        button9 = findViewById(R.id.button_ninth_id);
        button0 = findViewById(R.id.button_zeroeth_id);
        buttonPlus = findViewById(R.id.button_plus_id);
        buttonMinus = findViewById(R.id.button_minus_id);
        buttonTimes = findViewById(R.id.button_times_id);
        buttonDivide = findViewById(R.id.button_divide_id);
        buttonEquals = findViewById(R.id.button_equals_id);
        buttonClear = findViewById(R.id.button_clear_id);
        button2nd = findViewById(R.id.button_2nd_id);
        buttonCE = findViewById(R.id.button_CE_id);
        text1 = findViewById(R.id.textView_display_id);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonTimes.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        button2nd.setOnClickListener(this);
        buttonCE.setOnClickListener(this);
    }
    public void onClick(View v) {
        Button button = (Button) v;
        CharSequence text = button.getText();
        if(button == button2nd) {
            if(buttonPlus.getText().equals("+")) {
                buttonPlus.setText("^");
                buttonMinus.setText("-");
                buttonTimes.setText(".");
                buttonDivide.setText("π");
                button0.setText("E");
                buttonPlus.setTextColor(Color.parseColor("#ff6500"));
                buttonMinus.setTextColor(Color.parseColor("#ff6500"));
                buttonTimes.setTextColor(Color.parseColor("#ff6500"));
                buttonDivide.setTextColor(Color.parseColor("#ff6500"));
                button0.setTextColor(Color.parseColor("#ff6500"));
            }
            else {
                buttonPlus.setText("+");
                buttonMinus.setText("—");
                buttonTimes.setText("×");
                buttonDivide.setText("÷");
                button0.setText("0");
                buttonPlus.setTextColor(Color.parseColor("#696969"));
                buttonMinus.setTextColor(Color.parseColor("#000000"));
                buttonTimes.setTextColor(Color.parseColor("#696969"));
                buttonDivide.setTextColor(Color.parseColor("#000000"));
                button0.setTextColor(Color.parseColor("#000000"));
            }
        }

        if (text1.getText().charAt(0) == '0' && button.getText() != "0" && button != button2nd && button != buttonCE)
            text1.setText(null);
        if ("0123456789+—×÷^.-E".contains(text))
            text1.append(text);
        if("π".contains(text))
            text1.append(PI+"");
        if (button == buttonClear)
            text1.setText("0");
        if (button == buttonEquals) {
            try {
                String textString = "" + text1.getText();
                StringTokenizer tokenizer = new StringTokenizer(textString, "+—÷×^", true);
                ArrayList<String> arr = new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    arr.add(tokenizer.nextToken());
                }
                int index = 0;
                if(arr.contains("+")||arr.contains("—")||arr.contains("÷")||arr.contains("×")||arr.contains("^")){
                    while(arr.size() > 1 && index < arr.size()) {
                        if(arr.get(index).equals("^")) {
                            answer = "" + onExp(arr.get(index - 1), arr.get(index + 1));
                            arr.set(index - 1, Double.toString(onExp(arr.get(index - 1), arr.get(index + 1))));
                            arr.remove(index + 1);
                            arr.remove(index);
                            index--;
                        }
                        index++;
                    }
                    index = 0;
                    while (arr.size() > 1 && index < arr.size()) {
                        if (arr.get(index).equals("×")) {
                            answer = "" + onMultiply(arr.get(index - 1), arr.get(index + 1));
                            arr.set(index - 1, Double.toString(onMultiply(arr.get(index - 1), arr.get(index + 1))));
                            arr.remove(index + 1);
                            arr.remove(index);
                            index--;
                        }
                        if (arr.get(index).equals("÷")) {
                            answer = "" + onDivide(arr.get(index - 1), arr.get(index + 1));
                            arr.set(index - 1, (onDivide(arr.get(index - 1), arr.get(index + 1))));
                            arr.remove(index + 1);
                            arr.remove(index);
                            index--;
                        }
                        index++;
                    }
                    index = 0;

                    while (arr.size() > 1 && index < arr.size()) {
                        if (arr.get(index).equals("+")) {
                            answer = "" + onAdd(arr.get(index - 1), arr.get(index + 1));
                            arr.set(index - 1, Double.toString(onAdd(arr.get(index - 1), arr.get(index + 1))));
                            arr.remove(index + 1);
                            arr.remove(index);
                            index--;
                        }
                        if (arr.get(index).equals("—")) {
                            answer = "" + onSubtract(arr.get(index - 1), arr.get(index + 1));
                            arr.set(index - 1, Double.toString(onSubtract(arr.get(index - 1), arr.get(index + 1))));
                            arr.remove(index + 1);
                            arr.remove(index);
                            index--;
                        }
                        index++;
                    }
                }
                else
                    answer = "" + onMultiply(arr.get(0),"1");
            if(answer.charAt(answer.length()-2)=='.' && answer.charAt(answer.length()-1)=='0')
                answer = answer.substring(0,answer.length()-2);
            text1.setText(answer);
            answer = 0 + "";
        }catch(Exception e){
            text1.setText("Error");
            e.printStackTrace();
            }
        }
        if(button == buttonCE){
            if(text1.length() > 1)
                text1.setText(text1.getText().toString().substring(0, text1.length() - 1));
        }
    }
    public double onAdd(String first, String second) {
            double first1 = Double.parseDouble(first);
            double second1 =Double.parseDouble(second);
            return first1+second1;
    }
    public double onSubtract(String first, String second){
            double first1 = Double.parseDouble(first);
            double second1 = Double.parseDouble(second);
            return first1-second1;
    }
    public double onMultiply(String first, String second){
            double first1 = Double.parseDouble(first);
            double second1 = Double.parseDouble(second);
            return first1*second1;    }
    public String onDivide(String first, String second){
            double first1 = Double.parseDouble(first);
            double second1 =Double.parseDouble(second);
            if(second1 == 0)
                    return "Divide by 0 error";
            return Double.toString(first1/second1);
    }
    public double onExp(String first, String exp){
            double first1 = Double.parseDouble(first);
            double exp1 = Double.parseDouble(exp);
            return Math.pow(first1, exp1);
    }
}
