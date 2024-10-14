package com.sny.ratingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    ImageView logo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        Runnable fadeInOut = new Runnable() {
            @Override
            public void run() {
                logo.animate().rotation(logo.getAlpha() == 1f ? 0.5f:360f).setDuration(10000);
                //logo.animate().translationYBy(1000f).setDuration(2000);
                logo.animate().scaleX(logo.getAlpha() == 1f ? 0.5f:1f).scaleY(logo.getAlpha() == 1f ? 0.5f:1f).setDuration(1000);
                logo.animate().alpha(logo.getAlpha() == 1f ? 0f : 1f)
                        .setDuration(2000)
                        .withEndAction(this);
            }
        };
        logo.animate().alpha(0f).setDuration(2000).withEndAction(fadeInOut);

        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(7000);
                    Intent intent = new Intent(SplashActivity.this, ListActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}