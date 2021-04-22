package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Produits extends AppCompatActivity {

    List<ListProduct> productList ;
    RecyclerView recycler2;
    private SnapHelper snapHelper;
    ScaleCenterItemManager scaleCenterItemManager;
    MyAdapterprod myAdapat1 ;

    DatabaseReference ref;
    ImageView store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        store=findViewById(R.id.imageView);

        recycler2 = findViewById(R.id.recycler1);
        // MyAdapterprod myAdapt=new MyAdapterprod(productList,this);
        // recycler2.setAdapter(myAdapt);
        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler2.setLayoutManager(new LinearLayoutManager(this));
        recycler2.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recycler2);

        //Firebase
        ref = FirebaseDatabase.getInstance().getReference().child("shops").child("store1").child("products");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ListProduct p = dataSnapshot1.getValue(ListProduct.class);

                    productList.add(p);
                }
                myAdapat1 = new MyAdapterprod(productList,Produits.this);
                recycler2.setAdapter(myAdapat1);
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