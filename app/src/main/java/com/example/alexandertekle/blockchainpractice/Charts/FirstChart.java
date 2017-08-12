package com.example.alexandertekle.blockchainpractice.Charts;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 * Created by alexandertekle on 8/3/17.
 */

public class FirstChart {

    private FirstChart()
    {

    }

    public static float[] getData(int days) throws Exception
    {
        int amount = days;
       /* if (days > 1000)
            amount = days/2;*/


        float [] values = new float[amount-1];

        String url = "https://api.blockchain.info/charts/market-price?format=json&timespan=" + amount + "days";

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

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


        String ret = "";
        int i = 0;/*
        if (amount > 500)
        {
            while (i < amount - 1) {
                JSONObject daily = data.getJSONObject(i);
                values[i] = Float.parseFloat(daily.getString("y"));
                i += 2;
                if (i == amount-1)
                    i = amount-2;
            }
        }
        else {*/
            while (i < amount - 1) {
                JSONObject daily = data.getJSONObject(i);
                values[i] = Float.parseFloat(daily.getString("y"));
                i++;
            }
      //  }
        return values;

    }
}
