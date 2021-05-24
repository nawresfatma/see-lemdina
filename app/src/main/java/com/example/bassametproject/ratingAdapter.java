package com.example.bassametproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ratingAdapter extends RecyclerView.Adapter<ratingAdapter.RatingViewHolder> {

    Context context;
    ArrayList<RatingComment> ratingComments;

    public ratingAdapter(Context c , ArrayList<RatingComment> r)
    {
        context = c;
        ratingComments = r;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RatingViewHolder(LayoutInflater.from(context).inflate(R.layout.ratingrecycler , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        holder.name.setText(ratingComments.get(position).getName());
        holder.description.setText(ratingComments.get(position).getDescription());
        holder.title.setText(ratingComments.get(position).getTitle());
        Picasso.get().load(ratingComments.get(position).getImage()).resize(500 , 500).into(holder.imgrating);
        holder.ratingBar.setRating(ratingComments.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return ratingComments.size();
    }

    class RatingViewHolder extends  RecyclerView.ViewHolder
    {

        TextView name , description , title;
        ImageView imgrating;
        RatingBar ratingBar;
        String valuestring;
        float valuefloat;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.namerating);
            description = (TextView) itemView.findViewById(R.id.descriptionrating);

            title = (TextView) itemView.findViewById(R.id.title);

            imgrating = (ImageView) itemView.findViewById(R.id.imgcardviewrate);

            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingbarrating);

        }
    }
}

