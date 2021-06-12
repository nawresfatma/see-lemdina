package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    //drawer declarations
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private FirebaseDatabase database;
    private DatabaseReference userRef2;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private ImageView userprofile,navUserprofile;
    private TextView userName , userEmail , userPoints;
    private TextView navUserName , navUserEmail , navUserPoints;
    public static String username , userphotourl  , useremail ,userPoint;
    //end Drawer declarations
    User U=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(" ");
        userprofile = findViewById(R.id.userImg);
        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userMail);
        userPoints=findViewById(R.id.userPoint);
        fAuth = FirebaseAuth.getInstance();

//getData
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_menu);
        View headerView = navigationView.getHeaderView(0);
        navUserprofile = headerView.findViewById(R.id.userprofile);
        navUserName = headerView.findViewById(R.id.textView8);
        navUserEmail = headerView.findViewById(R.id.textView28);
        navUserPoints = headerView.findViewById(R.id.textView25);

        Picasso.get().load(Home.userphotourl).resize(500 , 500).transform(new CircleTransform()).into(navUserprofile);
        navUserName.setText(Home.username);
        navUserEmail.setText(Home.useremail);
        navUserPoints.setText(Home.userPoint);

        //profile
        Picasso.get().load(Home.userphotourl).resize(500 , 500).transform(new CircleTransform()).into(userprofile);
        userName.setText(Home.username);
        userEmail.setText(Home.useremail);
        userPoints.setText(Home.userPoint);

        user = fAuth.getCurrentUser();

        userRef2 = database.getInstance().getReference("User").child(user.getUid());
//
//        userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    userphotourl = ds.child("image").getValue().toString();
//                    username = ds.child("name").getValue().toString();
//                    useremail = ds.child("email").getValue().toString();
//                    userPoint = ds.child("point").getValue().toString();
//                }
//                Picasso.get().load(Home.userphotourl).resize(500 , 500).transform(new CircleTransform()).into(userprofile);
//                userName.setText(Home.username);
//                userEmail.setText(Home.useremail);
//                userPoints.setText(Home.userPoint);
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent HomeIntent = new Intent(Profile.this, Home.class);
                startActivity(HomeIntent);
                break;
            case R.id.sign_scanner:
                Intent ScanIntent = new Intent(Profile.this, Scan.class);
                startActivity(ScanIntent);
                break;
            case R.id.achivements:
                Intent AchIntent = new Intent(Profile.this, Achievements.class);
                startActivity(AchIntent);
                break;
            case R.id.chatbot:
                Intent chatIntent = new Intent(Profile.this, intro_chatbot.class);
                startActivity(chatIntent);
                break;

            case R.id.profile:
                Intent ProfileIntent = new Intent(Profile.this, Profile.class);
                startActivity(ProfileIntent);
                break;
            case R.id.map:
                Intent MapIntent = new Intent(Profile.this, MapBoxActivity.class);
                startActivity(MapIntent);
                break;
            case R.id.LeaderBoard:
                Intent LeaderBoardIntent = new Intent(Profile.this, Ranking.class);
                startActivity(LeaderBoardIntent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}