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
import java.text.NumberFormat;



public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    Button btn;
    float currentPrice;
    float minPrice;
    float maxPrice;

    private String[] titles;
    private String[] urls;
    private float volume;
    private int idChart = R.id.onemonth;


    private boolean isBusy = false;//this flag to indicate whether your async task completed or not
    private boolean stop = false;//this flag to indicate whether your button stop clicked
    private Handler handler = new Handler();

    private Context context;

    private TreeMap <Integer, ChartWithData>charts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        titles = new String[5];
        urls = new String[5];
        charts = new TreeMap<Integer, ChartWithData>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        create();

        //set onclicklisteners for all news buttons
        Button one = (Button) findViewById(R.id.Source1);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.Source2);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.Source3);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.Source4);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.Source5);
        one.setOnClickListener(this);

        //set onclicklisteners for all chart buttons
        one = (Button) findViewById(R.id.oneweek);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.onemonth);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.threemonths);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.sixmonths);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.oneyear);
        one.setOnClickListener(this);
        one = (Button) findViewById(R.id.threeyears);
        one.setOnClickListener(this);



    }

    public void onClick(View view) {
        //first find the view clicked
        if (view.getId() == R.id.oneweek || view.getId() == R.id.onemonth || view.getId() == R.id.threemonths || view.getId() == R.id.sixmonths
                || view.getId() == R.id.oneyear || view.getId() == R.id.threeyears)
        {
            //update for chart wanted
            idChart = view.getId();
            new UpdateChart().execute();
        }
        else {
            //handling the opening of a news article
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
        new UpdateChart().execute();
        startHandler();
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


                currentPrice = exchange.toFiat("USD", new BigDecimal(1)).floatValue();
                volume = statsresponse.getTradeVolumeUSD().floatValue();


                ret = "Current Price: $" + String.format("%.2f", currentPrice) + " 24h Volume: $" + NumberFormat.getInstance().format(volume) +"\n" + "Min: $"
                        + String.format("%.2f", minPrice) + " Max: $" + String.format("%.2f", maxPrice);
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

        }

    }

    class UpdateChart extends AsyncTask<Void, Void, float[]>
    {
        @Override
        protected void onPreExecute() {
        }

        @Override
        public float[] doInBackground(Void... urls) {
            try {
                switch (idChart) {
                    case R.id.oneweek:
                        if (charts.containsKey(R.id.oneweek))
                            return null;
                        return FirstChart.getData(8);
                    case R.id.onemonth:
                        if (charts.containsKey(R.id.onemonth))
                            return null;
                        return FirstChart.getData(31);
                    case R.id.threemonths:
                        if (charts.containsKey(R.id.threemonths))
                            return null;
                        return FirstChart.getData(91);
                    case R.id.sixmonths:
                        if (charts.containsKey(R.id.sixmonths))
                            return null;
                        return FirstChart.getData(181);
                    case R.id.oneyear:
                        if (charts.containsKey(R.id.oneyear))
                            return null;
                        return FirstChart.getData(366);
                    case R.id.threeyears:
                        if (charts.containsKey(R.id.threeyears))
                            return null;
                        return FirstChart.getData(1096);

                    default:
                        return FirstChart.getData(31);
                }

            } catch (Exception E) {
                return null;
            }

        }

        public void onPostExecute(float [] ret){
            if (charts.containsKey(idChart))
            {
                LineChart chart = (LineChart) findViewById(R.id.chart);
                chart.setData(charts.get(idChart).getDataSet());

                TextView txt = (TextView) findViewById(R.id.priceView);
                txt.setText("Current Price: $" + String.format("%.2f",currentPrice) + " 24h Volume: $" + NumberFormat.getInstance().format(volume)
                        +"\n" + "Min: $" + String.format("%.2f",charts.get(idChart).getMin()) + " Max: $" + String.format("%.2f",charts.get(idChart).getMax()));

                chart.invalidate();
            }
            else {
                LineChart chart = (LineChart) findViewById(R.id.chart);
                chart.setAutoScaleMinMaxEnabled(true);

                List<Entry> entries = new ArrayList<Entry>();
                //YAxis yaxis = chart.getAxisLeft();
                //yaxis.setAxisMinimum(1000f);
                float min = ret[0];
                float max = ret[0];
                entries.add(new Entry(0, ret[0]));
                for (int i = 1; i < ret.length; i++) {
                    entries.add(new Entry(i, ret[i]));
                    if (ret[i] < min)
                        min = ret[i];
                    else if (ret[i] > max)
                        max = ret[i];
                }
                minPrice = min;
                maxPrice = max;

                UpdatePriceData x = new UpdatePriceData();
                entries.add(new Entry(ret.length, currentPrice));

                LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to
                dataSet.setDrawValues(false);
                dataSet.setDrawFilled(true);
                dataSet.setLineWidth(1.3f);
                dataSet.setDrawCircles(false);
                //chart.setMaxVisibleValueCount(5);
                dataSet.setColor(Color.BLUE);
                //dataSet.setCircleColor(Color.BLACK);
                //dataSet.setValueTextColor(Color.RED); // styling, ...

                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);

                chart.invalidate(); // refresh
                TextView txt = (TextView) findViewById(R.id.priceView);
                txt.setText("Current Price: $" + String.format("%.2f", currentPrice) + " 24h Volume: $" + NumberFormat.getInstance().format(volume) +"\n" + "Min: $"
                        + String.format("%.2f", minPrice) + " Max: $" + String.format("%.2f", maxPrice));
                charts.put(idChart, new ChartWithData(minPrice, maxPrice, lineData));
            }
        }
    }
}
