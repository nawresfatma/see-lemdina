package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class LoadActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
        ///////Home page controle wowéh //////////
        RecyclerView recycler3;
        private SnapHelper snapHelper;




        DrawerLayout drawerLayout;
        NavigationView navigationView;
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_load);
                recycler3=findViewById(R.id.recyclerView);


                //////////////////////////*myAdapter myAdapt=new myAdapter(eventList1,this);
                //////////////////////////* recycler3.setAdapter(myAdapt);
                //////////////////////////*snapHelper = new LinearSnapHelper();
                //////////////////////////* ScaleCenterItemManager scaleCenterItemManager= new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL,false);
                //////////////////////////* recycler3.setLayoutManager(new LinearLayoutManager(this));
                //////////////////////////*recycler3.setLayoutManager(scaleCenterItemManager);
                //////////////////////////*snapHelper.attachToRecyclerView(recycler3);
                //////////HooooKs/////////////////////////
                drawerLayout=findViewById(R.id.drawer_layout);
                navigationView=findViewById(R.id.nav_view);
                toolbar= findViewById(R.id.imageView11);
                //////////////////TOOLBAR//////////
                setSupportActionBar(toolbar);
                ///////////////////////////////////////////// Navigation /:://///////////////////
                ////////// Hide Or Show items
                /////////////Menu menu=navigationView.getMenu();/////////////::



                navigationView.bringToFront();
                ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
                drawerLayout.addDrawerListener(toggle);
                toggle.syncState();
                navigationView.setNavigationItemSelectedListener(this);
                navigationView.setCheckedItem(R.id.nav_home);


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
         * @param item The selected item
         * @return true to display the item as the selected item
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                        case R.id.nav_home:
                                break;
                        case R.id.sign_scanner:
                                Intent ScanIntent = new Intent(LoadActivity.this,Scan.class);
                                startActivity(ScanIntent);
                                break;
                        case R.id.achivements:
                                Toast.makeText(this ,"Share",Toast.LENGTH_SHORT).show();
                                break;
                        case R.id.profile:
                                Intent ProfileIntent = new Intent(LoadActivity.this,Profile.class);
                                startActivity(ProfileIntent);
                                break;
                        case R.id.map:
                                Intent MapIntent = new Intent(LoadActivity.this,MapActivity.class);
                                startActivity(MapIntent);
                                break;
                        case R.id.LeaderBoard:
                                Intent LeaderBoardIntent = new Intent(LoadActivity.this,LeaderBoard.class);
                                startActivity(LeaderBoardIntent);
                                break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
}