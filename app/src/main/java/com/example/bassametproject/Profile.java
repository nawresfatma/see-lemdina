package com.example.bassametproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener authStateListener;

    ImageView profileimage;
    User U=new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileimage = findViewById(R.id.imgprofile);

    }}