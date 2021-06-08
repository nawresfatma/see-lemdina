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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    static ArrayList<soukList> CardList;
    Context c;
    public static soukList soukStatic;
    public static  String shops="souk";

    public CardAdapter(ArrayList<soukList> cardList, Context c) {
        CardList = cardList;
        this.c = c;
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
        final soukList souk =CardList.get(position);
        holder.myText.setText(souk.getSoukName());
       Picasso.get().load(CardList.get(position).getVidioUrl()).into(holder.myImage);

        //const
        holder.itemSouk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              //  ((Activity)c).finish();
                Intent intent=new Intent(c,SellproductActivity.class);
                intent.putExtra("soukName", souk.getSoukName());
                intent.putExtra("soukImage",CardList.get(position).getVidioUrl());
                soukStatic = souk;
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return CardList.size();
    }
    public void filterList(ArrayList<soukList> filteredList) {
        CardList = filteredList;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myText;
        ImageView myImage;
        ConstraintLayout itemSouk;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myText=itemView.findViewById(R.id.mycardtitle);
            myImage=itemView.findViewById(R.id.mycardimage);
            itemSouk=itemView.findViewById(R.id.itemSouk);


        }
    }


}
