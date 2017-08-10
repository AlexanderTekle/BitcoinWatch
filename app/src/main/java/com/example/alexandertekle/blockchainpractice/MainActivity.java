package com.example.alexandertekle.blockchainpractice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.alexandertekle.blockchainpractice.NewsUpdate.HackerNewsGET;
import com.example.alexandertekle.blockchainpractice.info.blockchain.api.exchangerates.APIException;
import com.example.alexandertekle.blockchainpractice.info.blockchain.api.exchangerates.Currency;
import com.example.alexandertekle.blockchainpractice.info.blockchain.api.exchangerates.ExchangeRates;
import com.example.alexandertekle.blockchainpractice.Charts.*;
import com.example.alexandertekle.blockchainpractice.info.blockchain.api.exchangerates.Statistics;
import com.example.alexandertekle.blockchainpractice.info.blockchain.api.exchangerates.StatisticsResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.os.Handler;
import java.lang.Runnable;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    Button btn;
    float currentPrice;
    float minPrice;
    float maxPrice;

    private String[] titles;
    private String[] urls;

    private boolean isBusy = false;//this flag to indicate whether your async task completed or not
    private boolean stop = false;//this flag to indicate whether your button stop clicked
    private Handler handler = new Handler();

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titles = new String[5];
        urls = new String[5];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        create();/*
        btn = (Button) findViewById(R.id.Source1);
                btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, OpenNewsActivity.class);
                intent.putExtra("url", urls[0]);
                startActivity(intent);
            }
        });*/
        Button one = (Button) findViewById(R.id.Source1);
        one.setOnClickListener(this);
        Button two = (Button) findViewById(R.id.Source2);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.Source3);
        three.setOnClickListener(this);
        Button four = (Button) findViewById(R.id.Source4);
        four.setOnClickListener(this);
        Button five = (Button) findViewById(R.id.Source5);
        five.setOnClickListener(this);
    }

    public void onClick(View view) {
        // detect the view that was "clicked"

        Intent intent = new Intent(context, OpenNewsActivity.class);

        switch (view.getId()) {
            case R.id.Source1:
                intent.putExtra("url", urls[0]);
                break;
            case R.id.Source2:
                intent.putExtra("url", urls[1]);
                break;
            case R.id.Source3:
                intent.putExtra("url", urls[2]);
                break;
            case R.id.Source4:
                intent.putExtra("url", urls[3]);
                break;
            case R.id.Source5:
                intent.putExtra("url", urls[4]);
                break;
        }
        startActivity(intent);
    }

    public void startHandler()
    {
        callAysncTask();
        /*
        Uncomment this for updates
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(!isBusy) callAysncTask();

                if(!stop) startHandler();
            }
        }, 5000);*/
    }

    private void callAysncTask()
    {
        //TODO
        new UpdatePriceData().execute();
    }


    public void create() {
        new UpdateNews().execute();
        new Chart().execute();
        startHandler();
/*
        Button temp = (Button) findViewById(R.id.Source1);
        temp.setText(newsURLs.get(1));
       /* Button temp3 = (Button) findViewById(ids[2]);
        temp3.setText(newsURLs.get(ids[2]));*/
        String problem = "";/*
        for (Integer x : newsURLs.keySet())
        {
                Log.d("issue", problem);

        //problem += x;
        }
        Log.d("issue", problem);*/
        //Set x = newsURLs.keySet();

    }




    class UpdatePriceData extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {
        }

        @Override
        public String doInBackground(Void... urls) {
            String ret = "";
            try {
                ExchangeRates exchange = new ExchangeRates();

                Statistics stats = new Statistics();
                StatisticsResponse statsresponse = stats.getStats();


                BigDecimal BTCToUSD = exchange.toFiat("USD", new BigDecimal(1));
                BigDecimal volume = statsresponse.getTradeVolumeUSD();


                currentPrice = BTCToUSD.floatValue();
                ret = "Current Price: $" + BTCToUSD + " 24h Volume: $" + volume +"\n" + "Min: $" + minPrice + " Max: $" + maxPrice;
                // add 24h high, low, volume
            }
            catch (Exception E)
            {
                ret = E.toString() + E.getMessage();
            }
            return ret;

        }

        @Override
        public void onPostExecute(String ret)
        {
                TextView txt = (TextView) findViewById(R.id.priceView);
                txt.setText(ret);

        }


    }

    class UpdateNews extends AsyncTask<Void, Void, String[]>
    {
        @Override
        protected void onPreExecute() {
        }

        @Override
        public String[] doInBackground(Void... urls) {
            HackerNewsGET Sources = new HackerNewsGET();
            try {
                //newsURLs = Sources.sendGet();
                titles = Sources.sendGet().split("splithere");

                ExchangeRates exchange = new ExchangeRates();
                currentPrice = exchange.toFiat("USD", new BigDecimal(1)).floatValue();

            }
            catch (Exception E)
            {

            }

            return titles;

        }


        public void onPostExecute(String[] ret)
        {
            //TextView txt = (TextView) findViewById(R.id.sourcesView);
            //txt.setText(ret);
            int btns[] = new int[]{R.id.Source1, R.id.Source2, R.id.Source3, R.id.Source4, R.id.Source5};
            for (int i = 0; i < 5; i++)
            {
                Button btn = (Button) findViewById(btns[i]);
                btn.setText(ret[i].substring(0,ret[i].indexOf("_")));
            }
            for (int q=0;q<5;q++)
            {
                urls[q] = ret[q].substring(ret[q].indexOf("_") + 1);
            }
/*
            i++;
            btn = (Button)findViewById(R.id.Source2);
            btn.setText(titles[i]);
            i++;
            btn = (Button) findViewById(R.id.Source3);
            btn.setText(titles[i]);
            i++;
            btn = (Button) findViewById(R.id.Source4);
            btn.setText(titles[i]);
            i++;
            btn = (Button) findViewById(R.id.Source5);
            btn.setText(titles[i]);
            i++;*/

        }

    }

    class Chart extends AsyncTask<Void, Void, float[]>
    {
        @Override
        protected void onPreExecute() {
        }

        @Override
        public float[] doInBackground(Void... urls) {
            try {
                return FirstChart.getData();
            } catch (Exception E) {
                return null;
            }
        }

        public void onPostExecute(float [] ret){
            LineChart chart = (LineChart) findViewById(R.id.chart);
            List<Entry> entries = new ArrayList<Entry>();
            //YAxis yaxis = chart.getAxisLeft();
            //yaxis.setAxisMinimum(1000f);
            float min = ret[0];
            float max = ret[0];
            entries.add(new Entry(0, ret[0]));
            for (int i = 1; i < 30; i++)
            {
                entries.add(new Entry(i, ret[i]));
                if (ret[i] < min)
                    min = ret[i];
                else
                    if (ret[i] > max)
                        max = ret[i];
            }
            minPrice = min;
            maxPrice = max;

            UpdatePriceData x = new UpdatePriceData();
            entries.add(new Entry(30, currentPrice));

            LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
            dataSet.setColor(Color.BLUE);
            dataSet.setValueTextColor(Color.RED); // styling, ...

            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate(); // refresh
        }
    }
}
