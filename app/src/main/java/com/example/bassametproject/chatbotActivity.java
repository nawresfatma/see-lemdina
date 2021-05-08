package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class chatbotActivity extends AppCompatActivity implements ai.api.AIListener {
    /*Static Variables*/
    public static int HOW_MANY_MESSAGES = 0;
    public static int SIZE = 0;

    /* Firebase References */
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;
    DatabaseReference dbRef;

    /* Project Variables */
    EditText message;
    ImageButton send_btn;
    ImageButton mic_btn;
    RecyclerView messages_recycler;
    ImageView imageView;
    String botMessage;
    String time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    final ArrayList<Message> messages = new ArrayList<>();
    final MessageAdapter messageAdapter = new MessageAdapter();
    String messageKey;
    CircularDotsLoader progressBar;
    TextView tip;
    /* DialogFlow */
    AIService aiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        dbRef = fDatabase.getReference().child("Chatbot");
        message = findViewById(R.id.message_input);
        send_btn = findViewById(R.id.send_btn);
        mic_btn = findViewById(R.id.btn_micro);
        progressBar = findViewById(R.id.progressBar);
        tip = findViewById(R.id.tip);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO
            }, 101);
        }


        final AIConfiguration config = new AIConfiguration("6a1e3c3ea493c2a7215b1553ed30e9ad90de6eb3",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        final AIDataService aiDataService = new AIDataService(config);
        final AIRequest aiRequest = new AIRequest();
        aiService.setListener((ai.api.AIListener) this);

        messages_recycler = findViewById(R.id.messages_recycler);
        messages_recycler.setLayoutManager(new LinearLayoutManager(this));


          dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                HOW_MANY_MESSAGES = (int) snapshot.child(fAuth.getCurrentUser().getUid()).getChildrenCount();
                if (HOW_MANY_MESSAGES == 0) {
                    tip.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    tip.setVisibility(View.GONE);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.getKey().equals(fAuth.getCurrentUser().getUid())) {
                            for (DataSnapshot messages_firebase : ds.getChildren()) {
                                messages.add(new userMsg((String) messages_firebase.child("message").getValue(), new User((String) messages_firebase.child("sender").child("name").getValue(), LoadActivity.userphotourl), time));
                            }
                            messageAdapter.setMessages(messages);
                            progressBar.setVisibility(View.GONE);
                            messages_recycler.setAdapter(messageAdapter);
                            messages_recycler.scrollToPosition(HOW_MANY_MESSAGES);
                        }
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(chatbotActivity.this,R.anim.fadein);
                send_btn.startAnimation(animation);
                final String user_message = message.getText().toString().trim();
                if (user_message.length() <= 0 || user_message == null) {
                    Toast.makeText(chatbotActivity.this,"You have to type something !",Toast.LENGTH_SHORT).show();
                } else {
                    messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    addUserMessages(messageKey + "0", user_message);
                    aiRequest.setQuery(user_message);
                    new AsyncTask<AIRequest, Void, AIResponse>() {
                        @Override
                        protected AIResponse doInBackground(AIRequest... requests) {
                            final AIRequest request = requests[0];
                            try {
                                final AIResponse response = aiDataService.request(aiRequest);
                                return response;
                            } catch (AIServiceException e) {
                            }
                            return null;
                        }

                        @SuppressLint("StaticFieldLeak")
                        @Override
                        protected void onPostExecute(AIResponse aiResponse) {
                            if (aiResponse != null || !aiResponse.getResult().getFulfillment().getSpeech().isEmpty()) {
                                messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                                addBotMessages(messageKey + "1", aiResponse.getResult().getFulfillment().getSpeech());
                            } else {
                                messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                                addBotMessages(messageKey + "1", "Sorry, i didn't get that");
                            }
                        }
                    }.execute(aiRequest);
                }
            }
        });
        mic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(chatbotActivity.this, R.anim.fadein);
                mic_btn.startAnimation(animation);
                aiService.startListening();
            }
        });

    }

    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();
        messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        addUserMessages(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()) + "0", result.getResolvedQuery());
        if (result.getFulfillment().getSpeech() != null) {
            messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            addBotMessages(messageKey + "1", result.getFulfillment().getSpeech());
        } else {
            Toast.makeText(chatbotActivity.this, "Respond error", Toast.LENGTH_SHORT).show();
            messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            addBotMessages(messageKey + "1", "Sorry, i didn't get that");
        }
    }

    @Override
    public void onError(AIError error) {
        Toast.makeText(this, "Listening error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {
        Toast.makeText(this, "Listening started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListeningCanceled() {
        Toast.makeText(this, "Listening canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListeningFinished() {
        Toast.makeText(this, "Listening finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void addUserMessages(String messageKey, String msgUser) {
        User user = new User(LoadActivity.username, LoadActivity.userphotourl);
        userMsg userMsg = new userMsg(msgUser, user, time);
        dbRef.child(fAuth.getUid()).child(messageKey).setValue(userMsg);
        message.setText("");
        messages_recycler.scrollToPosition(HOW_MANY_MESSAGES);
    }

    public void addBotMessages(final String messageKey, final String msgBot) {
        User bot = new User("Bot", LoadActivity.userphotourl);
        botMsg botMsg = new botMsg(msgBot, bot, time);
        dbRef.child(fAuth.getUid()).child(messageKey).setValue(botMsg);
        messages_recycler.scrollToPosition(HOW_MANY_MESSAGES);
    }

}