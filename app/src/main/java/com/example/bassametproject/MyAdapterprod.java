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

class MyAdapterprod extends RecyclerView.Adapter<MyAdapterprod.MyViewHolder> {
    List<ListProduct> productList;
    Context context;

    public MyAdapterprod(List<ListProduct> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapterprod.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_item_product,parent,false);
        return new MyAdapterprod.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterprod.MyViewHolder holder, int position) {
        ListProduct productClass=productList.get(position);
        holder.prodName.setText(productClass.getProdName());
        holder.prodDesc.setText(productClass.getProdDescription());
        holder.prodPrice.setText(productClass.getProdPrice());
        holder.prodRemise.setText(productClass.getProdRemise());
        Picasso.get().load(productList.get(position).getProd()).into(holder.prodImg);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView prodName,prodDesc,prodPrice,prodRemise;
        ImageView prodImg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            prodName=itemView.findViewById(R.id.productitemname2);
            prodDesc=itemView.findViewById(R.id.description3);
            prodPrice=itemView.findViewById(R.id.price);
            prodRemise=itemView.findViewById(R.id.off);
            prodImg=itemView.findViewById(R.id.balgha);

        }
    }
}