package com.sny.formationsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private EditText nom,phone,email,adresse;
    private Spinner Villes;
    private Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        adresse = findViewById(R.id.adresse);
        Villes = findViewById(R.id.spinnerVille);

        sendBtn = findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("NOM",nom.getText().toString() );
                intent.putExtra("PHONE",phone.getText().toString() );
                intent.putExtra("EMAIL",email.getText().toString() );
                intent.putExtra("ADRESSE",adresse.getText().toString() );
                intent.putExtra("VILLE", Villes.getSelectedItem().toString());

                startActivity(intent);
            }
        });
    }
}