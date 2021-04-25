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

public class rankAdapter extends RecyclerView.Adapter<rankAdapter.RatingViewHolder>{

    Context context;
    ArrayList<User> users;

    public rankAdapter(Context c , ArrayList<User> u)
    {
        context = c;
        users = u;
    }

    @NonNull
    @Override
    public rankAdapter.RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new rankAdapter.RatingViewHolder(LayoutInflater.from(context).inflate(R.layout.ranki_tem, parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull rankAdapter.RatingViewHolder holder, int position) {
        Picasso.get().load(users.get(position).getImage()).resize(500 , 500).into(holder.userimg);
        holder.username.setText(users.get(position).getName());
        holder.userpoints.setText(String.valueOf(users.get(position).getPoint()));
        holder.position.setText(String.valueOf(users.get(position).getRank()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class RatingViewHolder extends  RecyclerView.ViewHolder{

        ImageView userimg;
        TextView username , userpoints , position;
        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);

            userimg = (ImageView) itemView.findViewById(R.id.profilee);
            username = (TextView) itemView.findViewById(R.id.namee);
            userpoints = (TextView) itemView.findViewById(R.id.points);
            position = (TextView) itemView.findViewById(R.id.rank);

        }
    }

}
