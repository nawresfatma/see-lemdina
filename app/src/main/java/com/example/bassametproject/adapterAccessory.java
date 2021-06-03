package com.example.bassametproject;

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

import java.util.List;

public class adapterAccessory extends RecyclerView.Adapter<adapterAccessory.MyViewHolder> {
    List<ListProduct> accessoryList;
    Context context;
    public static ListProduct productStatic;
    public static StoreItem shopStatic;

    public adapterAccessory(List<ListProduct> accessoryList, Context context) {
        this.accessoryList = accessoryList;
        this.context = context;
    }
//commit
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListProduct product=accessoryList.get(position);
        holder.prodName.setText(product.getProdName());
        holder.prodPrice.setText(product.getProdPrice());
        Picasso.get().load(accessoryList.get(position).getProd()).resize(500,300).into(holder.prodImg1);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, produitActivity.class);

                productStatic = product;
                context.startActivity(intent);
            }
        });
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
        ConstraintLayout item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            prodName=itemView.findViewById(R.id.prodName);
            prodPrice=itemView.findViewById(R.id.price);
            prodImg1=itemView.findViewById(R.id.prod);
            item=itemView.findViewById(R.id.produitItem);

        }
    }
}
