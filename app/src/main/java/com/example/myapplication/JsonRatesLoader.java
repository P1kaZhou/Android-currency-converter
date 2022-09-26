package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Callable;

public class JsonRatesLoader implements Callable<JSONObject> {

    public JSONObject fetchJsonFromUrlAndLoad() {
        try {
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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JSONObject call() {
        return fetchJsonFromUrlAndLoad();
    }
}
