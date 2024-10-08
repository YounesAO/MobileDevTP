package com.sny.pizzarecipes.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sny.pizzarecipes.R;
import com.sny.pizzarecipes.classes.Produit;

import java.util.List;

public class PizzaAdapter extends BaseAdapter {
    private List<Produit> produits;
    private LayoutInflater inflater;

    public PizzaAdapter(List<Produit> produits, Activity activity) {
        this.produits = produits;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return produits.size();
    }

    @Override
    public Object getItem(int position) {
        return produits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = inflater.inflate(R.layout.item,null);

        TextView name = convertView.findViewById(R.id.name);
        TextView nbIngredient = convertView.findViewById(R.id.nbIngredient);
        TextView duration = convertView.findViewById(R.id.duration);
        ImageView img = convertView.findViewById(R.id.imagePizza);
        name.setText(produits.get(position).getNom());
        nbIngredient.setText(produits.get(position).getNbrIngredients()+"");
        duration.setText(produits.get(position).getDuree()+"");
        img.setImageResource(produits.get(position).getPhoto());

        return convertView;
    }
}
