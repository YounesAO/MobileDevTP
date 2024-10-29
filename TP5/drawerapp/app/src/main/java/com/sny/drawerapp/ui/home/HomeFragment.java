package com.sny.drawerapp.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.sny.drawerapp.MainActivity;
import com.sny.drawerapp.adapter.EtudiantAdapter;
import com.sny.drawerapp.classes.Etudiant;
import com.sny.drawerapp.databinding.FragmentHomeBinding;
import com.sny.drawerapp.databinding.FragmentSlideshowBinding;
import com.sny.drawerapp.ui.slideshow.SlideshowViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private EtudiantAdapter adapter;
    private List<Etudiant> etudiantList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerview;
        Toast.makeText(getActivity().getApplicationContext(), "Response: ", Toast.LENGTH_LONG).show();

        /**
         * Getall etudiant and create RecycleView
         *
         * */
        fetchEtudiantsFromAPI();

        return root;
    }

    private void fetchEtudiantsFromAPI() {
        String url = "http://192.168.188.1/phpe/ws/loadEtudiant.php";
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(getActivity().getApplicationContext(), "Response: " + response, Toast.LENGTH_LONG).show();

                        try {
                            etudiantList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String nom = jsonObject.getString("nom");
                                String prenom = jsonObject.getString("prenom");
                                String sexe = jsonObject.getString("sexe");
                                String ville = jsonObject.getString("ville");
                                String image = jsonObject.getString("image");
                                Log.d("TAG","image");
                                etudiantList.add(new Etudiant(id, nom, prenom, ville, sexe,image));
                            }
                            // Ensure notifyDataSetChanged() runs on the main UI thread
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                                    adapter = new EtudiantAdapter(getActivity(), etudiantList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged(); // Update adapter
                                    adapter.attachSwipeToDelete(recyclerView); // Call to enable swipe functionality


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

                        error.printStackTrace();
                    }
                });

        queue.add(jsonArrayRequest);
    }

    public void filter(String text) {
       adapter.getFilter().filter(text);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}