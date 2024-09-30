package com.sny.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button toastBtn,countBtn;
    private TextView valueTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toastBtn = findViewById(R.id.toastBtn);
        countBtn = findViewById(R.id.countBtn);
        valueTxt = findViewById(R.id.valueTxt);
        valueTxt.setText("0");
        toastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "Hello Toast"
                        , Toast.LENGTH_LONG).show();
            }
        });
        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(valueTxt.getText().toString());
                val++;
                valueTxt.setText(String.valueOf(val));
            }
        });
    }
}