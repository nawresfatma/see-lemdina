package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Produits extends AppCompatActivity {

    List<ListProduct> accessoryList;
    RecyclerView recyclerAccessory;
    private SnapHelper snapHelper;
    ScaleCenterItemManager scaleCenterItemManager;
    adapterAccessory adapterAccessory;
    List<ListProduct> listMaisons= new ArrayList<>();
    DatabaseReference ref;
    ImageView store;
//produits

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        store=findViewById(R.id.imageView);

        recyclerAccessory = findViewById(R.id.recycler1);

        // MyAdapterprod myAdapt=new MyAdapterprod(productList,this);
        // recycler2.setAdapter(myAdapt);

       // recycler2.setLayoutManager(new LinearLayoutManager(this));
        //recycler2.setLayoutManager(scaleCenterItemManager);

        BottomNavigationView navView=findViewById(R.id.navView);
        navView.setItemIconTintList(null);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.getItemId();
                startActivity(new Intent(getApplicationContext(), Scan.class));
                overridePendingTransition(0, 0);
                return true;
            }

        });
        //Firebase
        ref = FirebaseDatabase.getInstance().getReference().child("shops").child("store1").child("products");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accessoryList = new ArrayList<>();
                for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                    Toast.makeText(Produits.this, data2.toString(), Toast.LENGTH_LONG).show();
                    ListProduct p1 = data2.getValue(ListProduct.class);
                    accessoryList.add(p1);
                }
                adapterAccessory = new adapterAccessory(accessoryList,Produits.this);
                snapHelper = new LinearSnapHelper();
                scaleCenterItemManager = new ScaleCenterItemManager(Produits.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerAccessory.setLayoutManager(new LinearLayoutManager(Produits.this));
                recyclerAccessory.setLayoutManager(scaleCenterItemManager);
                recyclerAccessory.setAdapter(adapterAccessory);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Produits.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        //recyclerAccessory


        //intent
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Produits.this, SellproductActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }

}