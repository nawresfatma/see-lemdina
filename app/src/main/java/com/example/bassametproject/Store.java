package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Store extends AppCompatActivity  implements View.OnClickListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView storeRecycler;
    TextView currentPoints;
    User currentPointString;
    ArrayList<storeList> listStores;
    storeAdapter storeAdapter;
    //Firebase
    DatabaseReference storeReference;
    FirebaseAuth fAuth;
    DatabaseReference dbRef,refUser;
    private ImageView userprofile;
    private TextView userName , userEmail , userPoints;
    public static String username , userphotourl  , useremail ,userPoint;
    private FirebaseUser user;
    private DatabaseReference userRef2;
    private FirebaseDatabase database;
    TextView all,pop;
    private DatabaseReference storeReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        storeRecycler=findViewById(R.id.recyclerView5);
        currentPoints=findViewById(R.id.point);
        all =  findViewById(R.id.all);
        pop = findViewById(R.id.popular);
        pop.setOnClickListener(this);
        all.setOnClickListener(this);

        BottomNavigationView navView=findViewById(R.id.bookNow);
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

        fAuth = FirebaseAuth.getInstance();

        user = fAuth.getCurrentUser();

        userRef2 = database.getInstance().getReference("User").child(user.getUid());
        userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    userPoint = ds.child("point").getValue().toString();
                }


                currentPoints.setText(Home.userPoint);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listStores = new ArrayList<>();

        storeReference = FirebaseDatabase.getInstance().getReference("store");
        storeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listStores.clear();

                for (DataSnapshot d1 : dataSnapshot.getChildren()) {

                    if(d1!=null){
                        storeList s = d1.getValue(storeList.class);

                        if(s!=null){

                            Toast.makeText(Store.this, String.valueOf(s.getCategory()), Toast.LENGTH_SHORT).show();
                            listStores.add(s);

                        }
                    }}
                storeAdapter = new storeAdapter(listStores, Store.this);
                storeRecycler.setLayoutManager(new GridLayoutManager(Store.this, 2));
                storeRecycler.setAdapter(storeAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Store.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

//firebase store


    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.all:
                getData(0);
                all.setTextColor(Color.parseColor("#33D085"));
                break;

            case R.id.popular:
                getData(1);
                pop.setTextColor(Color.parseColor("#33D085"));
                break;

        }

    }
    public void getData(int category) {
        storeReference1 = FirebaseDatabase.getInstance().getReference("store");
        storeReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listStores.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    if(d!=null){
                        storeList s1 = d.getValue(storeList.class);
                        if (s1.getCategory()!=category)
                        {
                            s1=null;
                        }

                        if(s1!=null){
                            Toast.makeText(Store.this, String.valueOf(s1.getCategory()), Toast.LENGTH_SHORT).show();
                            listStores.add(s1);
                        }
                    }}
                storeAdapter= new storeAdapter(listStores, Store.this);
                storeRecycler.setLayoutManager(new GridLayoutManager(Store.this, 2));
                storeRecycler.setAdapter(storeAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Store.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void backInterface (View v){
        finish();
    }

}