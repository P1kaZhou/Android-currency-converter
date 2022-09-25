package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        System.out.println("resumed!");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Double value = extras.getDouble("convertedValue");
            Double initialValue = extras.getDouble("initialValue");
            System.out.println("I KNOW THE ANSWER AND THE ANSWER IS" + value);
            updateText(value, initialValue);
        } else {
            System.out.println("null extras :(");
        }
    }

    public void updateText(double answer, double initial){
        EditText answerBox = findViewById(R.id.result);
        EditText inputBox = findViewById(R.id.edit_currency);
        answerBox.setText(Double.toString(answer));
        inputBox.setText(Double.toString(initial));

    }

    public void switchActivity(View view){
        EditText startCurrency = findViewById(R.id.edit_currency);
        Intent myIntent = new Intent(MainActivity.this, CurrencyChooserActivity.class);

        if (!startCurrency.getText().toString().equals("")){
            double money = Double.parseDouble(startCurrency.getText().toString());
            myIntent.putExtra("ToConvert", money);
            startActivity(myIntent);
        }
        else {
            myIntent.putExtra("ToConvert", 0);
            startActivity(myIntent);
        }
    }
}