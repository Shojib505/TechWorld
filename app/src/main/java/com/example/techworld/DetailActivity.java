package com.example.techworld;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar progressBar;
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);

        setSupportActionBar(toolbar);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
                String javaScript = "javascript:(function(){var a = document.getElementsByTagName('header');a[0].hidden=true;a= document.getElementsByClassName('page_body');a[0].style.padding='0px';})()";
                webView.loadUrl(javaScript);

            }
        });
        webView.loadUrl(getIntent().getStringExtra("url"));

    }
}
