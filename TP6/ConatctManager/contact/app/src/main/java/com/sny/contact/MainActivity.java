package com.sny.contact;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.Manifest;

import com.sny.contact.adapter.ContactAdapter;
import com.sny.contact.beans.Contact;
import com.sny.contact.service.ContactService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Contact> contacts;
    private RecyclerView recyclerView;
    private ContactService service;
    private ContactAdapter contactAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();
        service = ContactService.getInstance();

        // Check for permissions and request if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            // Permission already granted
            loadContacts();
            recyclerView = findViewById(R.id.recycle_view);
            contactAdapter = new ContactAdapter(this, service.findAll());
            recyclerView.setAdapter(contactAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));        }
        Log.d("tag", "Loaded contacts size: " + service.findAll().size());

    }
        private void loadContacts() {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {

                while(cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    String[] nameParts = name.split(" ", 2);
                    String lastName = nameParts[0];
                    String firstName = nameParts.length > 1 ? nameParts[1] : "";

                    Contact contact = new Contact(firstName, lastName, phoneNumber);
                    service.create(contact);
                }
                cursor.close();
            }
            Log.d("tag", "Loaded contacts size: " + service.findAll().size());
        }

        private String formatContactString(Contact contact) {
            return "Name: " + contact.getNom() + " " + contact.getPrenom() + ", Tel: " + contact.getTel();
        }
    }