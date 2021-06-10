package com.example.bassametproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class chatbotActivity extends AppCompatActivity {
    private static final String TAG = chatbotActivity.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    /*Static Variables*/
    public static int HOW_MANY_MESSAGES=0;
    public static int SIZE = 0;

    /* Firebase References */
    FirebaseAuth fAuth;
    FirebaseDatabase fDatabase;
    DatabaseReference dbRef;

    /* Project Variables */
    EditText messageEdit;
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
    // Java V2
    private SessionsClient sessionsClient;
    private SessionName session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        fAuth = FirebaseAuth.getInstance();
        fDatabase = FirebaseDatabase.getInstance();
        dbRef = fDatabase.getReference().child("Chatbot");
        messageEdit = findViewById(R.id.message_input);
        send_btn = findViewById(R.id.send_btn);
        send_btn.setOnClickListener(this::sendMessage);
        mic_btn = findViewById(R.id.btn_micro);
        progressBar = findViewById(R.id.progressBar);
        tip = findViewById(R.id.tip);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO
            }, 101);
        }

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

        mic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(chatbotActivity.this, R.anim.fadein);
                mic_btn.startAnimation(animation);
            }
        });
        /**initiate bot**/
        initV2Chatbot();
       messageEdit.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        sendMessage(send_btn);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });


        /**send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(chatbotActivity.this,R.anim.fadein);
                send_btn.startAnimation(animation);
                final String user_message = messageEdit.getText().toString().trim();
                if (user_message.length() <= 0 || user_message == null) {
                    Toast.makeText(chatbotActivity.this,"You have to type something !",Toast.LENGTH_SHORT).show();
                } else {
                    messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                 //   addUserMessages(messageKey + "0", user_message);

                }
            }
        });**/

    }

    private void initV2Chatbot() {
        try {
         InputStream stream = getResources().openRawResource(R.raw.test_agent_credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
            String projectId = ((ServiceAccountCredentials)credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
          SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            session = SessionName.of(projectId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(View view) {
        String msg = messageEdit.getText().toString();
        if (msg.trim().isEmpty()) {
            Toast.makeText(chatbotActivity.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {


            messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            addUserMessages(messageKey + "0", msg);
            // Java V2
            QueryInput queryInput = QueryInput.newBuilder().setText(TextInput.newBuilder().setText(msg).setLanguageCode("en-US")).build();
            new RequestJavaV2Task(chatbotActivity.this, session, sessionsClient, queryInput).execute();
        }
    }

    public void callbackV2(DetectIntentResponse response) {
        if (response != null) {
            // process aiResponse here
            String botReply = response.getQueryResult().getFulfillmentText();
            Log.d(TAG, "V2 Bot Reply: " + botReply);
            messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            addBotMessages(messageKey + "1", botReply);

        } else {
            Toast.makeText(this, "Respond error", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Bot Reply: Null");
           // showTextView("There was some communication issue. Please Try again!", BOT);
            messageKey = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
            addBotMessages(messageKey + "1", "Sorry, i didn't get that");
        }
    }

  /*  private void showTextView(String message, int type) {
       RelativeLayout layout;
        switch (type) {
            case USER:
                layout = addUserMessages();
                break;
            case BOT:
                layout = addUserMessages();
                break;
            default:
                layout = addBotMessages();
                break;
        }
        layout.setFocusableInTouchMode(true);
       // chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.bot_message);
        tv.setText(message);
        layout.requestFocus();
        messageEdit.requestFocus(); // change focus back to edit text to continue typing
    }*/

    public void addUserMessages(String messageKey, String msgUser) {
        User user = new User(LoadActivity.username, LoadActivity.userphotourl);
        userMsg userMsg = new userMsg(msgUser, user, time);
        dbRef.child(fAuth.getUid()).child(messageKey).setValue(userMsg);
        messageEdit.setText("");
        messages_recycler.scrollToPosition(HOW_MANY_MESSAGES);}

        public void addBotMessages(final String messageKey, final String msgBot) {
            User bot = new User("Bot", LoadActivity.userphotourl);
            botMsg botMsg = new botMsg(msgBot, bot, time);
            dbRef.child(fAuth.getUid()).child(messageKey).setValue(botMsg);
            messages_recycler.scrollToPosition(HOW_MANY_MESSAGES);
        }
}