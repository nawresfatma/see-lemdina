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

        Context context;

public StoreAdapter(List<com.example.bassametproject.StoreItem> storeItems , Context c) {
        StoreItems = storeItems;
        context=c;
        }



@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_home,parent,false);
        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
        myImage=itemView.findViewById(R.id.Storename);

    }
}
}