package com.sny.drawerapp.ui.edit;

import static android.app.Activity.RESULT_OK;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.sny.drawerapp.R;
import com.sny.drawerapp.databinding.FragmentEditBinding;
import com.sny.drawerapp.databinding.FragmentHomeBinding;
import com.sny.drawerapp.ui.profile.ProfileFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditFragment extends Fragment {

    private EditViewModel mViewModel;

    private EditText nomEditText, prenomEditText;
    private Spinner villeSpinner;
    private ImageView imageView;
    private RadioGroup sexeRadioGroup;
    private Button saveButton;
    private Button editImgBtn;
    private FragmentEditBinding binding;
    private String image64;

    private int etudiantId; // Store the ID of the Etudiant

    public static EditFragment newInstance() {
        return new EditFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize views
        nomEditText = binding.nomup;
        prenomEditText = binding.prenomup;
        villeSpinner = binding.villeup;
        sexeRadioGroup = binding.sexeradioup;
        saveButton = binding.upBtn;
        imageView = binding.imageEdit;
        editImgBtn = binding.editImgBtn;


        // Retrieve the Etudiant data from arguments
        if (getArguments() != null) {
            etudiantId = getArguments().getInt("id");
            String nom = getArguments().getString("nom");
            String prenom = getArguments().getString("prenom");
            String ville = getArguments().getString("ville");
            String sexe = getArguments().getString("sexe");
            image64 = getArguments().getString("image");

            // Populate the fields with the passed data
            populateData(nom, prenom, ville, sexe, image64);
        }

        editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);

                // pass the constant to compare it
                // with the returned requestCode
                startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
            }
        });
        // Set onClick listener for saving changes
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nomEditText.getText().toString();
                String prenom = prenomEditText.getText().toString();
                String ville = villeSpinner.getSelectedItem().toString();
                String sexe = getSelectedSexe();

                // Call method to save changes via a Volley request
                updateEtudiant(etudiantId, nom, prenom, ville, sexe,image64);
                Snackbar.make(v, "Etudiant updated successuflly", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //NavController navController = Navigation.findNavController((AppCompatActivity) v.getContext(), R.id.nav_host_fragment_content_main);
               // navController.navigate(R.id.nav_host_fragment_content_main);
            }
        });


        return root;
    }

    // Populate the fields with the current data
    private void populateData(String nom, String prenom, String ville, String sexe, String image) {
        nomEditText.setText(nom);
        prenomEditText.setText(prenom);

        // Set Spinner selection
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) villeSpinner.getAdapter();
        int spinnerPosition = adapter.getPosition(ville);
        villeSpinner.setSelection(spinnerPosition);

        // Set RadioGroup selection
        if (sexe.equals("homme")) {
            sexeRadioGroup.check(R.id.m);
        } else if (sexe.equals("femme")) {
            sexeRadioGroup.check(R.id.f);
        }
        loadBase64Image(image, getContext(), imageView);


    }

    // Get selected gender
    private String getSelectedSexe() {
        int selectedId = sexeRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.m) {
            return "homme";
        } else if (selectedId == R.id.f) {
            return "femme";
        }
        return "";
    }

    // Volley request to update the Etudiant
    private void updateEtudiant(int id, String nom, String prenom, String ville, String sexe,String image) {
        String url = "http://10.0.2.2/phpE/ws/updateEtudiant.php"; // Your update URL
        Log.d("TAG","inside api");
        RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG","on response");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG",error.getMessage());
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Put the parameters in a HashMap
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("ville", ville);
                params.put("sexe", sexe);
                Log.d("TAG","on map params");

                params.put("image", image);
                return params;
            }
        };
        Log.d("TAG","add to quess");

        queue.add(stringRequest);
    }

    private void loadBase64Image(String image, Context context, ImageView imageview) {
        byte[] decodedBytes = Base64.decode(image, Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        // Use Glide to load the Bitmap
        Glide.with(context)
                .asBitmap()
                .load(bitmap)  // Load the decoded Bitmap
                .into(imageview);
        ;
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 200) {
                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {

                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImageUri);
                        String base64String = bitmapToBase64(bitmap);
                        image64 = base64String;
                        Glide.with(this).load(bitmap).into(imageView);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    /**
    @Override

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                // Convert URI to Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), selectedImageUri);

                // Convert Bitmap to Base64 string
                String base64String = bitmapToBase64(bitmap);

                // Optionally log or use the Base64 string
                Log.d("Base64String", base64String);

                // Load the image into an ImageView using Glide
                Glide.with(this).load(bitmap).into(imageView);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
**/
    // Method to convert Bitmap to Base64 string
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        // TODO: Use the ViewModel
    }

}