package com.example.bassametproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class adapterPhoto extends RecyclerView.Adapter<adapterPhoto.MyViewHolder> {
    List<Integer> images=new ArrayList<>();
    Context context;

    public adapterPhoto(List img, Context context) {
        images = img;
        this.context = context;
    }

    @NonNull
    @Override
    public adapterPhoto.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_photo_item, parent, false);
        return new adapterPhoto.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterPhoto.MyViewHolder holder, int position) {
        try {
            holder.myImage3.setImageResource(images.get(position));
        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewHolder: Null Pointer: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
            return images.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView myImage3;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myImage3 = itemView.findViewById(R.id.photo);
        }
    }
}