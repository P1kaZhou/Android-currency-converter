package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (!extras.getBoolean("Error")) {
                double value = extras.getDouble("convertedValue");
                String initialValue = Double.toString(extras.getDouble("initialValue"));
                String convertedCurrency = extras.getString("convertedCurrency");
                updateText(Math.floor(value * 100) / 100 + convertedCurrency, initialValue);
            } else {
                String value = Double.toString(extras.getDouble("initialValue"));
                updateText("Please make sure that both boxes are checked",value);
            }
        }
    }

    public void updateText(String answer, String initial) {
        EditText answerBox = findViewById(R.id.result);
        EditText inputBox = findViewById(R.id.edit_currency);
        answerBox.setText(answer);
        inputBox.setText(initial);

    }

    public void switchActivity(View view) {
        EditText startCurrency = findViewById(R.id.edit_currency);
        Intent myIntent = new Intent(MainActivity.this, CurrencyChooserActivity.class);

        if (!startCurrency.getText().toString().equals("")) {
            double money = Double.parseDouble(startCurrency.getText().toString());
            myIntent.putExtra("ToConvert", money);
            startActivity(myIntent);
        } else {
            myIntent.putExtra("ToConvert", 0);
            startActivity(myIntent);
        }
    }
}