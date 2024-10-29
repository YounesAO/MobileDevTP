package com.sny.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sny.contact.R;
import com.sny.contact.beans.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
        private static final String TAG = "StarAdapter";
        private List<Contact> contacts;
        private Context context;;
        public ContactAdapter(Context context, List<Contact> contacts) {
            this.contacts = contacts;
            this.context = context;
        }
        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(this.context).inflate(R.layout.item,
                    viewGroup, false);
            return new ContactViewHolder(v);
        }
        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
            Log.d(TAG, "onBindView call ! "+ i);

            contactViewHolder.nom.setText(contacts.get(i).getNom().toUpperCase()+" "+contacts.get(i).getPrenom().toUpperCase());
            contactViewHolder.phone.setText(contacts.get(i).getTel()+"");

            contactViewHolder.itemView.setOnClickListener(view -> {
                String phoneNumber = contacts.get(i).getTel();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(dialIntent);
            });
        }
        @Override
        public int getItemCount() {
            return contacts.size();
        }
        public class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView nom;
            TextView phone;
            RelativeLayout parent;
            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                nom = itemView.findViewById(R.id.nom);
                phone = itemView.findViewById(R.id.phone);

                parent = itemView.findViewById(R.id.parent);
            }
        }
    }
