package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Rating extends AppCompatActivity {

    private ImageView ReviewImageuser;
    private EditText reviewTitle, reviewDescription;
    private RatingBar dialograting_bar;
    private LottieAnimationView submitBtn;
    private DatabaseReference refRate , refStore;
    private FirebaseDatabase database;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private float RateSum;
    private int RateCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ReviewImageuser = findViewById(R.id.ReviewImageuser);
        reviewTitle = findViewById(R.id.reviewTitle);
        reviewDescription = findViewById(R.id.reviewDescription);
        dialograting_bar = findViewById(R.id.dialograting_bar);
        submitBtn = findViewById(R.id.animationView);

        if (adapterShopsHome.shop1.getId() != null) {

            refRate = database.getInstance().getReference(adapterShopsHome.shops).child(adapterShopsHome.shop1.getId()).child("RatingComment");
            refStore = database.getInstance().getReference(adapterShopsHome.shops).child(adapterShopsHome.shop1.getId());

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final RatingComment rate = new RatingComment();

                    rate.setDescription(reviewDescription.getText().toString());
                    rate.setTitle(reviewTitle.getText().toString());
                    rate.setStoreRate(dialograting_bar.getRating());
                    rate.setImage(Home.userphotourl);
                    rate.setName(Home.username);

                    fAuth = FirebaseAuth.getInstance();

                    user = fAuth.getCurrentUser();

                    refRate.child(user.getUid()).setValue(rate);

                    submitBtn.playAnimation();
                    refRate.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                            for (DataSnapshot ds : DataSnapshot.getChildren()) {
                                RateSum += Float.valueOf(ds.child("storeRate").getValue().toString());
                                RateCount++;
                            }

                            RateSum /= RateCount;

                            HashMap hashMap = new HashMap();

                            hashMap.put("storeRate", RateSum);

                            refStore.updateChildren(hashMap);

                            finish();
                            //adapterShops.shops.
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });


        } else if (adapterShops.shop.getId() != null) {
            refRate = database.getInstance().getReference(adapterShops.shops).child(adapterShops.shop.getId()).child("RatingComment");
            refStore = database.getInstance().getReference(adapterShops.shops).child(adapterShops.shop.getId());

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final RatingComment rate = new RatingComment();

                    rate.setDescription(reviewDescription.getText().toString());
                    rate.setTitle(reviewTitle.getText().toString());
                    rate.setStoreRate(dialograting_bar.getRating());
                    rate.setImage(Home.userphotourl);
                    rate.setName(Home.username);

                    fAuth = FirebaseAuth.getInstance();

                    user = fAuth.getCurrentUser();

                    refRate.child(user.getUid()).setValue(rate);


                    refRate.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                            for (DataSnapshot ds : DataSnapshot.getChildren()) {
                                RateSum += Float.valueOf(ds.child("storeRate").getValue().toString());
                                RateCount++;
                            }

                            RateSum /= RateCount;

                            HashMap hashMap = new HashMap();

                            hashMap.put("storeRate", RateSum);

                            refStore.updateChildren(hashMap);

                            finish();
                            //adapterShops.shops.
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });


        }

    }




}