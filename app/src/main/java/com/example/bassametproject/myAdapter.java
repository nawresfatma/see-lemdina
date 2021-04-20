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

public class myAdapter extends RecyclerView.Adapter<myAdapter.MyViewHolder> {
    List<com.example.bassametproject.StoreItem> StoreItems=new ArrayList<>();

    Context context;

    public myAdapter(List<com.example.bassametproject.StoreItem> storeItems , Context c) {
        StoreItems = storeItems;
        context=c;

    }

    @NonNull
    @Override
    public com.example.bassametproject.myAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_item_seller,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.bassametproject.myAdapter.MyViewHolder holder, int position) {
        com.example.bassametproject.StoreItem StoreItem=StoreItems.get(position);
        holder.myText.setText(StoreItem.getStoreName());
        holder.myText2.setText(StoreItem.getStoreDescription());
       // holder.myImage.setImageResource(StoreItem.getStoreImage());
        Picasso.get().load(StoreItems.get(position).getStoreImage()).into(holder.myImage);
    }

    @Override
    public int getItemCount() {
        return StoreItems.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText,myText2;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText=itemView.findViewById(R.id.shopname);
            myText2=itemView.findViewById(R.id.description);
            myImage=itemView.findViewById(R.id.rajel);

        }
    }
}