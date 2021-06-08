package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener authStateListener;

    private ImageView userprofile;
    private TextView userName , userEmail , userPoints;
    User U=new User();
    public static String username , userphotourl  , useremail ,userPoint;

    private FirebaseDatabase database;
    private DatabaseReference userRef2;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userprofile = findViewById(R.id.userImg);
        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userMail);
        userPoints=findViewById(R.id.userPoint);
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
                Picasso.get().load(Home.userphotourl).resize(50 , 50).into(userprofile);
                userName.setText(Home.username);
                userEmail.setText(Home.useremail);
                userPoints.setText(Home.userPoint);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }}