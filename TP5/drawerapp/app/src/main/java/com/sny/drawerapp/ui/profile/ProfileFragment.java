package com.sny.drawerapp.ui.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.sny.drawerapp.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private int id;
    private String nom;
    private String prenom;
    private String ville;
    private String sexe;
    private String image;
    private Button updateBtn;
    private Button deleteButton;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Retrieve data from arguments
        if (getArguments() != null) {
            id = getArguments().getInt("id", -1);
            nom = getArguments().getString("nom");
            prenom = getArguments().getString("prenom");
            ville = getArguments().getString("ville");
            sexe = getArguments().getString("sexe");
            image = getArguments().getString("image");
        }

        // Display the data in the UI
        displayEtudiantDetails(view);

        updateBtn = view.findViewById(R.id.update_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "modifier un nouveau etudiant", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("nom", nom);
                bundle.putString("prenom", prenom);
                bundle.putString("ville", ville);
                bundle.putString("sexe", sexe);
                bundle.putString("image", image);
                NavController navController = Navigation.findNavController((AppCompatActivity) v.getContext(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.editFragment,bundle);

            }
        });
        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDeleteDialog();

            }
        });

        return view;
    }

    /** permition methode get the yes ansewer ->> delete
     *  .......:::://///php/deleteEtudint endpoint\\\\\::::.......
      */

    private void confirmationDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you want to delete this?");
        /***  dial Yes  $ $ $ ***/
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem();
                NavController navController = Navigation.findNavController(getView());
                navController.popBackStack();
            }
        });
        /*** no ***/
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the dialog
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem() {
        String url = "http://10.0.2.2/phpE/ws/deleteEtudiant.php"; // Your update URL

        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Etudiant deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Failed to delete etudiant", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }
        };

        queue.add(stringRequest);

    }
    private void displayEtudiantDetails(View view) {
        ImageView imageView = view.findViewById(R.id.profile_image);
        TextView nomTextView = view.findViewById(R.id.nomprofile);
        TextView prenomTextView = view.findViewById(R.id.prenomprofile);
        TextView villeTextView = view.findViewById(R.id.villeprofile);
        TextView sexeTextView = view.findViewById(R.id.sexeprofile);

        // Set the data from the arguments to the TextViews
        Bitmap bitmap = decodeBase64ToBitmap(image);

        // Use Glide to load the Bitmap
        Glide.with(getContext())
                .asBitmap()
                .load(bitmap)  // Load the decoded Bitmap
                .into(imageView);
        nomTextView.setText(nom);
        prenomTextView.setText(prenom);
        villeTextView.setText(ville);
        sexeTextView.setText(sexe);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }
    private Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}