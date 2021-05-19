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
    DrawerLayout drawerLayout;
    NavigationView navigationView;
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
        /*//navbott
        BottomNavigationView navView=findViewById(R.id.navView);
        navView.setItemIconTintList(null);

//Hooks
        drawerLayout=findViewById(R.id.container);
        navigationView=findViewById(R.id.nav_menu);
        toolbar=findViewById(R.id.menubut);
//toolbar
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);*/
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



    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param v The selected item
     * @return true to display the item as the selected item
     **/
   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.sign_scanner:
                Intent ScanIntent = new Intent(MarketActivity.this, Scan.class);
                startActivity(ScanIntent);
                break;
            case R.id.achivements:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Intent ProfileIntent = new Intent(MarketActivity.this, Profile.class);
                startActivity(ProfileIntent);
                break;
            case R.id.map:
                Intent MapIntent = new Intent(MarketActivity.this, MapBoxActivity.class);
                startActivity(MapIntent);
                break;
            case R.id.LeaderBoard:
                Intent LeaderBoardIntent = new Intent(MarketActivity.this, LeaderBoard.class);
                startActivity(LeaderBoardIntent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.getItemId();
                startActivity(new Intent(getApplicationContext(), Scan.class));
                overridePendingTransition(0, 0);
                return true;
            }

        });
*/

    public void ScanInterface (View v){
        Intent intentLoadNewActivity = new Intent(MarketActivity.this, Scan.class);
        startActivity(intentLoadNewActivity);
    }

    public void MaisonInterface (View v){
        Intent intentLoadNewActivity = new Intent(MarketActivity.this, Maison.class);
        startActivity(intentLoadNewActivity);
    }



    }

