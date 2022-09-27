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

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (!extras.getBoolean("Error")) {
                double value = extras.getDouble("convertedValue");
                String initialValue = Double.toString(extras.getDouble("initialValue"));
                String convertedCurrency = extras.getString("convertedCurrency");
                String originCurrency = extras.getString("originCurrency");
                updateText(Math.floor(value * 100) / 100 + " " + convertedCurrency,
                        initialValue, originCurrency, true);
                TextView equalBox = findViewById(R.id.center_text);
                equalBox.setVisibility(View.VISIBLE);
            } else {
                String value = Double.toString(extras.getDouble("initialValue"));
                updateText("Please make sure that both boxes are checked",value, "", false);
            }
        }
    }

    public void updateText(String answer, String initial, String origin, boolean saulGoodman) {
        TextView originBox = findViewById(R.id.origin);
        TextView answerBox = findViewById(R.id.result);
        EditText inputBox = findViewById(R.id.edit_currency);
        answerBox.setText(answer);
        inputBox.setText(initial);
        if (saulGoodman) {
            originBox.setText(initial + " " + origin);
        }
    }

    public void switchActivity(View view) {
        TextView equalBox = findViewById(R.id.center_text);
        equalBox.setVisibility(View.INVISIBLE);
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