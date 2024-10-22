package com.sny.drawerapp.ui.gallery;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.sny.drawerapp.R;
import com.sny.drawerapp.databinding.FragmentGalleryBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;




/**
 * Younes AO owned;;;;;;
 * **/
public class GalleryFragment extends Fragment {
    private EditText nomEditText, prenomEditText;
    private Spinner villeSpinner;
    private RadioGroup sexeRadioGroup;
    private ImageView imageView;
    private String image64;
    private Button addEtudiantButton;
    private Button editImgBtn;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /**
         * utilisation de binding instead to find bind find find .........
         * **/
        addEtudiantButton = binding.addBtn;
        nomEditText = binding.nomadd;
        prenomEditText = binding.prenomadd;
        villeSpinner = binding.villeadd;
        sexeRadioGroup = binding.sexeradio;
        imageView = binding.imageView4;

        editImgBtn = binding.editImgBtn;
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

        addEtudiantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * event onclick envoyer data to php api -----
                 * **/
                String nom = nomEditText.getText().toString().toUpperCase();
                String prenom = prenomEditText.getText().toString().toUpperCase();
                String ville = villeSpinner.getSelectedItem().toString();
                String sexe = getSelectedSexe();

                addEtudiant(nom, prenom, ville, sexe);
            }
        });
        return root;
    }

    /**
     * sexe =====> string
     * **/    private String getSelectedSexe() {
        int selectedId = sexeRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.m) {
            return "homme";
        } else if (selectedId == R.id.f) {
            return "femme";
        }
        return "";
    }


    /**
     * send req to xamppppp -----
     * **/
    private void addEtudiant(String nom, String prenom, String ville, String sexe) {
        String url = "http://10.0.2.2/phpE/ws/createEtudiant.php"; // Change URL if needed

        // Create a new request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // Create a POST request with a StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle success (e.g., show a Toast or navigate back)
                        Toast.makeText(getContext(), "Etudiant added successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Toast.makeText(getContext(), "Failed to add etudiant", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Put the parameters in a HashMap
                Map<String, String> params = new HashMap<>();
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("ville", ville);
                params.put("sexe", sexe);
                params.put("image", image64);
                return params;
            }
        };

        // Add the request to the queue
        queue.add(stringRequest);
    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 200) {
                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {

                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImageUri);
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}