package com.example.alexandertekle.blockchainpractice.Charts;

import android.util.Log;

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
    float high;

    private FirstChart()
    {

    }

    public static float[] getData(int days) throws Exception
    {
        int amount;
        String url;
        if (days == 365)
        {
            amount = 365;
            url = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=365&aggregate=1&e=CCCAGG";
        }
        if (days == 1)
            {
                amount = 25;
                url = "https://min-api.cryptocompare.com/data/histohour?fsym=BTC&tsym=USD&limit=24&aggregate=1";
        }
        else
        {
                amount = days;
                url = "https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=" + (amount) + "&aggregate=1&e=CCCAGG";
        }

        float values[] = new float[amount*2];

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


        JSONArray data = j.getJSONArray("Data");


        String ret = "";
        int i = 0;
        int q = 0;

        /*

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
        Log.d("amt", "" + (amount*2 -1));
            while (i < (amount*2)) {

                JSONObject daily = data.getJSONObject(q);
                values[i] = Float.parseFloat(daily.getString("time"));
                values[i+1] = Float.parseFloat(daily.getString("close"));
                Log.d("here", i + " " + values[i]);
                Log.d("here", (i+1) + " " + values[i+1]);

                q++;
                i+=2;
            }
      //  }
        return values;

    }
}
