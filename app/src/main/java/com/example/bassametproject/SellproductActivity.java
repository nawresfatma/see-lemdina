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
    RecyclerView recyclerM;
    SnapHelper snapHelper , snapHelperM;
    //jdida
    myAdapter myAdapt;

    //firebase

    DatabaseReference mreff,mreff1 ;

    List<StoreItem> StoreItems;
    ScaleCenterItemManager scaleCenterItemManager , scaleCenterItemManagerM;
    //StoreItem s=new StoreItem(R.drawable.rajel,"maison belgassem","best host in the medina , a coffee u would like to try in ur life");
    //StoreItem s1=new StoreItem(R.drawable.rajelekher,"hanout el Bey","best jebba in town and a handmade one");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellproduct);
        recyclerM=findViewById(R.id.recyclerMS);
        recycler=findViewById(R.id.recycler);
        product=findViewById(R.id.Product);





        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recycler);

        //2nd
        snapHelperM = new LinearSnapHelper();
        scaleCenterItemManagerM = new ScaleCenterItemManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerM.setLayoutManager(new LinearLayoutManager(this));
        recyclerM.setLayoutManager(scaleCenterItemManagerM);
        snapHelperM.attachToRecyclerView(recyclerM);




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
                myAdapt = new myAdapter(StoreItems,SellproductActivity.this);
                recycler.setAdapter(myAdapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Firebase(Moresellers)



        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(SellproductActivity.this, products.class);
                startActivity(intentLoadNewActivity);
            }
        });
    }
}