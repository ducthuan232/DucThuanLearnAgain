package com.example.ducthuanlearnagain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.webkit.WebViewClient;

import com.example.ducthuanlearnagain.databinding.ActivityMain2Binding;

import java.net.URLConnection;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding viewBinding2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        viewBinding2 = ActivityMain2Binding.inflate(inflater);
        setContentView(viewBinding2.getRoot());
        Intent intent = getIntent();
        String link = intent.getStringExtra("linkNews");
        viewBinding2.webViewNews.loadUrl(link);
        viewBinding2.webViewNews.setWebViewClient(new WebViewClient());

    }
}