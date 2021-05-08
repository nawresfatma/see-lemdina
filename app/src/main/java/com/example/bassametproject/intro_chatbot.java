package com.example.bassametproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class intro_chatbot extends AppCompatActivity {
    private TextView intentText;
    private ImageButton btnImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_chatbot);
intentText=findViewById(R.id.btn_txt);
btnImage=findViewById(R.id.imageButtonchatbot);
        intentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(intro_chatbot.this, chatbotActivity.class);
                startActivity(intentLoadNewActivity);

            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(intro_chatbot.this, chatbotActivity.class);
                startActivity(intentLoadNewActivity);

            }
        });

    }
}