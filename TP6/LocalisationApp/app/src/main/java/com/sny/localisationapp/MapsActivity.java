package com.sny.localisationapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sny.localisationapp.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    String showUrl = "http://192.168.188.1/localisation/findPosition.php";
    RequestQueue requestQueue;

    private GoogleMap mMap;
    TextView distance,nbPosition;
    private ActivityMapsBinding binding;
    private List<LatLng> latlngList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nbPosition = binding.nbPosi;
        distance = binding.distance;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d("tag","position.toString()");
        latlngList = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("tag","onMapReady");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, // HTTP method
                showUrl,            // URL
                null,               // Pass null if you are not sending a JSON body; otherwise, create a JSONObject
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Handle the response
                            JSONArray positions = response.getJSONArray("positions");
                            nbPosition.setText(positions.length()+"");
                            for (int i = 0; i < positions.length(); i++) {
                                JSONObject position = positions.getJSONObject(i);
                                double latitude = position.getDouble("latitude");
                                double longitude = position.getDouble("longitude");
                                LatLng l = new LatLng(latitude, longitude);

                                mMap.addMarker(new MarkerOptions()
                                        .position(l)
                                        .title("Marker " + (i + 1)));
                                latlngList.add(l);

                            }
                            double d = calculateTotalDistanceFlatEarth(latlngList);
                            distance.setText(Math.round(d/0.762)+" pas ("+Math.round(d*100)/100+"m)");
                        } catch (JSONException e) {
                            e.printStackTrace(); // Handle JSON parsing error
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        error.printStackTrace(); // Print stack trace for debugging
                    }
                }
        );

// Add the request to the RequestQueue (assuming requestQueue is already initialized)
        requestQueue.add(jsonObjectRequest);

    }
    public double calculateDistanceFlatEarth(LatLng p1, LatLng p2) {
        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(p1.latitude);
        double lat2 = Math.toRadians(p2.latitude);
        double lon1 = Math.toRadians(p1.longitude);
        double lon2 = Math.toRadians(p2.longitude);
        final double EARTH_RADIUS = 6371000;

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        double distance = Math.sqrt(Math.pow(deltaLat, 2) + Math.pow(deltaLon, 2));
        distance = distance * EARTH_RADIUS;


        return  Math.round(distance * 100.0) / 100.0;

    }

    // Method to calculate total distance using the Flat Earth approximation
    public double calculateTotalDistanceFlatEarth(List<LatLng> positions) {
        double totalDistance = 0.0;

        for (int i = 0; i < positions.size() - 1; i++) {
            Log.d("tag",calculateDistanceFlatEarth(positions.get(i), positions.get(i + 1))+"");
            totalDistance += calculateDistanceFlatEarth(positions.get(i), positions.get(i + 1));
        }

        return totalDistance;
    }

}