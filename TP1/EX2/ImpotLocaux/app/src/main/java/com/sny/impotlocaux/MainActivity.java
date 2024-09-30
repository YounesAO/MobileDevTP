package com.sny.impotlocaux;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText nom;
    private EditText adresse;
    private EditText surface;
    private EditText nbPiece;

    private TextView impotBase ;
    private TextView impotSub;
    private TextView impotTotal;

    private Button calcul;
    private CheckBox piscine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom = findViewById(R.id.nomtxt);
        adresse = findViewById(R.id.adresse);
        surface = findViewById(R.id.surface);
        nbPiece = findViewById(R.id.nbPiece);

        impotBase = findViewById(R.id.impotBase);
        impotSub = findViewById(R.id.impotSup);
        impotTotal = findViewById(R.id.impotTotal);

        calcul = findViewById(R.id.calculBtn);
        piscine = findViewById(R.id.piscine);

        calcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double impotbase = Double.parseDouble(surface.getText().toString())*2;
                impotBase.setText(String.valueOf(impotbase));

                double impotsub = Integer.parseInt(nbPiece.getText().toString())*50;
                if(piscine.isChecked())
                    impotsub +=100;

                impotSub.setText(String.valueOf(impotsub));
                impotTotal.setText(String.valueOf(impotbase+impotsub));
            }
        });


    }
}