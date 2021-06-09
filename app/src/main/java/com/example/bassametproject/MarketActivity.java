package com.example.bassametproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import static com.example.bassametproject.CardAdapter.CardList;

public class MarketActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    public static String username , userphotourl  , useremail ,userPoint;
    private FirebaseDatabase database;
    private DatabaseReference userRef2;
    private ImageView userprofile;
    private TextView userName , userEmail , userPoints;

    RecyclerView recyclerSouk;
    ArrayList<soukList> Mylist;
    CardAdapter Soukadapter;

    //firebase
    DatabaseReference soukRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        recyclerSouk = findViewById(R.id.recyclerView);

        recyclerSouk.setLayoutManager(new LinearLayoutManager(this));
        //navbott
        BottomNavigationView navView = findViewById(R.id.navView);
        navView.setItemIconTintList(null);
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//Hooks
        drawerLayout = findViewById(R.id.container);
        navigationView = findViewById(R.id.nav_menu);
        toolbar = findViewById(R.id.menubut);
//toolbar
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_iconmen);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);
//Firebase(Stores)
        soukRef = FirebaseDatabase.getInstance().getReference("Souk");
        soukRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Mylist = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    soukList data = ds.getValue(soukList.class);
                    Mylist.add(data);

                }
                Soukadapter = new CardAdapter(Mylist, MarketActivity.this);
                recyclerSouk.setAdapter(Soukadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.getItemId();
                startActivity(new Intent(getApplicationContext(), Scan.class));
                overridePendingTransition(0, 0);
                return true;
            }

        });

        //search
        EditText filterEdit = findViewById(R.id.filterSouk);
        filterEdit.addTextChangedListener(new TextWatcher() {
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
        ArrayList<soukList> filteredList = new ArrayList<>();

        for (soukList item : CardList) {
            if (item.getSoukName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        Soukadapter.filterList(filteredList);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent HomeIntent = new Intent(MarketActivity.this, Home.class);
                startActivity(HomeIntent);
                break;
            case R.id.sign_scanner:
                Intent ScanIntent = new Intent(MarketActivity.this, Scan.class);
                startActivity(ScanIntent);
                break;
            case R.id.achivements:
                Intent AchIntent = new Intent(MarketActivity.this, Achievements.class);
                startActivity(AchIntent);
                break;
            case R.id.chatbot:
                Intent chatIntent = new Intent(MarketActivity.this, chatbotActivity.class);
                startActivity(chatIntent);
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


    }



    /*public void MaisonInterface (View v){
        Intent intentLoadNewActivity = new Intent(MarketActivity.this, Maison.class);
        startActivity(intentLoadNewActivity);
    }
*/




}

