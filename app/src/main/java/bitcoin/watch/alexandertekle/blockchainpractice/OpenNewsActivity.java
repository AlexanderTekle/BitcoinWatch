package bitcoin.watch.alexandertekle.blockchainpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class OpenNewsActivity extends AppCompatActivity {

    private WebView search;
    private String webURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_news);

        search = (WebView)findViewById(R.id.web);

        search.getSettings().setJavaScriptEnabled(true);

        Bundle b = getIntent().getExtras();
        webURL = b.getString("url");

        search.loadUrl(webURL);

        search.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        search.loadUrl(webURL);

    }
}
