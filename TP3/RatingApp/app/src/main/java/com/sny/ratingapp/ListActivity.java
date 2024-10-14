package com.sny.ratingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.sny.ratingapp.adapter.CarAdapter;
import com.sny.ratingapp.beans.Car;
import com.sny.ratingapp.service.CarService;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Car> cars;
    private RecyclerView recyclerView;
    private CarAdapter carAdapter = null;
    private  CarService service;
    private String TAG="Rating APP debug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        cars = new ArrayList<>();
        service = CarService.getInstance();
        init();

        recyclerView = findViewById(R.id.recyclerView);
        carAdapter=new CarAdapter(this,service.findAll());
        recyclerView.setAdapter(carAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
          @Override
          public boolean onQueryTextSubmit(String query) {
              return true;
          }
          @Override
          public boolean onQueryTextChange(String newText) {
              if (carAdapter != null){
                carAdapter.getFilter().filter(newText);
              }
              return true;
          }
      });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Stars")
                    .setText(txt)
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }


    public void init(){
        service.create(new Car("MG", "Marvel R", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/mg.png", 4.5f));
        service.create(new Car("Mini", "Cabrio", "Convertible", "https://www.moteur.ma/media/images/brands/thumbs/mini.png", 4.0f));
        service.create(new Car("Mini", "Clubman", "Wagon", "https://www.moteur.ma/media/images/brands/thumbs/mini.png", 3.8f));
        service.create(new Car("Mitsubishi", "Outlander", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/mitsubishi.png", 4.1f));
        service.create(new Car("Mitsubishi", "Pajero Sport", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/mitsubishi.png", 4.0f));
        service.create(new Car("Neo Motors", "BVM", "Sedan", "https://www.moteur.ma/media/images/brands/thumbs/neo-motors.png", 3.5f));
        service.create(new Car("Nissan", "Evalia", "MPV", "https://www.moteur.ma/media/images/brands/thumbs/nissan.png", 3.7f));
        service.create(new Car("Opel", "Crossland X", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/opel.png", 3.8f));
        service.create(new Car("Opel", "Mokka", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/opel.png", 4.0f));
        service.create(new Car("Peugeot", "Boxer", "Van", "https://www.moteur.ma/media/images/brands/thumbs/peugeot.png", 3.6f));
        service.create(new Car("Peugeot", "Expert", "Van", "https://www.moteur.ma/media/images/brands/thumbs/peugeot.png", 3.7f));
        service.create(new Car("Porsche", "Cayenne Coupe", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/porsche.png", 4.5f));
        service.create(new Car("Renault", "Megane", "Hatchback", "https://www.moteur.ma/media/images/brands/thumbs/renault.png", 4.0f));
        service.create(new Car("Renault", "Express", "Van", "https://www.moteur.ma/media/images/brands/thumbs/renault.png", 3.6f));
        service.create(new Car("SEAT", "Tarraco", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/seat.png", 4.1f));
        service.create(new Car("SEAT", "Leon", "Hatchback", "https://www.moteur.ma/media/images/brands/thumbs/seat.png", 4.0f));
        service.create(new Car("Seres", "3", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/seres.png", 3.9f));
        service.create(new Car("Seres", "SF5", "SUV", "https://www.moteur.ma/media/images/brands/thumbs/seres.png", 4.0f));
        service.create(new Car("Skoda", "Octavia", "Sedan", "https://www.moteur.ma/media/images/brands/thumbs/skoda.png", 4.2f));
    }


}