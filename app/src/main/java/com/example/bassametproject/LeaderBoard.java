package com.example.bassametproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {

    private TextView First, Second, Third;
    private ImageView FirstI, SecondI, ThirdI;
    RecyclerView recyclerView;

    static ArrayList<ListClassement> listClassement = new ArrayList<>();
    rankAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerView3);
        First = findViewById(R.id.FirstplaceName);
        Second = findViewById(R.id.SecondplaceName);
        Third = findViewById(R.id.ThirdplaceName);

        listClassement.add(new ListClassement(1, "fathi", 8000, R.drawable.coin, R.drawable.cube));
        listClassement.add(new ListClassement(2, "mohssen", 1000, R.drawable.coin, R.drawable.img));
        listClassement.add(new ListClassement(3, "nawres", 7000, R.drawable.coin, R.drawable.iimg));
        listClassement.add(new ListClassement(4, "Samy", 8000, R.drawable.coin, R.drawable.iimg));
        adapter = new rankAdapter(listClassement, this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        First.setText(listClassement.get(0).getName());
        Second.setText(listClassement.get(1).getName());
        Third.setText(listClassement.get(2).getName());
        // FirstI.setImageResource(listClassement.get(0).getProfile());
        // SecondI.setImageResource(listClassement.get(1).getProfile());
        //ThirdI.setImageResource(listClassement.get(2).getProfile());

        for (int j = 0; j < 3; j++) {
            listClassement.remove(0);
        }
    }}


