 package com.example.bassametproject;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.android.material.navigation.NavigationView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

public class MissionsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //drawer declarations
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private FirebaseDatabase database;
    private DatabaseReference userRef2;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private ImageView userprofile,test;
    private TextView userName , userEmail , userPoints;
    public static String username , userphotourl  , useremail ,userPoint;
    //end Drawer declarations

    RecyclerView recyclerView;

    ArrayList<missionList> listMissions= new ArrayList<>();
    TextView checkStore,checkMission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(" ");
        setContentView(R.layout.activity_missions);
        checkStore=findViewById(R.id.checkStore);
        checkMission=findViewById(R.id.checkMission);

//getdata
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_menu);
        View headerView = navigationView.getHeaderView(0);
        userprofile = headerView.findViewById(R.id.userprofile);
        userName = headerView.findViewById(R.id.textView8);
        userEmail = headerView.findViewById(R.id.textView28);
        userPoints = headerView.findViewById(R.id.textView25);

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
                Picasso.get().load(Home.userphotourl).resize(500 , 500).transform(new CircleTransform()).into(userprofile);
                userName.setText(Home.username);
                userEmail.setText(Home.useremail);
                userPoints.setText(Home.userPoint);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//Hooks
        drawerLayout=findViewById(R.id.container);
        navigationView=findViewById(R.id.nav_menu);
        toolbar=findViewById(R.id.menubut);
//toolbar
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_iconmen);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);
        recyclerView=findViewById(R.id.missionRecycler);
        listMissions.add(new missionList("The Legend ","Go to BEB BHAR to start the first mission",R.drawable.man,"DISCOVER"));
        listMissions.add(new missionList("Second Mission","To be added soon",R.drawable.cube,"DISCOVER"));
        listMissions.add(new missionList("Third Mission ","To be added soon",R.drawable.man,"DISCOVER"));

        missionAdapter adapter =new missionAdapter(listMissions, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        checkMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(getPackageManager().getLaunchIntentForPackage("com.DefaultCompany.Missions"));


                if(launchIntent != null){


                    startActivity(launchIntent);
                }

                }
        });

    checkStore.setOnClickListener(new View.OnClickListener() {
           @Override
        public void onClick(View v) {
         Intent intentLoadNewActivity = new Intent(MissionsActivity.this, Store.class);
             startActivity(intentLoadNewActivity);  }  });



        BottomNavigationView navView=findViewById(R.id.bookNow);
        navView.setItemIconTintList(null);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.getItemId();
                Intent launchIntent = new Intent(getPackageManager().getLaunchIntentForPackage("com.DefaultCompany.VufioraTshirtTemplate"));


                if(launchIntent != null){


                    startActivity(launchIntent);
                }


                return true;
            }

        });

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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent HomeIntent = new Intent(MissionsActivity.this, Home.class);
                startActivity(HomeIntent);
                break;
            case R.id.sign_scanner:
                Intent ScanIntent = new Intent(MissionsActivity.this, Scan.class);
                startActivity(ScanIntent);
                break;
            case R.id.achivements:
                Intent AchIntent = new Intent(MissionsActivity.this, Achievements.class);
                startActivity(AchIntent);
                break;
            case R.id.chatbot:
                Intent chatIntent = new Intent(MissionsActivity.this, chatbotActivity.class);
                startActivity(chatIntent);
                break;

            case R.id.profile:
                Intent ProfileIntent = new Intent(MissionsActivity.this, Profile.class);
                startActivity(ProfileIntent);
                break;
            case R.id.map:
                Intent MapIntent = new Intent(MissionsActivity.this, MapBoxActivity.class);
                startActivity(MapIntent);
                break;
            case R.id.LeaderBoard:
                Intent LeaderBoardIntent = new Intent(MissionsActivity.this, Ranking.class);
                startActivity(LeaderBoardIntent);
                break;
            case R.id.logout:
                logout();
                Intent logoutIntent = new Intent(MissionsActivity.this,LoginActivity.class);
                startActivity(logoutIntent);
                Toast.makeText(MissionsActivity.this, "signed out successfully ! ", Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    private void logout() {
        fAuth.signOut();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
