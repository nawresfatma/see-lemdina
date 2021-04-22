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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterEvent extends RecyclerView.Adapter<com.example.bassametproject.adapterEvent.ViewHolder>{
private List<event> eventList=new ArrayList<>();
    private Context context;

    public adapterEvent() {
    }

    public adapterEvent(List<event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public com.example.bassametproject.adapterEvent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_events,parent,false);
        return  new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull com.example.bassametproject.adapterEvent.ViewHolder holder, int position) {
        event e =eventList.get(position);
        holder.eventName.setText(e.getEventName());
        holder.eventLocation.setText(e.getEventLocation());
        Picasso.get().load(eventList.get(position).getEventimg()).into(holder.eventpic);
        holder.eventPrice.setText(e.getEventPrice());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView eventLocation;
        public ImageView eventpic;
        public TextView eventPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
          eventName=(TextView) itemView.findViewById(R.id.eventname);
            eventLocation=(TextView) itemView.findViewById(R.id.eventlocation);
            eventPrice=(TextView) itemView.findViewById(R.id.eventPrice);
            eventpic=(ImageView) itemView.findViewById(R.id.eventpic);
            eventpic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Seller = new Intent();

                }
            });

        }
    }
}
