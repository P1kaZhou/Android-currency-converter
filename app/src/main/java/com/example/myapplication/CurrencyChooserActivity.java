package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class CurrencyChooserActivity extends AppCompatActivity {

    public static final int EURO_ID = 1;
    public static final int DOLLAR_ID = 2;
    public static final int POUNDS_ID = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_chooser);
        JSONObject leJson = loadLeJson();
    }

    public JSONObject loadLeJson() {
        InputStream inputStream = getResources().openRawResource(R.raw.taux_2017_11_02);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
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

    public ArrayList<Integer> getSourceAndDestination(View view){
        // We get all buttons first
        RadioButton euroSourceButton = (RadioButton) findViewById(R.id.euro_button_source);
        RadioButton dollarSourceButton = (RadioButton) findViewById(R.id.dollar_button_source);
        RadioButton poundsSourceButton = (RadioButton) findViewById(R.id.pounds_button_source);
        RadioButton euroDestinationButton = (RadioButton) findViewById(R.id.euro_button_destination);
        RadioButton dollarDestinationButton = (RadioButton) findViewById(R.id.dollar_button_destination);
        RadioButton poundsDestinationButton = (RadioButton) findViewById(R.id.pounds_button_destination);

        ArrayList<Integer> leResult = new ArrayList<>();

        if (euroSourceButton.isChecked()){
            leResult.add(EURO_ID);
        } else if (dollarSourceButton.isChecked()){
            leResult.add(DOLLAR_ID);
        } else if (poundsSourceButton.isChecked()){
            leResult.add(POUNDS_ID);
        }

        if (euroDestinationButton.isChecked()){
            leResult.add(EURO_ID);
        } else if (dollarDestinationButton.isChecked()){
            leResult.add(DOLLAR_ID);
        } else if (poundsDestinationButton.isChecked()){
            leResult.add(POUNDS_ID);
        }

        return leResult;
    }

}