package com.example.bassametproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView recycler;
   // com.example.bassametproject.adapterEvent adapterevent;
    private SnapHelper snapHelper;
   // List<com.example.bassametproject.event> eventList1;
    //Firebase
    DatabaseReference mref,eventReference;
    //intents
    ImageView market , map , missions;
    //storerecycler
    List<StoreItem> StoreItems;
    ScaleCenterItemManager scaleCenterItemManager;
   adapterShops myAdapt ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recycler=findViewById(R.id.recyclerMarket);
        //stores
        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recycler);
        //events
     /*
        com.example.bassametproject.ScaleCenterItemManager scaleCenterItemManager= new com.example.bassametproject.ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL,false);
        recycler3.setLayoutManager(new LinearLayoutManager(this));
        recycler3.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recycler3);**/
        /*  eventReference = FirebaseDatabase.getInstance().getReference().child("Events");
        eventReference.addValueEventListener(new ValueEventListener() {
          @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList1 = new ArrayList<event>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    event e = dataSnapshot1.getValue(event.class);
                    eventList1.add(e);
                }
                adapterevent = new adapterEvent(eventList1,Home.this);
                recycler3.setAdapter(adapterevent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        }); */
        //Stores

        mref= FirebaseDatabase.getInstance().getReference("shops");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StoreItems=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    StoreItem data=ds.getValue(StoreItem.class);
                    Toast.makeText(Home.this, data.toString(), Toast.LENGTH_LONG).show();

                    StoreItems.add(data);
                }
                myAdapt = new adapterShops(StoreItems,Home.this);
                recycler.setAdapter(myAdapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //INTENTS
        market=findViewById(R.id.marketbut);
        map=findViewById(R.id.mapbut);
        missions=findViewById(R.id.missbut);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Home.this,MarketActivity.class);
                //normalement bel market
                startActivity(intentLoadNewActivity);

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Home.this, MapBoxActivity.class);
                //normalement bel map
                startActivity(intentLoadNewActivity);

            }
        });

        missions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Home.this, MissionsActivity.class);
                //normalement bel map
                startActivity(intentLoadNewActivity);

            }
        });

    }
    public void ScanInterface (View v){
        Intent intentLoadNewActivity = new Intent(Home.this, Scan.class);
        startActivity(intentLoadNewActivity);
    }
}