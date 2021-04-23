package com.example.bassametproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    ArrayList<Card> CardList;
    Context c;

    public CardAdapter(ArrayList<Card> list){
        this.CardList = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycard,parent,false);
        MyViewHolder cardAdapter = new MyViewHolder(view);
        return cardAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.myText.setText(CardList.get(position).getTitle());
        holder.myImage.setImageResource(R.drawable.soukberk);

       holder.myText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), SellproductActivity.class));

            }});
    }

    @Override
    public int getItemCount() {
        return CardList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText;
        ImageView myImage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myText=itemView.findViewById(R.id.mycardtitle);
            myImage=itemView.findViewById(R.id.mycardimage);

        }
    }


}
