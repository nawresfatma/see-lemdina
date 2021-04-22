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

public class myAdapterrecy extends RecyclerView.Adapter<myAdapterrecy.MyViewHolder> {
    List<ListProduct> ListsMaisons=new ArrayList<>();
    Context context;

    public myAdapterrecy(List<ListProduct> listMaisons, Context c) {
        ListsMaisons = listMaisons;
        context = c;
    }

    @NonNull
    @Override
    public myAdapterrecy.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_products_recycler,parent,false);
        return new myAdapterrecy.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListProduct list=ListsMaisons.get(position);
        holder.prodName.setText(list.getProdName());
        holder.prodDesc.setText(list.getProdDescription());
        holder.prodPrice.setText(list.getProdPrice());
        // holder.myImage1.setImageResource(list.getProd());
        Picasso.get().load(ListsMaisons.get(position).getProd()).into(holder.prodImg);
    }

    @Override
    public int getItemCount() {
        return ListsMaisons.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView prodName,prodDesc,prodPrice;
        ImageView prodImg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            prodName=itemView.findViewById(R.id.productitemname2);
            prodDesc=itemView.findViewById(R.id.description3);
            prodPrice=itemView.findViewById(R.id.price2);
            prodImg=itemView.findViewById(R.id.Imgproduct);

        }
    }
}
