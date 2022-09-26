package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CurrencyChooserActivity extends AppCompatActivity {

    double usdToEuro;
    double usdToPounds;
    double valueToConvert;

    String currencyDestination = "amongus" ; // So it is not null no matter what

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_chooser);

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<JSONObject> jsonRates = executor.submit(new JsonRatesLoader());

        try {
            JSONObject rates = jsonRates.get();
            applyTheRates(rates);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void applyTheRates(JSONObject rates) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valueToConvert = extras.getDouble("ToConvert");
            try {
                usdToEuro = rates.getJSONObject("rates").getDouble("EUR");
                usdToPounds = rates.getJSONObject("rates").getDouble("GBP");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnStuff(View view) {
        double rate = findTheRate(usdToEuro, usdToPounds);
        Intent myIntent = new Intent(CurrencyChooserActivity.this, MainActivity.class);
        if (rate != -1) {
            double convertedValue = valueToConvert * rate;
            myIntent.putExtra("initialValue", valueToConvert);
            myIntent.putExtra("convertedValue", convertedValue);
            myIntent.putExtra("convertedCurrency", currencyDestination);
            myIntent.putExtra("Error", false);
        } else {
            myIntent.putExtra("initialValue", valueToConvert);
            myIntent.putExtra("convertedValue",
                    "Please make sure that both boxes are checked");
            myIntent.putExtra("Error", true);
        }
        startActivity(myIntent);
    }

    public double findTheRate(double usdToEuro, double usdToPounds) {

        double euroToUsd = 1 / usdToEuro;
        double poundsToUsd = 1 / usdToPounds;
        double poundsToEuro = usdToEuro / usdToPounds;
        double euroToPounds = usdToPounds / usdToEuro;
        RadioGroup source = findViewById(R.id.source);
        RadioGroup destination = findViewById(R.id.destination);

        int sourceId = source.getCheckedRadioButtonId();
        int destinationId = destination.getCheckedRadioButtonId();

        if (sourceId == -1 || destinationId == -1) {
            return -1.;
        }

        if (sourceId == R.id.euro_button_source) { // Case one: from euro
            if (destinationId == R.id.euro_button_destination) {
                currencyDestination = "€";
                return 1.;
            } else if (destinationId == R.id.dollar_button_destination) {
                currencyDestination = "$";
                return euroToUsd;
            } else {
                currencyDestination = "£";
                return euroToPounds;
            }
        } else if (sourceId == R.id.dollar_button_source) { // From USD
            if (destinationId == R.id.euro_button_destination) {
                currencyDestination = "€";
                return usdToEuro;
            } else if (destinationId == R.id.dollar_button_destination) {
                currencyDestination = "$";
                return 1.;
            } else {
                return usdToPounds;
            }
        } else { // From pounds
            if (destinationId == R.id.euro_button_destination) {
                currencyDestination = "€";
                return poundsToEuro;
            } else if (destinationId == R.id.dollar_button_destination) {
                currencyDestination = "$";
                return poundsToUsd;
            } else {
                currencyDestination = "£";
                return 1.;
            }
        }
    }

}