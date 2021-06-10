package com.example.bassametproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.bassametproject.adapterShops.shop;

public class Home extends MarketActivity  implements NavigationView.OnNavigationItemSelectedListener{
    //drawer
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recycler,recyclerevent;
    // com.example.bassametproject.adapterEvent adapterevent;
    private SnapHelper snapHelper,snapHelperevent;
    adapterEvent adapterEvent;
    //listevent
    List<eventList> eventslist;
    //  eventList e =new eventList("barcha alwen","mdina","25" ,R.drawable.eventone);
    //eventList e1 =new eventList("barcha barcha alwen","mdina1","10" ,R.drawable.photoone);
    //eventList e2 =new eventList("barcha chwaya alwen","mdina2","15" ,R.drawable.phototwo);
    private ImageView userprofile;
    private TextView userName , userEmail , userPoints;


    // List<com.example.bassametproject.event> eventList1;
    //Firebase
    DatabaseReference mref,eventReference;
    //intents
    ImageView market , map , missions;
    //storerecycler
    List<StoreItem> StoreItems;
    ScaleCenterItemManager scaleCenterItemManager;
    adapterShopsHome myAdapt ;
    ImageView Achievements;

    private FirebaseDatabase database;
    private DatabaseReference userRef2;
    private FirebaseAuth fAuth;
    private FirebaseUser user;

    public static String username , userphotourl  , useremail ,userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//getdata
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_menu);
        View headerView = navigationView.getHeaderView(0);
        userprofile = headerView.findViewById(R.id.userprofile);
        userName = headerView.findViewById(R.id.textView8);
        userEmail = headerView.findViewById(R.id.textView28);
        userPoints = headerView.findViewById(R.id.textView25);

        fAuth = FirebaseAuth.getInstance();

        user = fAuth.getCurrentUser();

        userRef2 = database.getInstance().getReference("User").child(user.getUid());

        userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    userphotourl = ds.child("image").getValue().toString();
                    username = ds.child("name").getValue().toString();
                    useremail = ds.child("email").getValue().toString();
                    userPoint = ds.child("point").getValue().toString();
                }
                Picasso.get().load(Home.userphotourl).resize(500 , 500).transform(new CircleTransform()).into(userprofile);
                userName.setText(Home.username);
                userEmail.setText(Home.useremail);
                userPoints.setText(Home.userPoint);

            }
//end get Data
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Achievements=findViewById(R.id.Achievements);
        recyclerevent=findViewById(R.id.recyclerEvent);
        recycler=findViewById(R.id.recyclerMarkett);
        //stores
        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setLayoutManager(scaleCenterItemManager);
        snapHelper.attachToRecyclerView(recycler);

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
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_iconmen);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);


        //events
     /*   eventslist.add(e);
        eventslist.add(e1);
        eventslist.add(e2);*/

        snapHelperevent = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerevent.setLayoutManager(new LinearLayoutManager(this));
        recyclerevent.setLayoutManager(scaleCenterItemManager);
        snapHelperevent.attachToRecyclerView(recyclerevent);
        //BottomNavigation
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
        //endNavBott
        //events


        com.example.bassametproject.ScaleCenterItemManager scaleCenterItemManager= new com.example.bassametproject.ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerevent.setLayoutManager(new LinearLayoutManager(this));
        recyclerevent.setLayoutManager(scaleCenterItemManager);
        snapHelperevent.attachToRecyclerView(recyclerevent);
        eventReference = FirebaseDatabase.getInstance().getReference().child("Events");
        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventslist = new ArrayList<eventList>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    eventList e = dataSnapshot1.getValue(eventList.class);
                    eventslist.add(e);
                }
                adapterEvent = new adapterEvent(eventslist,Home.this);
                recyclerevent.setAdapter(adapterEvent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        //Stores

        mref= FirebaseDatabase.getInstance().getReference("shops");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StoreItems=new ArrayList<>();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    StoreItem data=ds.getValue(StoreItem.class);
                    StoreItems.add(data);
                }
                myAdapt = new adapterShopsHome(StoreItems,Home.this);
                recycler.setAdapter(myAdapt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //INTENTS
        market=findViewById(R.id.marketbut);
        map=findViewById(R.id.mapbut);
        missions=findViewById(R.id.Achievements);
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

        Achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Home.this, Achievements.class);
                startActivity(intentLoadNewActivity);

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent HomeIntent = new Intent(Home.this, Home.class);
                startActivity(HomeIntent);
                break;
            case R.id.sign_scanner:
                Intent ScanIntent = new Intent(Home.this, Scan.class);
                startActivity(ScanIntent);
                break;
            case R.id.achivements:
                Intent AchIntent = new Intent(Home.this, Achievements.class);
                startActivity(AchIntent);
                break;
            case R.id.chatbot:
                Intent chatIntent = new Intent(Home.this, intro_chatbot.class);
                startActivity(chatIntent);
            case R.id.profile:
                Intent ProfileIntent = new Intent(Home.this, Profile.class);
                startActivity(ProfileIntent);
                break;
            case R.id.map:
                Intent MapIntent = new Intent(Home.this, MapBoxActivity.class);
                startActivity(MapIntent);
                break;
            case R.id.LeaderBoard:
                Intent LeaderBoardIntent = new Intent(Home.this, LeaderBoard.class);
                startActivity(LeaderBoardIntent);
                break;
            case R.id.logout:
                logout();
                Intent logoutIntent = new Intent(Home.this,LoginActivity.class);
                startActivity(logoutIntent);
                Toast.makeText(Home.this, "signed out successfully ! ", Toast.LENGTH_SHORT).show();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }
    private void logout() {
        fAuth.signOut();

    }
}