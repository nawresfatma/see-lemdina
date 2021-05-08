package com.example.bassametproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class storeAdapter extends RecyclerView.Adapter<storeAdapter.ViewHolder> {

    List<storeList> listStores;
    Context context;

    public storeAdapter(List<storeList> listStores, Context context) {
        this.listStores = listStores;
        this.context = context;
    }

    @NonNull
    @Override
    public storeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_store,parent,false);
        return  new storeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull storeAdapter.ViewHolder holder, int position) {
       storeList l=listStores.get(position);
        holder.prName.setText(l.getPrName());
        holder.prprice.setText(l.getPrPrice());
        //holder.imageStore.setImageResource(listStore.getImage1());
        Picasso.get().load(listStores.get(position).getPrImg()).into(holder.prImg);

    }

    @Override
    public int getItemCount() {
        return listStores.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView prName;
        public TextView prprice;
        public ImageView prImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prImg= itemView.findViewById(R.id.imagestore) ;
            prName= itemView.findViewById(R.id.title2);
            prprice= itemView.findViewById(R.id.price);

        }
    }
}
