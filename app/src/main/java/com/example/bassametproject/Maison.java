package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Maison extends AppCompatActivity {


    List<ListProduct> accessoryList;
    RecyclerView recyclerAccessory;
    SnapHelper snapHelper;
    ScaleCenterItemManager scaleCenterItemManager;
    List<SlideModel> slideModels= new ArrayList<>();
    //adapter
    myAdapterrecy maisonAdapter;
    adapterAccessory adapterAccessory1;
    // onClick Item
    TextView shopName , shopDescription,openHour,storeLocation;
    String name , desc,hour,location;

    //firebase
    private FirebaseDatabase database;
    //video
    VideoView videoView;

    //end video declaration
    private DatabaseReference userRef, refRate ,refAccessory,refShop;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    public String username, userphotourl;
    //map dialogue
    ImageButton getdirection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maison);
 //Video
        MediaController mediaController=new MediaController(this);
 videoView=(VideoView) findViewById(R.id.videoView);
videoView.setMediaController(mediaController);
mediaController.setAnchorView(videoView);
Uri uri =Uri.parse("gs://pfe2021-270a5.appspot.com/Video/videoTest.mp4");
videoView.setVideoURI(uri);
videoView.start();

//end video
//imageSlider
        ImageSlider imageSlider = findViewById(R.id.image_slider1);
        slideModels.add(new SlideModel(R.drawable.mtaaslider, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mtaaslider, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);
        //onclick Item
        shopDescription=findViewById(R.id.shopDesc1);
        shopName=findViewById(R.id.shopName1);
        storeLocation=findViewById(R.id.storeLocation);
        openHour=findViewById(R.id.openingHour);

        getData();
        setData();



        //accessory
        recyclerAccessory=findViewById(R.id.moreaccessory1);
        //user firebase
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userRef = database.getInstance().getReference("User").child(user.getUid());

        // refRate = database.getInstance().getReference(adapterShops.theChosenOne).child(adapterShops.shopStatic.getStoreName());
        //   refShop = database.getInstance().getReference("shops").child("store1").child(adapterShops.shopStatic.getStoreName());
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


        //Firebase accessory
        refAccessory = FirebaseDatabase.getInstance().getReference(adapterShops.shops).child("store2").child("products");
        refAccessory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accessoryList = new ArrayList<>();
                for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                    Toast.makeText(Maison.this, data2.toString(), Toast.LENGTH_SHORT).show();

                    ListProduct p1 = data2.getValue(ListProduct.class);
                    accessoryList.add(p1);
                }
                adapterAccessory1 = new adapterAccessory(accessoryList,Maison.this);
                snapHelper = new LinearSnapHelper();
                scaleCenterItemManager = new ScaleCenterItemManager(Maison.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerAccessory.setLayoutManager(new LinearLayoutManager(Maison.this));
                recyclerAccessory.setLayoutManager(scaleCenterItemManager);
                recyclerAccessory.setAdapter(adapterAccessory1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Maison.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }





    //onClick
    private void getData(){
        if (getIntent().hasExtra("Home")){
            name=getIntent().getStringExtra("shopName");
        }
    }
    private void setData(){
/*shopName.setText(name);
shopDescription.setText(desc);*/
        if(adapterShopsHome.shop1.getStoreName()!=null){
            storeLocation.setText(adapterShopsHome.shop1.getStoreLocation());
            shopName.setText(adapterShopsHome.shop1.getStoreName());
            shopDescription.setText(adapterShopsHome.shop1.getStoreDescription());
            openHour.setText(adapterShopsHome.shop1.getOpeningHour());

        }else if(adapterShops.shop.getStoreName()!=null){
            storeLocation.setText(adapterShops.shop.getStoreLocation());
            shopName.setText(adapterShops.shop.getStoreName());
            shopDescription.setText(adapterShops.shop.getStoreDescription());
            openHour.setText(adapterShops.shop.getOpeningHour());
        }
    }
    //map dialog
    public void openMapDialog(View view)
    {
        finish();
        Intent intent = new Intent(Maison.this , MapBoxActivity.class);
        startActivity(intent);
    }

    public void RateInterface (View v){
        Intent intentLoadNewActivity = new Intent(Maison.this, Rating.class);
        startActivity(intentLoadNewActivity);
    }
   /* public void backInterface (View v){
      finish();
    }*/
}
