package com.example.bassametproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class missionAdapter  extends RecyclerView.Adapter<missionAdapter.ViewHolder> {
    public List<missionList> ListMissions;
    public Context context;


    public missionAdapter(ArrayList<missionList> listMissions , Context context) {
        this.ListMissions=listMissions;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mission_card,parent,false);
        return  new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        missionList listMissions=ListMissions.get(position);
        holder.title1.setText(listMissions.getTitle());
        holder.desc1.setText(listMissions.getDesc());
        holder.img1.setImageResource(listMissions.getImage());
        holder.discover.setText(listMissions.getDiscover());
    }

    @Override
    public int getItemCount() {
        return ListMissions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title1;
        public TextView desc1;
        public ImageView img1;
        public TextView discover ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title1=(TextView) itemView.findViewById(R.id.title1);
            desc1=(TextView) itemView.findViewById(R.id.description1);
            discover=(TextView) itemView.findViewById(R.id.discover1);
            img1=(ImageView) itemView.findViewById(R.id.img1);

        }
    }

}


