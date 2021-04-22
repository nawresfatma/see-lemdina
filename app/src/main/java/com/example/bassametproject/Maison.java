package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.denzcoskun.imageslider.constants.ScaleTypes;

import java.util.ArrayList;
import java.util.List;

public class Maison extends AppCompatActivity {

    List<ListProduct> accessoryList;
    RecyclerView maisonRecycler,recyclerAccessory;
    SnapHelper snapHelper;
    ScaleCenterItemManager scaleCenterItemManager;
    List<ListProduct> listMaisons= new ArrayList<>();
    List<SlideModel> slideModels= new ArrayList<>();
    //adapter
    myAdapterrecy maisonAdapter;
    adapterAccessory adapterAccessory;
    //firebase
    DatabaseReference maisonsref,refAccessory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maison);
        maisonRecycler=findViewById(R.id.moreaccessory);

        snapHelper = new LinearSnapHelper();
        scaleCenterItemManager = new ScaleCenterItemManager(this, LinearLayoutManager.HORIZONTAL, false);
        maisonRecycler.setLayoutManager(new LinearLayoutManager(this));
        maisonRecycler.setLayoutManager(scaleCenterItemManager);

        //videoView
        VideoView videoView=findViewById(R.id.videoView);
        String videoPath="android.resource://"+getPackageName() + "/"+R.raw.video;
        Uri uri= Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        MediaController mediaController=new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        maisonsref= FirebaseDatabase.getInstance().getReference().child("shops").child("store1").child("products");
        maisonsref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Toast.makeText(Maison.this, ds.toString(), Toast.LENGTH_SHORT).show();
                    ListProduct data=ds.getValue(ListProduct.class);

                    listMaisons.add(data);

                }
                maisonAdapter =new myAdapterrecy(listMaisons,Maison.this);
                maisonRecycler.setAdapter(maisonAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            } });


        ImageSlider imageSlider = findViewById(R.id.image_slider1);

        slideModels.add(new SlideModel(R.drawable.balghaslider, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

        //accessory
        recyclerAccessory=findViewById(R.id.moreaccessory);
        //Firebase accessory
        refAccessory = FirebaseDatabase.getInstance().getReference().child("shops").child("store1").child("products");
        refAccessory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accessoryList = new ArrayList<>();
                for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                    ListProduct p1 = data2.getValue(ListProduct.class);

                    accessoryList.add(p1);
                }
                adapterAccessory = new adapterAccessory(accessoryList,Maison.this);

                recyclerAccessory.setLayoutManager(new GridLayoutManager(Maison.this,2));

                recyclerAccessory.setAdapter(adapterAccessory);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Maison.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void RateInterface (View v){
        Intent intentLoadNewActivity = new Intent(Maison.this, Rating.class);
        startActivity(intentLoadNewActivity);
    }
    public void ScanInterface (View v){
        Intent intentLoadNewActivity = new Intent(Maison.this, Scan.class);
        startActivity(intentLoadNewActivity);
    }
}