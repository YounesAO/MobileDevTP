package com.sny.pizzarecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView nom,prepa,ingreds,desc;
    private ImageButton imgBtn;
    private ImageView  img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        nom = findViewById(R.id.nom);
        prepa = findViewById(R.id.preparation);
        ingreds = findViewById(R.id.ingredients);
        desc = findViewById(R.id.description);
        img = findViewById(R.id.photo);

        nom.setText(this.getIntent().getStringExtra("nom"));
        desc.setText(this.getIntent().getStringExtra("description"));
        ingreds.setText(this.getIntent().getStringExtra("ingreds"));
        prepa.setText(this.getIntent().getStringExtra("preparation"));
        img.setImageResource(this.getIntent().getIntExtra("img",0));
        imgBtn =findViewById(R.id.imageButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                DetailsActivity.this.finish();

            }
        });



    }
}