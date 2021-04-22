package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity{
    RecyclerView recyclerView;

    ArrayList<Card> Mylist= new ArrayList<>();
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        recyclerView=findViewById(R.id.recyclerView);
        Mylist.add(new Card("Souk sidi Mehéz ","soukberk"));
        Mylist.add(new Card("Souk  Sidi abdesslem","soukb"));
        Mylist.add(new Card("Souk el sabbaghin ","soukch"));
        adapter =new CardAdapter(Mylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    public void ScanInterface (View v){
        Intent intentLoadNewActivity = new Intent(MarketActivity.this, Scan.class);
        startActivity(intentLoadNewActivity);
    }

    public void MaisonInterface (View v){
        Intent intentLoadNewActivity = new Intent(MarketActivity.this, Maison.class);
        startActivity(intentLoadNewActivity);
    }


    }

