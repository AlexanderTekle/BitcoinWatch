package com.example.alexandertekle.blockchainpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class OpenNewsActivity extends AppCompatActivity {

    private WebView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_news);

        search = (WebView)findViewById(R.id.web);

        search.getSettings().setJavaScriptEnabled(true);

        search.loadUrl("http://en.wikipedia.org");
    }
}
