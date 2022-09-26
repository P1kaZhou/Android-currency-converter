package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonRatesLoader implements Runnable {

    private JSONObject jsonRatesLoader;

    public JsonRatesLoader(JSONObject jsonRatesLoader) {
        this.jsonRatesLoader = jsonRatesLoader;
    }

    public JSONObject getJsonRatesLoader() {
        return this.jsonRatesLoader;
    }

    @Override
    public void run() {
        fetchJsonFromUrlAndLoad();
    }

    public void fetchJsonFromUrlAndLoad() {
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
                this.jsonRatesLoader = new JSONObject(jsonString);

            } catch (IOException e) {
                System.err.println("Warning something " + e.getLocalizedMessage());
            } catch (JSONException e) {
                System.err.println("Warning something json " + e.getLocalizedMessage());
            }

        } catch (IOException e) {
            System.out.println("gros malaise mf");
        }
    }
}
