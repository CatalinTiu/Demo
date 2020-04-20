package com.example.parkingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Chitanta extends AppCompatActivity {

    WebView webView;

    public String fileName = "invoice.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitanta);

        Intent intent = getIntent();
        final String nume = intent.getStringExtra(DetaliiLoc.EXTRA_MESSAGE1);
        final String loc_parcare = intent.getStringExtra(DetaliiLoc.EXTRA_MESSAGE2);
        final String nr_ore = intent.getStringExtra(DetaliiLoc.EXTRA_MESSAGE3);

        // init webView
        webView = (WebView) findViewById(R.id.simpleWebView);
        // displaying content in WebView from html file that stored in assets folder
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/" + fileName);

    }
}
