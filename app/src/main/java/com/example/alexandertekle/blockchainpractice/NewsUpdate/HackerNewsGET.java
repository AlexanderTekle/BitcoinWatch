package com.example.alexandertekle.blockchainpractice.NewsUpdate;

/**
 * Created by alexandertekle on 7/30/17.
 */


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

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

    private TreeMap<Integer, String> sites;

    private final String USER_AGENT = "Mozilla/5.0";


    public HackerNewsGET()
    {
    }

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

        int i = 0;
        int q = 0;
        while (q < 5 && hits.getJSONObject(i) != null)
        {
           // Log.d("here", "here");
            JSONObject article = hits.getJSONObject(i);
            if (!article.getString("title").contains("Ask HN:")) {
                String newsurl = article.getString("url");
                String source = "";
                //check how to get substring
                if (!newsurl.contains("https"))
                {
                    newsurl = newsurl.replaceAll("http", "https");
                }
                //Log.d("here", newsurl);
                if (newsurl.contains("www")) {
                    source = newsurl.substring(newsurl.indexOf("//") + 6, newsurl.indexOf(".com")).toUpperCase() + ": ";
                }
                else {
                    source = newsurl.substring(newsurl.indexOf("//") + 2, newsurl.indexOf(".com")).toUpperCase() + ": ";
                }
                ret += source + article.getString("title") + "_" + newsurl + "splithere";
                q++;
            }
            i++;
        }

        return ret;

    }



}