package com.example.bassametproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rankAdapter extends RecyclerView.Adapter<rankAdapter.MyViewHolder> {
    private List<ListClassement> ListClassement;
    private Context context;

    public rankAdapter(List<com.example.bassametproject.ListClassement> listClassement, Context context) {
        ListClassement = listClassement;
        this.context = context;
    }

    @NonNull
    @Override
    public rankAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranki_tem,parent,false);
        MyViewHolder rankAdapter = new MyViewHolder(view);
        return  rankAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull rankAdapter.MyViewHolder holder, int position) {
        ListClassement listClassement=ListClassement.get(position);
        holder.rank.setText(String.valueOf(listClassement.getRank()));
        holder.name.setText(listClassement.getName());
        holder.points.setText(String.valueOf(listClassement.getPoints()));
        holder.coin.setImageResource(listClassement.getCoin());
        holder.profile.setImageResource(listClassement.getProfile());
    }





    @Override
    public int getItemCount() {
        return ListClassement.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rank;
        public TextView name;
        public TextView points ;
        public ImageView coin;
        public ImageView profile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rank=(TextView) itemView.findViewById(R.id.rank);
            points=(TextView) itemView.findViewById(R.id.points);
            name=(TextView) itemView.findViewById(R.id.namee);
            profile=(ImageView) itemView.findViewById(R.id.profilee);
            coin=(ImageView) itemView.findViewById(R.id.coin);

        }


    }
}
