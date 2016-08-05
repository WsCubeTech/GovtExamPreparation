package com.govt_exam_preparation.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.others.My_URL;


public class Follow_Us extends AppCompatActivity {

    WebView web;
    ProgressBar progressBar;
    Toolbar toolbar;

    TextView txtHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow__us);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtHeader = (TextView) findViewById(R.id.txtHeader);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle("");


        web = (WebView) findViewById(R.id.webview01);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        if (My_URL.Web_view_url.equalsIgnoreCase("FB")) {
            txtHeader.setText("Facebook");
            web.loadUrl("https://www.facebook.com/wscubetech.india");
        } else if (My_URL.Web_view_url.equalsIgnoreCase("TW")) {
            txtHeader.setText("Twitter");
            web.loadUrl("https://twitter.com/wscube");
        } else if (My_URL.Web_view_url.equalsIgnoreCase("LI")) {
            txtHeader.setText("Linked in");
            web.loadUrl("https://www.linkedin.com/company/wscube-tech");
        } else if (My_URL.Web_view_url.equalsIgnoreCase("G+")) {
            txtHeader.setText("Google +");
            web.loadUrl("https://plus.google.com/+wscubetechjodhpur/posts");
        }


    }

    @Override
    public void onBackPressed() {
        My_URL.Web_view_url = "";
        super.onBackPressed();
    }


    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
