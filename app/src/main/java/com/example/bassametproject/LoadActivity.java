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

public class LoadActivity extends AppCompatActivity {
        ///////Home page controle wowéh //////////
        RecyclerView recycler3;
        private SnapHelper snapHelper;




        DrawerLayout drawerLayout;
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
                toolbar= findViewById(R.id.imageView11);
                //////////////////TOOLBAR//////////
                setSupportActionBar(toolbar);
                ///////////////////////////////////////////// Navigation /:://///////////////////
                ////////// Hide Or Show items
                /////////////Menu menu=navigationView.getMenu();/////////////::



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
      }