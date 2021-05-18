package com.example.bassametproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class Register extends AppCompatActivity {

    private Uri imageUri;


    EditText mFullName,mEmail,mPassword,mPassword2;
    ImageButton mRegisterBtn;
    FirebaseAuth fAuth;
    ProgressBar registerprogressbar;
    FirebaseDatabase database;
    DatabaseReference ref;
    User user;
    ImageView mRegisterPhoto;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private boolean resend = false;
    TextView counter;


    private String DownloadUrl;

    boolean changedPhoto = false;

    private String email , password , fullname , password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.nametxt);
        mEmail = findViewById(R.id.emailtxt);
        mPassword = findViewById(R.id.passtxt);
        mRegisterBtn = findViewById(R.id.menubutt);
        mPassword2 = findViewById(R.id.passtxt);
        mRegisterPhoto = findViewById(R.id.userphoto);

        counter = findViewById(R.id.counter);

        fAuth = FirebaseAuth.getInstance();
        registerprogressbar = findViewById(R.id.registerprogressbar);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        user = new User();
        ref = database.getInstance("https://pfe2021-270a5-default-rtdb.firebaseio.com/").getReference("User");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(resend == false)
                {

                    email  =  mEmail.getText().toString().trim();
                    password = mPassword.getText().toString().trim();
                    fullname = mFullName.getText().toString().trim();
                    password2 = mPassword2.getText().toString().trim();

                    if(TextUtils.isEmpty(email))
                    {
                        mEmail.setError("Email is Required.");
                        registerprogressbar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    if(TextUtils.isEmpty(password))
                    {
                        mPassword.setError("password is Required.");
                        registerprogressbar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    if(password.length() < 6)
                    {
                        mPassword.setError("Password Must be >=6 Characters");
                        registerprogressbar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    if(TextUtils.isEmpty(fullname))
                    {
                        mFullName.setError("Name is Required.");
                        registerprogressbar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    if(password.length() != password2.length())
                    {
                        mPassword2.setError("Password error !");
                        return;
                    }
                    else
                    {
                        for(int i = 0 ; i < password.length() ; i++)
                        {
                            if(password.charAt(i) != password2.charAt(i))
                            {
                                mPassword2.setError("Password error !");
                                return;
                            }
                        }
                    }


                    if(changedPhoto == false)
                    {
                        Toast.makeText(com.example.bassametproject.Register.this , "Image is Empty !" , Toast.LENGTH_SHORT).show();
                        return;
                    }



                    registerprogressbar.setVisibility(View.VISIBLE);



                    //register the user in firebase

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {

                                //send verification email

                                FirebaseUser fuser = fAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(com.example.bassametproject.Register.this , "Verification Email Has been Sent" , Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(com.example.bassametproject.Register.this , "Error Occured !" , Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //set counter start
                                counter.setVisibility(View.VISIBLE);

                                CountDownTimer countDownTimer;

                                mRegisterBtn.setEnabled(false);

                                countDownTimer = new CountDownTimer(60000 , 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        counter.setText(millisUntilFinished/1000 + " sec");
                                    }

                                    @Override
                                    public void onFinish()
                                    {
                                        resend = true;
                                        mRegisterBtn.setEnabled(true);
                                        counter.setText("");
                                        counter.setVisibility(View.INVISIBLE);

                                    }
                                };

                                countDownTimer.start();

                                //set counter end


                                Toast.makeText(com.example.bassametproject.Register.this,"User Created",Toast.LENGTH_SHORT).show();
                                uploadPicture();
                                Intent i = new Intent(Register.this, LoginActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(com.example.bassametproject.Register.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                registerprogressbar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });




                }
                else if(resend == true)
                {
                    FirebaseUser user = fAuth.getCurrentUser();
                    if(!user.isEmailVerified())
                    {
                        user = fAuth.getCurrentUser();
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                //set counter start
                                counter.setVisibility(View.VISIBLE);

                                CountDownTimer countDownTimer;

                                mRegisterBtn.setEnabled(false);

                                countDownTimer = new CountDownTimer(60000 , 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        counter.setText(millisUntilFinished/1000 + " sec");
                                    }

                                    @Override
                                    public void onFinish()
                                    {
                                        mRegisterBtn.setEnabled(true);
                                        counter.setText("");
                                        counter.setVisibility(View.INVISIBLE);
                                    }
                                };

                                countDownTimer.start();

                                //set counter end

                                Toast.makeText(com.example.bassametproject.Register.this , "Verification Email Has been Sent" , Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(com.example.bassametproject.Register.this , "Error !" , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }

        });

        mRegisterPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });


    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent , 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            mRegisterPhoto.setImageURI(imageUri);
            changedPhoto = true;


        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();
        pd.setCancelable(false);

        final String randomKey = UUID.randomUUID().toString();
        final StorageReference riversRef = storageReference.child("UsersImages/" + randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Get a URL to the uploaded content
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                pd.dismiss();
                                Toast.makeText(com.example.bassametproject.Register.this,"Image Uploaded",Toast.LENGTH_SHORT).show();

                                StorageReference filePath = storageReference.child("UsersImages/").child(imageUri.getLastPathSegment() + randomKey);

                                DownloadUrl = uri.toString();
                                addmember();
                                mRegisterBtn.setBackgroundResource(R.drawable.resendbtn);
                                registerprogressbar.setVisibility(View.INVISIBLE);
                                //startActivity(new Intent(getApplicationContext(),HomeMain.class));

                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(com.example.bassametproject.Register.this,"Image Failed Upload",Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage("Percentage : " + (int) progressPercent + " %");

            }
        });

    }


    public void addmember()
    {

        user.setName(mFullName.getText().toString());
        user.setEmail(mEmail.getText().toString());
        user.setId(fAuth.getUid());
        user.setImage(DownloadUrl);
        user.setPoint(0);

        ref.child(fAuth.getUid()).child("User").setValue(user);

    }


}
