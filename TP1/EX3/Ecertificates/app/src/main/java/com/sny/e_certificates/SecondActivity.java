package com.sny.e_certificates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView rep1,rep2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        rep1 = findViewById(R.id.rep1txt);
        rep2 = findViewById(R.id.rep2txt);

        rep1.setText(this.getIntent().getStringExtra("Response1"));
        rep2.setText(this.getIntent().getStringExtra("Response2"));
    }
}