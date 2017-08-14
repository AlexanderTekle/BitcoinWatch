package com.example.alexandertekle.blockchainpractice.Charts;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by alexandertekle on 8/12/17.
 */

public class VolumeChart {
    public static float[] getData(int days) throws Exception
    {
        int amount = days;
        float [] values = new float[amount-1];
        String url= "";
        if (amount > 1000)
        {
             url = "https://api.blockchain.info/charts/trade-volume?timespan=" + 2003 + "days&format=json";

        }
        else {
             url = "https://api.blockchain.info/charts/trade-volume?timespan=" + amount + "days&format=json";
        }

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);


        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";

        String jsonLine = "[";
        String json = "";
        while ((json = rd.readLine()) != null){
            jsonLine += json;
        }
        jsonLine += "]";


        JSONTokener tokener = new JSONTokener(jsonLine);
        JSONArray jsonArray = new JSONArray(tokener);
        JSONObject j = jsonArray.getJSONObject(0);

        JSONArray data = j.getJSONArray("values");
        Log.d("here", data.toString());
        int i = 0;

        while (i < amount - 1) {
            JSONObject daily = data.getJSONObject(i);
            Log.d("now",i + ": "+Float.parseFloat(daily.getString("y")));
            values[i] = Float.parseFloat(daily.getString("y"));
            i++;
        }

        return values;
    }
}

