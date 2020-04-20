package com.example.parkingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Chitanta extends AppCompatActivity {

    WebView webView;//Este necesar pentru a afisa pagini html, iar chitanta este scrisa in html

    public String fileName = "invoice.html";// numele paginii html

    // Functia onCreate se apeleaza prima, atunci cand se incarca pagina.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitanta);



        // initiam webView, care incarca pagina html cu chitanta
        webView = (WebView) findViewById(R.id.simpleWebView);
        // apelam deschiderea fisierului html din folderul assets
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/" + fileName);

    }
}
