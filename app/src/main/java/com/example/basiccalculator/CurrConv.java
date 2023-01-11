package com.example.basiccalculator;


import android.content.Intent;
import android.os.Bundle;

import com.example.basiccalculator.Retrofit.RetrofitBuilder;
import com.example.basiccalculator.Retrofit.RetrofitInterface;
import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrConv extends AppCompatActivity implements View.OnClickListener {

    Spinner spUp, spDown;
    TextView convTV, tobeconvTV;
    MaterialButton buttonCalc;
    MaterialButton buttonC;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.curr_converter);

        spUp = findViewById(R.id.sp_up);
        spDown = findViewById(R.id.sp_down);
        convTV = findViewById(R.id.converted_text_view);
        tobeconvTV = findViewById(R.id.base_text_view);

        assignId(buttonCalc, R.id.button_calculator);



        assignId(buttonC, R.id.button_c);
        assignId(buttonAC, R.id.button_ac);

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

        String[] dropDownLIst ={"USD", "EUR", "JPY", "GBP", "DKK", "CZK", "TRY"};
        String[] dropDownLIst2 ={"EUR", "USD", "JPY", "GBP", "DKK", "CZK", "TRY"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, dropDownLIst);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, dropDownLIst2);
        spUp.setAdapter(adapter);
        spDown.setAdapter(adapter2);




    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();


        RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
        Call<JsonObject> call = retrofitInterface.getExchangeCurrency(spDown.getSelectedItem().toString());
        call.enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            if(!response.isSuccessful()){
                convTV.setText("err");
            }
                JsonObject res = response.body();
                JsonObject rates = res.getAsJsonObject("rates");
                double currency = Double.valueOf(tobeconvTV.getText().toString());
                double multiplier = Double.valueOf(rates.get(spUp.getSelectedItem().toString()).toString());
                double result = currency * multiplier;

                convTV.setText(String.valueOf(result));

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                convTV.setText("Try again with wifi on.");
            }
        });


        if(buttonText.equals("Calculator")){
            Intent intent = new Intent(CurrConv.this, MainActivity.class);
            startActivity(intent);
            return;
        }

        if(buttonText.equals("AC")){
            convTV.setText("0.0");
            tobeconvTV.setText("0");
            return;
        }

        if(buttonText.equals("C")){
            if(tobeconvTV.length()==1){
                tobeconvTV.setText("0");
            }
            else{
                tobeconvTV.setText(tobeconvTV.getText().toString().substring(0, tobeconvTV.getText().toString().length()-1));
            }
        }
        else{
            if(buttonText.equals(".")){
                if(!tobeconvTV.getText().toString().contains(".")){
                    tobeconvTV.setText(tobeconvTV.getText().toString()+".");
                }
                else{
                    return;
                }
            }
            else if(!buttonText.equals("Convert")){
                if(tobeconvTV.getText().toString().equals("0") ){
                    tobeconvTV.setText(buttonText.toString());
                }
                else {
                    tobeconvTV.setText(tobeconvTV.getText().toString()+buttonText.toString());
                }
            }

            //dataToCalculate = dataToCalculate + buttonText;
        }
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }





}
