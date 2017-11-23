package com.example.sunny.companiesproject;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sunny on 22/11/2017.
 */

public class CompanyService extends IntentService {

    protected static final String ACTION_JSON = "com.example.sunny.companiesproject.action.GET_JSON";

    public CompanyService() {
        super("CompanyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        String result = "";

        try {
            URL url = new URL(intent.getStringExtra("url"));
            urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line = bufferedReader.readLine();
            while (line != null){
                result += line;
                result += "\n";
                line = bufferedReader.readLine();
            }

            Intent dataIntent = new Intent(ACTION_JSON);
            dataIntent.putExtra("json", result);
            LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }
}
