package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

/*public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
   /* DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
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
                Intent HomeIntent = new Intent(DrawerActivity.this, Home.class);
                startActivity(HomeIntent);
                break;
            case R.id.sign_scanner:
                Intent ScanIntent = new Intent(DrawerActivity.this, Scan.class);
                startActivity(ScanIntent);
                break;
            case R.id.achivements:
                Intent AchIntent = new Intent(DrawerActivity.this, Achievements.class);
                startActivity(AchIntent);
                break;
            case R.id.chatbot:
                Intent chatIntent = new Intent(DrawerActivity.this, chatbotActivity.class);
                startActivity(chatIntent);
            case R.id.profile:
                Intent ProfileIntent = new Intent(DrawerActivity.this, Profile.class);
                startActivity(ProfileIntent);
                break;
            case R.id.map:
                Intent MapIntent = new Intent(DrawerActivity.this, MapBoxActivity.class);
                startActivity(MapIntent);
                break;
            case R.id.LeaderBoard:
                Intent LeaderBoardIntent = new Intent(DrawerActivity.this, LeaderBoard.class);
                startActivity(LeaderBoardIntent);
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}*/