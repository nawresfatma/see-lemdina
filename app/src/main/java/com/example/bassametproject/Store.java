package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Store extends AppCompatActivity {

    RecyclerView storeRecycler;
    TextView currentPoints;
    User currentPointString;
    ArrayList<storeList> listStores;
    storeAdapter storeAdapter;
    //Firebase
    DatabaseReference storeReference;
    FirebaseAuth fAuth;
    DatabaseReference dbRef,refUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        storeRecycler=findViewById(R.id.recyclerView5);
        currentPoints=findViewById(R.id.point);


       /*listStores.add(new ListStore("chachia" , "100" ,R.drawable.chachia));
        listStores.add(new ListStore("chachia" , "100" ,R.drawable.chachia));
        listStores.add(new ListStore("chachia" , "100",R.drawable.chachia));
        listStores.add(new ListStore("chachia" , "100",R.drawable.chachia));*/

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

     /*p
        dbRef = FirebaseDatabase.getInstance().getReference("User");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User u = snapshot.child("user").getValue(User.class);
              currentPoints.setText(String.valueOf(u.getPoint()));
                Toast.makeText(Store.this, currentPoints.toString(), Toast.LENGTH_SHORT).show();
                    }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(Store.this, " something is wrong !", Toast.LENGTH_SHORT).show();
            }
        }
            );
*/
//firebase store
        storeReference= FirebaseDatabase.getInstance().getReference("store");
        storeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listStores=new ArrayList<>();
                for(DataSnapshot d1: dataSnapshot.getChildren())
                {


                    storeList s = d1.getValue(storeList.class);
                    listStores.add(s);


                }


                storeAdapter = new storeAdapter(listStores,Store.this);
                storeRecycler.setLayoutManager(new GridLayoutManager(Store.this,2));
                storeRecycler.setAdapter(storeAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Store.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }
}