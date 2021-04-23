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

import java.util.ArrayList;
import java.util.List;

public class adapterShops extends RecyclerView.Adapter<adapterShops.MyViewHolder> {
    List<StoreItem> StoreItems = new ArrayList<>();
    Context context;

    public adapterShops(List<StoreItem> storeItems, Context c) {
        StoreItems = storeItems;
        context = c;
    }
//commit

    @NonNull
    @Override
    public adapterShops.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_item_seller,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterShops.MyViewHolder holder, int position) {
        StoreItem store = StoreItems.get(position);
        holder.shopName.setText(store.getStoreName());
        holder.shopDescription.setText(store.getStoreDescription());
        // holder.myImage.setImageResource(StoreItem.getStoreImage());
      Picasso.get().load(StoreItems.get(position).getStoreImage()).into(holder.shopImage);
    }

    @Override
    public int getItemCount() {
        return StoreItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shopName,shopDescription;
        ImageView shopImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName=itemView.findViewById(R.id.shopname);
            shopDescription=itemView.findViewById(R.id.shopDescription);
            shopImage=itemView.findViewById(R.id.StoreImage);

        }
    }
}



