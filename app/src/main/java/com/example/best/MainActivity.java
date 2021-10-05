package com.example.best;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    WebView WEBVIEW;
    private static  int nextscreen=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WEBVIEW= findViewById(R.id.WEEB);
        //WebSettings webSettings= WEBVIEW.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //String file="file:android_asset/pre.gif";
        //WEBVIEW.loadUrl(file);
        new Handler().postDelayed(new Runnable() {
            @Override

            public void run(){
                Intent intent= new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }

        },nextscreen);
    }
}