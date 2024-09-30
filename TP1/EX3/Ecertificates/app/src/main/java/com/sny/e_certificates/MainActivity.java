package com.sny.e_certificates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private Button nextBtn,exitBtn;
    private CheckBox [] reponses;
    private RadioButton yesRB, noRB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextBtn = findViewById(R.id.nextBtn);
        exitBtn = findViewById(R.id.exitBtn);

        yesRB = findViewById(R.id.YesCB);
        noRB  = findViewById(R.id.noCB);
        reponses = new CheckBox[4];
        reponses[0]=findViewById(R.id.res1);
        reponses[1]=findViewById(R.id.res2);
        reponses[2]=findViewById(R.id.res3);
        reponses[3]=findViewById(R.id.res4);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String response1 = "";
                for(CheckBox res : reponses){
                    if(res.isChecked()){
                        response1 += res.getText().toString()+"\n";
                    }
                }
                String response2 = "";
                if(yesRB.isChecked())
                    response2 = yesRB.getText().toString();
                else if (noRB.isChecked())
                    response2 = noRB.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("Response1", response1);
                intent.putExtra("Response2", response2);

                startActivity(intent);

            }
        });
    }
}