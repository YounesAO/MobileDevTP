package com.sny.drawerapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.sny.drawerapp.R;
import com.sny.drawerapp.classes.Etudiant;
import com.sny.drawerapp.ui.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> implements Filterable {
    private static final String TAG = "EtudiantAdapter";
    private List<Etudiant> etudiants;
    private List<Etudiant> etudiantFilter;
    private NewFilter mfilter;
    private Context context;


    public EtudiantAdapter(Context context, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        this.context = context;
        etudiantFilter = new ArrayList<>();
        etudiantFilter.addAll(etudiants);
        mfilter = new NewFilter(this);

    }

    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item,
                viewGroup, false);
        EtudiantViewHolder holder = new EtudiantViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etudiant etudiant = etudiantFilter.get(holder.getAdapterPosition()); // Get the clicked Etudiant

                NavController navController = Navigation.findNavController((AppCompatActivity) v.getContext(), R.id.nav_host_fragment_content_main);

                // Create a bundle to pass the data to ProfileFragment
                Bundle bundle = new Bundle();
                bundle.putInt("id", etudiant.getId());
                bundle.putString("nom", etudiant.getNom());
                bundle.putString("prenom", etudiant.getPrenom());
                bundle.putString("ville", etudiant.getVille());
                bundle.putString("sexe", etudiant.getSexe());
                bundle.putString("image", etudiant.getImage());

                // Navigate to ProfileFragment and pass the arguments
                navController.navigate(R.id.profileFragment, bundle);
            }
        });

        return holder;
    }
    private Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    @Override
    public void onBindViewHolder(@NonNull EtudiantViewHolder etudiantViewHolder, int i) {
        Log.d(TAG, "onBindView call ! " + i);
        Bitmap bitmap = decodeBase64ToBitmap(etudiantFilter.get(i).getImage());

        // Use Glide to load the Bitmap
        Glide.with(context)
                .asBitmap()
                .load(bitmap)  // Load the decoded Bitmap
                .into(etudiantViewHolder.img);

        etudiantViewHolder.villeEtudiant.setText(etudiantFilter.get(i).getVille().toUpperCase());
        etudiantViewHolder.nomEtudiant.setText(etudiantFilter.get(i).getNom().toUpperCase() + " " + etudiantFilter.get(i).getPrenom().toUpperCase());
        etudiantViewHolder.idEtudiant.setText(etudiantFilter.get(i).getId() + "");
    }


    public void attachSwipeToDelete(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // We are not implementing move
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Etudiant etudiantToRemove = etudiantFilter.get(position); // Get the swiped Etudiant

                etudiantFilter.remove(position);
                String url = "http://10.0.2.2/phpE/ws/deleteEtudiant.php"; // Your update URL

                RequestQueue queue = Volley.newRequestQueue(viewHolder.itemView.getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(viewHolder.itemView.getContext(), "Etudiant deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(viewHolder.itemView.getContext(), "Failed to delete etudiant", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", String.valueOf(etudiantToRemove.getId()));
                        return params;
                    }
                };

                queue.add(stringRequest);



                notifyItemRemoved(position);


                Snackbar.make(recyclerView, etudiantToRemove.getNom() +" "+etudiantToRemove.getPrenom()+" deleted", Snackbar.LENGTH_LONG)
                        .setAction(null,null)
                        .show();
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView); // Attach to your RecyclerView
    }


    @Override
    public int getItemCount() {
        return etudiantFilter.size();
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    public class NewFilter extends Filter {
        public RecyclerView.Adapter mAdapter;

        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            Log.d("filterby",charSequence.toString());
            etudiantFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                etudiantFilter.addAll(etudiants);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Etudiant p : etudiants) {
                    if (p.getNom().toLowerCase().startsWith(filterPattern)|| p.getPrenom().toLowerCase().startsWith(filterPattern)) {
                        etudiantFilter.add(p);
                    }
                }
            }
            results.values = etudiantFilter;
            results.count = etudiantFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            etudiantFilter = (List<Etudiant>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }


    public class EtudiantViewHolder extends RecyclerView.ViewHolder {
        TextView idEtudiant;
        ImageView img;
        TextView nomEtudiant;
        TextView villeEtudiant;
        LinearLayout parent;

        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
            idEtudiant = itemView.findViewById(R.id.iditem);
            img = itemView.findViewById(R.id.imgitem);
            nomEtudiant = itemView.findViewById(R.id.nomitem);
            villeEtudiant = itemView.findViewById(R.id.villeitem);
            parent = itemView.findViewById(R.id.parent);
        }
    }

}