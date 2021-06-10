package com.example.bassametproject;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Message> messages = new ArrayList<>();

    public MessageAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        ViewHolder holder;
        switch (viewType) {
            case Message.USER_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chatbot, parent, false);
                holder = new ViewHolder(view);
                return holder;
            case Message.BOT_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_chatbot, parent, false);
                holder = new ViewHolder(view);
                return holder;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (holder.getItemViewType() == Message.USER_MESSAGE) {
           Picasso.get().load(Home.userphotourl).resize(500,500).into(holder.image);
          //  Picasso.get().load(User.get(position).getImage()).resize(500,500 ).into(holder.image);
            holder.message.setText(messages.get(position).message);
            holder.time.setText(messages.get(position).createdAt);
        } else {
            holder.image.setImageResource(R.drawable.intro_bot);
            if (position+1 >= messages.size()) {
                holder.is_writing.setVisibility(View.VISIBLE);
                holder.message.setVisibility(View.GONE);
                holder.time.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.message.setText(messages.get(position).message);
                        holder.time.setText(messages.get(position).createdAt);
                        holder.is_writing.setVisibility(View.GONE);
                        holder.message.setVisibility(View.VISIBLE);
                        holder.time.setVisibility(View.VISIBLE);
                    }
                }, 1000);
            } else {
                holder.message.setText(messages.get(position).message);
                holder.time.setText(messages.get(position).createdAt);
                holder.is_writing.setVisibility(View.GONE);
                holder.message.setVisibility(View.VISIBLE);
                holder.time.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        String name = messages.get(position).getSender().getName();
        if ("bot".equalsIgnoreCase(name)) {
            return Message.BOT_MESSAGE;
        } else {
            return Message.USER_MESSAGE;
        }
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private LazyLoader is_writing;
        private TextView message;
        private TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.bot_image);
            is_writing = itemView.findViewById(R.id.bot_is_writing);
            message = itemView.findViewById(R.id.bot_message);
            time = itemView.findViewById(R.id.bot_time);
        }
    }

}

