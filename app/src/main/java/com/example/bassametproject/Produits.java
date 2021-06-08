package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Produits extends AppCompatActivity {

    List<ListProduct> accessoryList1;
    RecyclerView recyclerAccessory;
    private SnapHelper snapHelper;
    ScaleCenterItemManager scaleCenterItemManager;
    adapterAccessory adapterAccessory;
    List<ListProduct> listMaisons;
    DatabaseReference ref;
    ImageView store;
    StoreItem shop;
//produits

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        store=findViewById(R.id.imageView);
        recyclerAccessory = findViewById(R.id.recycler12);
        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerAccessory.setLayoutManager(new LinearLayoutManager(this));
        recyclerAccessory.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recyclerAccessory);
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

        ref = FirebaseDatabase.getInstance().getReference().child("shops");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accessoryList1 = new ArrayList<>();
                for (DataSnapshot data2 : dataSnapshot.child("store1").child("products").getChildren()) {
                    ListProduct p1 = data2.getValue(ListProduct.class);
                    accessoryList1.add(p1);
                }
                adapterAccessory = new adapterAccessory(accessoryList1,Produits.this);

//             recyclerAccessory.setLayoutManager(new LinearLayoutManager(Produits.this));
                //  recyclerAccessory.setLayoutManager(scaleCenterItemManager);
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
        EditText editText = findViewById(R.id.filterProd);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        ArrayList<ListProduct> filteredList = new ArrayList<>();

        for (ListProduct item : accessoryList1) {
            if (item.getProdName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapterAccessory.filterList(filteredList);
    }
}

