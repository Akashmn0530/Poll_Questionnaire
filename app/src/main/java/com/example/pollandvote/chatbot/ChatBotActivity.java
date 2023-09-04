package com.example.pollandvote.chatbot;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pollandvote.Admin.bottom_nav.BottomNavigation;
import com.example.pollandvote.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

public class ChatBotActivity extends AppCompatActivity  {

    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private final String BOT_KEY = "bot";
    BottomNavigationView bottomNavigationView;

    // creating a variable for array list and adapter class.
    private ArrayList<Message> messageModalArrayList;
    private MessageRVAdapter messageRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbat);

        chatsRV = findViewById(R.id.idRVChats);
        ImageView sendMsgIB = findViewById(R.id.idIBSend);
        userMsgEdt = findViewById(R.id.idEdtMessage);

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.getCache().clear();

        // creating a new array list
        messageModalArrayList = new ArrayList<>();

        // Bottom layout
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        BottomNavigation.bottomNavProvider(bottomNavigationView,getApplicationContext());

        // adding on click listener for send message button.
        sendMsgIB.setOnClickListener(v -> {
            if (userMsgEdt.getText().toString().isEmpty()) {
                // if the edit text is empty display a toast message.
                Toast.makeText(ChatBotActivity.this, "Please enter your message..", Toast.LENGTH_SHORT).show();
                return;
            }

            sendMessage(userMsgEdt.getText().toString());

            // below line we are setting text in our edit text as empty
            userMsgEdt.setText("");
        });


        messageRVAdapter = new MessageRVAdapter(messageModalArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        chatsRV.setLayoutManager(linearLayoutManager);
        chatsRV.setAdapter(messageRVAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage(String userMsg) {

        String USER_KEY = "user";
        messageModalArrayList.add(new Message(userMsg, USER_KEY));
        messageRVAdapter.notifyDataSetChanged();

        String url = "http://api.brainshop.ai/get?bid=177425&key=Oww6veYGfHYpSlxg&uid=[uid]&msg=[msg]" + userMsg;

        // creating a variable for our request queue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // on below line we are making a json object request for a get request and passing our url .
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                String botResponse = response.getString("cnt");
                messageModalArrayList.add(new Message(botResponse, BOT_KEY));
                chatsRV.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> chatsRV.postDelayed(() -> chatsRV.smoothScrollToPosition(
                        Objects.requireNonNull(chatsRV.getAdapter()).getItemCount() - 1), 100));
                // notifying our adapter as data changed.
                messageRVAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();

                // handling error response from bot.
                messageModalArrayList.add(new Message("No response", BOT_KEY));
                messageRVAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            // error handling.
            messageModalArrayList.add(new Message(error.getMessage(), BOT_KEY));
            Toast.makeText(ChatBotActivity.this, "No response from the bot..",Toast.LENGTH_SHORT).show();
        });

        queue.add(jsonObjectRequest);
    }
}