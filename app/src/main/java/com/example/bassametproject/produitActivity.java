package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class produitActivity extends AppCompatActivity {
    List<ListProduct> accessoryList;
    RecyclerView recyclerAccessory1;
    SnapHelper snapHelper;
    ScaleCenterItemManager scaleCenterItemManager;
    List<SlideModel> slideModels = new ArrayList<>();
    //adapter
    myAdapterrecy maisonAdapter;
    adapterAccessory adapterAccessory1;
    // onClick Item
    TextView shopName, productDescription , storeLocation,prodName;
    String name, desc, hour, location;
    RecyclerView recyclerViewRating;
    ArrayList<RatingComment> listRating;

    //firebase
    private FirebaseDatabase database;
    private DatabaseReference refRating;
    ratingAdapter adapterRating;
    //video
    VideoView videoView;
    ImageView play_button;

    //end video declaration
    private DatabaseReference userRef, refRate, refAccessory, refShop;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    public String username, userphotourl;
    //map dialogue
    ImageButton getdirection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit);

        //imageSlider
        ImageSlider imageSlider = findViewById(R.id.image_slider1);
        slideModels.add(new SlideModel(R.drawable.mtaaslider, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.mtaaslider, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);

        productDescription = findViewById(R.id.prodDesc1);
        shopName = findViewById(R.id.shopName);
        prodName=findViewById(R.id.product);
        storeLocation = findViewById(R.id.storeLocation);
        recyclerAccessory1=findViewById(R.id.moreaccessory12);
        getData();
        setData();

        //user firebase
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        userRef = database.getInstance().getReference("User").child(user.getUid());

        BottomNavigationView navView = findViewById(R.id.navView);
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

        refAccessory = FirebaseDatabase.getInstance().getReference("shops").child(adapterAccessory.productStatic.getId()).child("products");
        refAccessory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                accessoryList = new ArrayList<>();
                for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                    Toast.makeText(produitActivity.this, data2.toString(), Toast.LENGTH_SHORT).show();

                    ListProduct p1 = data2.getValue(ListProduct.class);
                    accessoryList.add(p1);
                }
                adapterAccessory1 = new adapterAccessory(accessoryList, produitActivity.this);
                snapHelper = new LinearSnapHelper();
                scaleCenterItemManager = new ScaleCenterItemManager(produitActivity.this, LinearLayoutManager.HORIZONTAL, false);

                recyclerAccessory1.setLayoutManager(new LinearLayoutManager(produitActivity.this));
                recyclerAccessory1.setLayoutManager(scaleCenterItemManager);
                recyclerAccessory1.setAdapter(adapterAccessory1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(produitActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        refRating = FirebaseDatabase.getInstance().getReference("shops").child(adapterAccessory.productStatic.getId()).child("RatingComment");

        recyclerViewRating = (RecyclerView) findViewById(R.id.ratingrecycler);

        recyclerViewRating.setLayoutManager(new LinearLayoutManager(this));


        listRating = new ArrayList<RatingComment>();

        refRating.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    RatingComment r = dataSnapshot1.getValue(RatingComment.class);
                    listRating.add(r);

                }

                adapterRating = new ratingAdapter(produitActivity.this, listRating);
                recyclerViewRating.setAdapter(adapterRating);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(produitActivity.this, " something is wrong !", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //onClick
    private void getData() {
        if (getIntent().hasExtra("Home")) {
            name = getIntent().getStringExtra("shopName");
        }
    }

    private void setData() {
/*shopName.setText(name);
shopDescription.setText(desc);*/

            storeLocation.setText(adapterAccessory.productStatic.getProdPrice());
            shopName.setText(adapterAccessory.productStatic.getProdName());
            productDescription.setText(adapterAccessory.productStatic.getProdDescription());
    }

}