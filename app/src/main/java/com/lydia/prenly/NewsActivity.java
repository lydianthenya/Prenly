package com.lydia.prenly;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.lydia.prenly.utils.MyPreferences;

public class NewsActivity extends AppCompatActivity {

    WebView terms;
    SwipeRefreshLayout sRefresh;
    MyPreferences pref;
    Context context = NewsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        terms = (WebView) findViewById(R.id.webView);
        sRefresh = findViewById(R.id.news_swipe_refresh);

        sRefresh.setRefreshing(true);
        pref = new MyPreferences(NewsActivity.this);


        terms.getSettings().setJavaScriptEnabled(true);
        terms.getSettings().setSupportZoom(true);
        terms.getSettings().setBuiltInZoomControls(true);


        terms.setWebViewClient(new WebViewClient());
        terms.loadUrl(pref.getSelectedUrl());

        WebSettings settings = terms.getSettings();
        settings.setDomStorageEnabled(true);

        terms.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                sRefresh.setRefreshing(false);
            }

        });

    }



}