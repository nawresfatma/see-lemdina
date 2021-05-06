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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoadActivity extends AppCompatActivity {
        ///////Home page controle wowéh //////////
        RecyclerView recycler3;
        private SnapHelper snapHelper;
       //firebase
        private DatabaseReference userRef2;
        private FirebaseAuth fAuth;
        private FirebaseUser user;

        public static String username , userphotourl , useremail;



        DrawerLayout drawerLayout;
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_load);
                recycler3=findViewById(R.id.recyclerView);
                fAuth = FirebaseAuth.getInstance();

                user = fAuth.getCurrentUser();

                userRef2 = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());

                userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                        userphotourl = ds.child("photoURL").getValue().toString();
                                        username = ds.child("name").getValue().toString();
                                        useremail = ds.child("email").getValue().toString();
                                }

                                finish();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });

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