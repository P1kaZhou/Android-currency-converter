package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import android.widget.RadioGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CurrencyChooserActivity extends AppCompatActivity {

    double usdToEuro;
    double usdToPounds;
    double valueToConvert;
    JSONObject rates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_chooser);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //todo: fix the things, i don't know argh
    }

    public void applyTheRates(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valueToConvert = extras.getDouble("ToConvert");
            try {
                System.out.println("je suis dans le applyTheRates");
                usdToEuro = rates.getJSONObject("rates").getDouble("EUR");
                usdToPounds = rates.getJSONObject("rates").getDouble("GBP");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject loadLeJson() throws IOException {
        URL exchangeRatesURL =
                new URL("https://perso.telecom-paristech.fr/eagan/class/igr201/data/rates_2017_11_02.json");
        InputStream inputStream = exchangeRatesURL.openStream();
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            String jsonString = stringBuilder.toString();
            return new JSONObject(jsonString);
        } catch (IOException e) {
            System.err.println("Warning something " + e.getLocalizedMessage());
        } catch (JSONException e) {
            System.err.println("Warning something json " + e.getLocalizedMessage());
        }
        return null;
    }

    public void returnStuff(View view) {
        double rate = findTheRate(usdToEuro, usdToPounds);
        Intent myIntent = new Intent(CurrencyChooserActivity.this, MainActivity.class);
        double convertedValue = valueToConvert * rate;
        myIntent.putExtra("initialValue", valueToConvert);
        myIntent.putExtra("convertedValue", convertedValue);
        startActivity(myIntent);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //startActivityIfNeeded(myIntent, 0);
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
            return 0; // Later
        }

        if (sourceId == R.id.euro_button_source) { // Case one: from euro
            if (destinationId == R.id.euro_button_destination) {
                return 1.;
            } else if (destinationId == R.id.dollar_button_destination) {
                return euroToUsd;
            } else {
                return euroToPounds;
            }
        } else if (sourceId == R.id.dollar_button_source) { // From USD
            if (destinationId == R.id.euro_button_destination) {
                return usdToEuro;
            } else if (destinationId == R.id.dollar_button_destination) {
                return 1.;
            } else {
                return usdToPounds;
            }
        } else { // From pounds
            if (destinationId == R.id.euro_button_destination) {
                return poundsToEuro;
            } else if (destinationId == R.id.dollar_button_destination) {
                return poundsToUsd;
            } else {
                return 1.;
            }
        }
    }

}