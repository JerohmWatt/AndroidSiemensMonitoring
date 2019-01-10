package xuro.be.projetandroidwattin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import xuro.be.projetandroidwattin.R;

public class WebActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        String source = intent.getStringExtra("AUTOMATE");
        webview = findViewById(R.id.wb_web_navigator);
        webview.setWebViewClient(new WebViewClient());
        if(source.equals("pill")){
            webview.loadUrl("http:/10.1.0.119");
        }
        else {
            webview.loadUrl("http://10.1.0.114");
        }
    }
}
