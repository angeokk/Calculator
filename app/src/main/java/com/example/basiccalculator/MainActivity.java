package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    TextView inputTV, solutionTV, actionTV;
    MaterialButton buttonCurrConv;
    MaterialButton buttonC;
    MaterialButton buttonDiv, buttonMul, buttonPlus, buttonMin, buttonEq;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;


    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization

        inputTV = findViewById(R.id.input_text_view);
        solutionTV = findViewById(R.id.solution_text_view);
        actionTV = findViewById(R.id.action_text_view);

        assignId(buttonCurrConv, R.id.button_curr_conv);
        assignId(buttonC, R.id.button_c);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonDiv, R.id.button_divide);
        assignId(buttonMul, R.id.button_multiply);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonMin, R.id.button_minus);
        assignId(buttonEq, R.id.button_equal);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonDot, R.id.button_dot);
    }



    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();

        if (buttonText.equals("Currency Converter")) {
            Intent intent = new Intent(MainActivity.this, CurrConv.class);
            startActivity(intent);
            return;
        }

        if (buttonText.equals("AC")) {
            solutionTV.setText("");
            inputTV.setText("0");
            actionTV.setText("");
            return;
        }

        if (buttonText.equals("=")) {
            if (actionTV.getText().toString().equals("")) {
                solutionTV.setText(inputTV.getText());
                inputTV.setText("0");
                return;
            }
            solutionTV.setText(getResult(solutionTV.getText().toString(), actionTV.getText().toString(), inputTV.getText().toString()));
            inputTV.setText("0");
            actionTV.setText("");
            return;
        }

        if (buttonText.equals("/") || buttonText.equals("*") || buttonText.equals("+") || buttonText.equals("-")) {
            if (inputTV.getText().toString().equals("0")) {
                actionTV.setText(buttonText);
                return;
            }
            if (!actionTV.getText().toString().equals("")) {
                solutionTV.setText(getResult(solutionTV.getText().toString(), actionTV.getText().toString(), inputTV.getText().toString()));
                inputTV.setText("0");
                return;
            }
            solutionTV.setText(inputTV.getText());
            inputTV.setText("0");
            actionTV.setText(buttonText);
            return;
        }

        if (buttonText.equals("C")) {
            if (inputTV.length() == 1) {
                inputTV.setText("0");
            } else {
                inputTV.setText(inputTV.getText().toString().substring(0, inputTV.getText().toString().length() - 1));
            }
        } else {
            if (buttonText.equals(".")) {
                if (!inputTV.getText().toString().contains(".")) {
                    inputTV.setText(inputTV.getText().toString() + ".");
                } else {
                    return;
                }
            } else {
                if (inputTV.getText().toString().equals("0")) {
                    inputTV.setText(buttonText.toString());
                } else {
                    inputTV.setText(inputTV.getText().toString() + buttonText.toString());
                }
            }
        }
    }

    String getResult(String s, String a, String r) {
        try {
            String finalResult;
            if (a.equals("/")) {
                finalResult = (Float.parseFloat(s) / Float.parseFloat(r)) + "";
            } else if (a.equals("*")) {
                finalResult = (Float.parseFloat(s) * Float.parseFloat(r)) + "";
            } else if (a.equals("-")) {
                finalResult = (Float.parseFloat(s) - Float.parseFloat(r)) + "";
            } else {
                finalResult = (Float.parseFloat(s) + Float.parseFloat(r)) + "";
            }
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.substring(0, finalResult.length() - 2);
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}