package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarketActivity extends AppCompatActivity  {
    RecyclerView recyclerSouk;
    //DrawerLayout drawerLayout;
    //NavigationView navigationView;
    Toolbar toolbar;

    ArrayList<soukList> Mylist;
    CardAdapter Soukadapter;
    //firebase

    DatabaseReference soukRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        recyclerSouk=findViewById(R.id.recyclerView);

        recyclerSouk.setLayoutManager(new LinearLayoutManager(this));
 //navbott
        BottomNavigationView navView=findViewById(R.id.navView);
        navView.setItemIconTintList(null);

//Hooks



        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.getItemId();
                startActivity(new Intent(getApplicationContext(), Scan.class));
                overridePendingTransition(0, 0);
                return true;
            }

        });

//Firebase(Stores)
        soukRef= FirebaseDatabase.getInstance().getReference("Souk");
        soukRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Mylist= new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    soukList data=ds.getValue(soukList.class);
                    Mylist.add(data);

                }
               Soukadapter = new CardAdapter(Mylist,MarketActivity.this);
                recyclerSouk.setAdapter(Soukadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

