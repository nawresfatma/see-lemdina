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

public class adapterShopsHome extends RecyclerView.Adapter<adapterShopsHome.MyViewHolder> {
    List<StoreItem> StoreItems = new ArrayList<>();
    Context context;
    public static StoreItem shop1;
    public static  String shops="shops";
    String i="2";

    public adapterShopsHome(List<StoreItem> storeItems, Context c) {
        StoreItems = storeItems;
        context = c;
    }
//commit

    @NonNull
    @Override
    public adapterShopsHome.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_menu_sellers, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull adapterShopsHome.MyViewHolder holder, int position) {
        final StoreItem store = StoreItems.get(position);
        holder.shopName.setText(store.getStoreName());
        holder.shopDescription.setText(store.getStoreDescriptionShort());
        // holder.shopImage.setImageResource(R.drawable.rajell);
        Picasso.get().load(StoreItems.get(position).getStoreImage()).resize(690,400 ).into(holder.shopImage);

        //const
        holder.item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Maison.class);
                shop1 = store;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return StoreItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return StoreItems.get(position).getType();
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