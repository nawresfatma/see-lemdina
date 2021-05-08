package com.example.bassametproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MissionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<missionList> listMissions= new ArrayList<>();
    TextView checkStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

      checkStore=findViewById(R.id.checkStore);


        recyclerView=findViewById(R.id.missionRecycler);
        listMissions.add(new missionList("1000 lila ou lila ","we are more than happy",R.drawable.man,"DISCOVER"));
        listMissions.add(new missionList("torbet el bey","we are more than happy",R.drawable.cube,"DISCOVER"));
        listMissions.add(new missionList("dar ben gacem ","we are more than happy",R.drawable.man,"DISCOVER"));

        missionAdapter adapter =new missionAdapter(listMissions, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        checkStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(MissionsActivity.this, Store.class);
                startActivity(intentLoadNewActivity);

            }
        });

    }
}