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

public class adapterAccessory extends RecyclerView.Adapter<adapterAccessory.MyViewHolder> {
    List<ListProduct> accessoryList;
    Context context;

    public adapterAccessory(List<ListProduct> accessoryList, Context context) {
        this.accessoryList = accessoryList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListProduct product=accessoryList.get(position);
        holder.prodName.setText(product.getProdName());
        holder.prodPrice.setText(product.getProdPrice());
        Picasso.get().load(accessoryList.get(position).getProd()).into(holder.prodImg1);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.moreaccessory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return accessoryList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView prodName,prodPrice;
        ImageView prodImg1;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            prodName=itemView.findViewById(R.id.prodName);
            prodPrice=itemView.findViewById(R.id.price);
            prodImg1=itemView.findViewById(R.id.prod);

        }
    }
}
