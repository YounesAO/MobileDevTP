package com.sny.formationsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView nom,adresse,phone,email,ville;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nom = findViewById(R.id.nameTXT);
        email = findViewById(R.id.EmailTXT);
        phone = findViewById(R.id.PhoneTXT);
        ville = findViewById(R.id.VilleTXT);
        adresse = findViewById(R.id.AdresseTXT);

        nom.setText(this.getIntent().getStringExtra("NOM"));
        email.setText(this.getIntent().getStringExtra("EMAIL"));
        phone.setText(this.getIntent().getStringExtra("PHONE"));
        ville.setText(this.getIntent().getStringExtra("VILLE"));
        adresse.setText(this.getIntent().getStringExtra("ADRESSE"));


    }
}