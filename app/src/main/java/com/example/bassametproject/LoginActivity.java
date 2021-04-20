package com.example.bassametproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    //facebook start
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private TextView textViewUser;
    private ImageView mLogo;
    private LoginButton loginButton;
    private static final String TAG = "FacebookAuthentification";
    private  FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;

    //facebook end

    //google start

    private Button signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button btnSignOut;
    private int RC_SIGN_IN = 1;
    private TextView googletxt;
    private ImageView googleImg;

    //google end

    //Email and pass Login Start

    EditText mEmail,mPassword;
    ImageButton mLoginBtn;
    TextView mforgetpass;
    LinearLayout mCreateBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    //Email And Pass Login END

    User userr;
    DatabaseReference ref;
    FirebaseDatabase database;

    FirebaseUser usere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Email And Pass Login START


        mEmail = findViewById(R.id.emailtxt);
        mPassword = findViewById(R.id.passwordtxt);
        mLoginBtn = findViewById(R.id.imageButton);
        mCreateBtn = findViewById(R.id.signupbtn);
        mforgetpass = findViewById(R.id.forget);


        fAuth = FirebaseAuth.getInstance();
        usere = fAuth.getCurrentUser();
        progressBar = findViewById(R.id.progress_bar_login);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String email =  mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                progressBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("password is Required.");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                if(password.length() < 6)
                {
                    mPassword.setError("Password Must be >=6 Characters");
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(com.example.bassametproject.LoginActivity.this, Home.class);
                            intent.putExtra("email",email);
                            Toast.makeText(com.example.bassametproject.LoginActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                        else
                        {
                            Toast.makeText(com.example.bassametproject.LoginActivity.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        //start go to register
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.bassametproject.Register.class));
            }
        });

        mforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your E-mail To Recived Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //extract the email and send the reset link
                        progressBar.setVisibility(View.VISIBLE);
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(com.example.bassametproject.LoginActivity.this,"Reset Link Send To Your E-mail",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(com.example.bassametproject.LoginActivity.this,"Error ! Reset Link is Not Send " +e.getMessage(),Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //close the dialog

                    }
                });
                passwordResetDialog.create().show();
            }
        });


        if(fAuth.getCurrentUser() != null)
        {


            for (UserInfo user: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                if (user.getProviderId().equals("password"))
                {

                    if(!usere.isEmailVerified())
                    {
                        //send verification email

                        usere.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(com.example.bassametproject.LoginActivity.this , "Account Not Verified !\nVerification Email Has been resended" , Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(com.example.bassametproject.LoginActivity.this , "Error !" , Toast.LENGTH_SHORT).show();
                            }
                        });

                        FirebaseAuth.getInstance().signOut();
                    }
                    else
                    {
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                    }

                }
                else if((user.getProviderId().equals("facebook.com") || user.getProviderId().equals("google.com")))
                {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                }
            }

        }






//facebook start

        userr = new User();
        database = FirebaseDatabase.getInstance("https://basamet-6876b-default-rtdb.firebaseio.com/");
        ref = database.getReference().child("User");

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());

        loginButton = findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions("email" , "public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError");
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    //updateUI(user);
                    for (UserInfo usera: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                        if (usera.getProviderId().equals("facebook.com"))
                        {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            overridePendingTransition(0, 0);
                        }
                    }

                }
                else
                {
                    //updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    mFirebaseAuth.signOut();
                }
            }
        };
        //facebook end



        //google start

        signInButton = findViewById(R.id.googlesignInButton);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this , gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

/*        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(Login.this,"You are Logged Out" , Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.GONE);
            }
        });*/


        //google end



    }


    //google start

    private  void SignIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent , RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(com.example.bassametproject.LoginActivity.this , "Signed In Successfully !" , Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);


        }catch (ApiException e)
        {
            Toast.makeText(com.example.bassametproject.LoginActivity.this , "Signed In Failed !" , Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }

    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct)
    {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken() , null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(com.example.bassametproject
                            .LoginActivity.this , "Successful" , Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    //updateUIGoogle(user);
                    addmembergoogle(user);


                    Intent intent = new Intent(com.example.bassametproject.LoginActivity.this, Home.class);
                    startActivity(new Intent(getApplicationContext(), Home.class));
                }
                else
                {
                    Toast.makeText(com.example.bassametproject.LoginActivity.this , "Failed !" , Toast.LENGTH_SHORT).show();
                    //updateUIGoogle(null);
                }
            }
        });
    }

/*    private void updateUIGoogle(FirebaseUser fuser)
    {
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null)
        {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Picasso.get().load(personPhoto).into(googleImg);
            googletxt.setText(personName + " " + personGivenName + " " + personFamilyName + " " + personEmail + " " + personId);

        }
    }*/


    //google end




    //facebook start
    private  void handleFacebookToken(AccessToken token)
    {
        Log.d(TAG , "handleFacebookToken" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG , "sign in with credential : successful");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    //if he is successful login what will hapen in this case we will put the img and his name
                    addmemberfacebook(user);

                    //updateUI(user);
                }
                else
                {
                    Log.d(TAG , "sign in with credential : failure" , task.getException());
                    Toast.makeText(com.example.bassametproject.LoginActivity.this,"Authenfication Failed" , Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //facebook start

        mCallbackManager.onActivityResult(requestCode,resultCode,data);

        //facebook end
        super.onActivityResult(requestCode, resultCode, data);
        //google start

        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }


        //google end
    }

    public void addmemberfacebook(final FirebaseUser user)
    {

        DatabaseReference userRef;

        userRef = database.getInstance("https://basamet-6876b-default-rtdb.firebaseio.com/").getReference("User");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = "";

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    id = String.valueOf(ds.getKey());

                    if(id.compareTo(user.getUid()) == 0)
                    {
                        return;
                    }

                }

                System.out.println(user.getEmail() + "1");


                userr.setName(user.getDisplayName());
                userr.setEmail(user.getEmail());
                userr.setId(user.getUid());
                String photoUrl = user.getPhotoUrl() + "?type=large";
                userr.setImage(photoUrl);

                System.out.println(userr.getEmail() + "2");

                ref.child(fAuth.getUid()).child("user").setValue(userr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void addmembergoogle(final FirebaseUser user)
    {

        DatabaseReference userRef;

        userRef = database.getInstance("https://basamet-6876b-default-rtdb.firebaseio.com/").getReference("User");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String id = "";

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    id = String.valueOf(ds.getKey());

                    if(id.compareTo(user.getUid()) == 0)
                    {
                        return;
                    }

                }

                userr.setName(user.getDisplayName());
                userr.setEmail(user.getEmail());
                userr.setImage(user.getPhotoUrl().toString());
                userr.setId(user.getUid());

                ref.child(fAuth.getUid()).child("user").setValue(userr);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

/*    private void updateUI(FirebaseUser user)
    {
        if(user != null)
        {
            textViewUser.setText(user.getDisplayName());
            if (user.getPhotoUrl() != null)
            {
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=large";
                Picasso.get().load(photoUrl).into(mLogo);
            }
        }
        else
        {
            textViewUser.setText("error");
            mLogo.setImageResource(R.drawable.aaa);
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null)
        {
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
    //facebook end
    private void setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }



    //go to register


}