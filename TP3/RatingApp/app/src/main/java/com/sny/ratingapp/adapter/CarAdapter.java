package com.sny.ratingapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sny.ratingapp.R;
import com.sny.ratingapp.beans.Car;
import com.sny.ratingapp.service.CarService;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> implements Filterable {
    private static final String TAG = "CarAdapter";
    private List<Car> cars;
    private List<Car> carFilter;
    private NewFilter mfilter;
    private Context context;


    public CarAdapter(Context context, List<Car> cars) {
        this.cars = cars;
        this.context = context;
        carFilter = new ArrayList<>();
        carFilter.addAll(cars);
        mfilter = new NewFilter(this);

    }
    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item,
                viewGroup, false);
        CarViewHolder holder=new CarViewHolder(v);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popup = LayoutInflater.from(context).inflate(R.layout.edit_item, null,
                        false);
                final ImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar);
                final TextView idc = popup.findViewById(R.id.idc);
                final TextView name = popup.findViewById(R.id.name);
                Bitmap bitmap =
                        ((BitmapDrawable)((ImageView)v.findViewById(R.id.img)).getDrawable()).getBitmap();
                img.setImageBitmap(bitmap);
                bar.setRating(((RatingBar)v.findViewById(R.id.stars)).getRating());
                idc.setText(((TextView)v.findViewById(R.id.idc)).getText().toString());
                name.setText(((TextView)v.findViewById(R.id.marque)).getText().toString()+" "+((TextView)v.findViewById(R.id.model)).getText().toString());
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Notez : ")
                        .setMessage("Donner une note entre 1 et 5 :")
                        .setView(popup)
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {


                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(idc.getText().toString());
                                Car car = CarService.getInstance().findById(ids);
                                car.setStars(s);
                                CarService.getInstance().update(car);
                                notifyItemChanged(holder.getAdapterPosition());
                            }

                        })
                        .setNegativeButton("Annuler", null)
                        .create();
                dialog.show();
            }
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull CarViewHolder carViewHolder, int i) {
        Log.d(TAG, "onBindView call ! "+ i);
        Glide.with(context)
                .asBitmap()
                .load(carFilter.get(i).getImg())
                .apply(new RequestOptions().override(50,50 ))
                .into(carViewHolder.img);
        carViewHolder.marque.setText(carFilter.get(i).getMarque().toUpperCase());
        carViewHolder.model.setText(carFilter.get(i).getModel().toUpperCase());
        carViewHolder.categorie.setText(carFilter.get(i).getCategorie().toUpperCase());
        carViewHolder.stars.setRating(carFilter.get(i).getStars());
        carViewHolder.idCar.setText(carFilter.get(i).getId()+"");
    }
    @Override
    public int getItemCount() {
        return carFilter.size();
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
            carFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                carFilter.addAll(cars);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Car p : cars) {
                    if (p.getMarque().toLowerCase().startsWith(filterPattern)|| p.getModel().toLowerCase().startsWith(filterPattern)) {
                        carFilter.add(p);
                    }
                }
            }
            results.values = carFilter;
            results.count = carFilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            carFilter = (List<Car>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }




    public class CarViewHolder extends RecyclerView.ViewHolder {
        TextView idCar;
        ImageView img;
        TextView model;
        TextView categorie;
        TextView marque;
        RatingBar stars;
        RelativeLayout parent;
        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            idCar = itemView.findViewById(R.id.idc);
            img = itemView.findViewById(R.id.img);
            marque = itemView.findViewById(R.id.marque);
            model = itemView.findViewById(R.id.model);
            categorie = itemView.findViewById(R.id.categorie);
            stars = itemView.findViewById(R.id.stars);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
