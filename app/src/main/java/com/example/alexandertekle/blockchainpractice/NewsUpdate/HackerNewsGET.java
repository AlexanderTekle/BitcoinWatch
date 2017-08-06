package com.example.alexandertekle.blockchainpractice.NewsUpdate;

/**
 * Created by alexandertekle on 7/30/17.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HackerNewsGET {

    private final String USER_AGENT = "Mozilla/5.0";
/*
    public static void main(String[] args) throws Exception {

        HackerNewsGET http = new HackerNewsGET();

        System.out.println("Testing 1 - Send Http GET request");
        System.out.println(http.sendGet());
        //String S = http.sendGet();
        //HttpRequestParser H = new HttpRequestParser();
        //H.parseRequest(S);
        //System.out.println("Here " + H.getRequestLine());



    }*/

    // HTTP GET request
    public String sendGet() throws Exception {

        String url = "https://hn.algolia.com/api/v1/search_by_date?query=bitcoin&tags=story";

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
        String jsonLine = "[" + rd.readLine() + "]";

        JSONTokener tokener = new JSONTokener(jsonLine);
        JSONArray jsonArray = new JSONArray(tokener);
        JSONObject j = jsonArray.getJSONObject(0);


        JSONArray hits = j.getJSONArray("hits");

        String ret = "";
        int i = 1;
        while (i < 5 && hits.getJSONObject(i) != null)
        {
            JSONObject article = hits.getJSONObject(i);
            ret += (i+1) + ". " + article.getString("title") + "\n";
            ret += "    -" + article.getString("url") + "\n";
            i++;
        }

        return ret;


    }



}