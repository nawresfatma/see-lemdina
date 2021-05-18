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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SellproductActivity extends AppCompatActivity {
    ImageView product ;
    RecyclerView recycler;
    String soukName;
    SnapHelper snapHelper;
  adapterShops myAdapt;

    //firebase

    DatabaseReference mreff ;

    List<StoreItem> StoreItems;
    ScaleCenterItemManager scaleCenterItemManager ;
    //StoreItem s=new StoreItem(R.drawable.rajel,"maison belgassem","best host in the medina , a coffee u would like to try in ur life");
    //StoreItem s1=new StoreItem(R.drawable.rajelekher,"hanout el Bey","best jebba in town and a handmade one");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellproduct);
        recycler=findViewById(R.id.recyclerMarkett);
        product=findViewById(R.id.products);





        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recycler);



//Firebase(Stores)
        mreff= FirebaseDatabase.getInstance().getReference("shops");
        mreff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StoreItems=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    StoreItem data=ds.getValue(StoreItem.class);
                    StoreItems.add(data);

                }
                myAdapt = new adapterShops(StoreItems,SellproductActivity.this);
                recycler.setAdapter(myAdapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(SellproductActivity.this, Produits.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
    public void ScanInterface (View v){
        Intent intentLoadNewActivity = new Intent(SellproductActivity.this, Scan.class);
        startActivity(intentLoadNewActivity);
    }
}