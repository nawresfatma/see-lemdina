package com.example.bassametproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bassametproject.ProgramClass;
import com.example.bassametproject.R;

import java.util.List;

public class adapterProgram extends RecyclerView.Adapter<adapterProgram.MyViewHolder> {
    private List<ProgramClass> programList;
    private Context context;

    public adapterProgram(List<ProgramClass> programList, Context context) {
        this.programList = programList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.program,parent,false);
        return new adapterProgram.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProgramClass programClass=programList.get(position);
        holder.myImage4.setImageResource(programClass.getFormateur());
        holder.myText.setText(programClass.getPrg());
        holder.myText2.setText(programClass.getPoste());
        holder.myText3.setText(programClass.getBio());
        holder.myText4.setText(programClass.getTiming());
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myText,myText2,myText3,myText4;
        ImageView myImage4;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myText=itemView.findViewById(R.id.activity);
            myText2=itemView.findViewById(R.id.poste);
            myText3=itemView.findViewById(R.id.bio);
            myText4=itemView.findViewById(R.id.time);
            myImage4=itemView.findViewById(R.id.formateur);
        }
    }
}
