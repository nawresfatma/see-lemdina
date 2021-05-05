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

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
        List<StoreItem> StoreItems=new ArrayList<>();
        Context context2;

public StoreAdapter(List<com.example.bassametproject.StoreItem> storeItems , Context c) {
        StoreItems = storeItems;
        context2=c;
        }



@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(context2);
        View view=inflater.inflate(R.layout.activity_item_seller,parent,false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        com.example.bassametproject.StoreItem StoreItem=StoreItems.get(position);
        holder.storeName.setText(StoreItem.getStoreName());
        holder.storeDesciption.setText(StoreItem.getStoreDescription());
        // holder.myImage.setImageResource(StoreItem.getStoreImage());
        Picasso.get().load(StoreItems.get(position).getStoreImage()).into(holder.storeImage);
        }

@Override
public int getItemCount() {
        return StoreItems.size();
        }

public static class MyViewHolder extends RecyclerView.ViewHolder {
    TextView storeName,storeDesciption;
    ImageView storeImage;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        storeName=itemView.findViewById(R.id.shopname);
        storeDesciption=itemView.findViewById(R.id.shopDescription);
        storeImage=itemView.findViewById(R.id.StoreImage);

    }
}
}