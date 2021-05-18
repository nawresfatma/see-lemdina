package com.example.bassametproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterShops extends RecyclerView.Adapter<adapterShops.MyViewHolder> {
    List<StoreItem> StoreItems = new ArrayList<>();
    Context context;
    public static StoreItem shop;
    public static  String shops="shops";

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
       final StoreItem store = StoreItems.get(position);
        holder.shopName.setText(store.getStoreName());
        holder.shopDescription.setText(store.getStoreDescription());
       // holder.shopImage.setImageResource(R.drawable.rajell);
     Picasso.get().load(StoreItems.get(position).getStoreImage()).into(holder.shopImage);

      //const
        holder.item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
                Intent intent=new Intent(context,Maison.class);
               shop = store;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return StoreItems.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shopName,shopDescription;
        ImageView shopImage;
        ConstraintLayout item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopName=itemView.findViewById(R.id.shopname);
            shopDescription=itemView.findViewById(R.id.shopDescription);
            shopImage=itemView.findViewById(R.id.img);
            item=itemView.findViewById(R.id.itemSeller);

        }
    }
}



