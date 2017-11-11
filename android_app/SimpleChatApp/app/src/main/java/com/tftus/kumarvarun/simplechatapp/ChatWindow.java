package com.tftus.kumarvarun.simplechatapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.tftus.kumarvarun.simplechatapp.Adapter.ChatMessageAdapter;
import com.tftus.kumarvarun.simplechatapp.Adapter.OnlineUsersAdapter;
import com.tftus.kumarvarun.simplechatapp.Model.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatWindow extends AppCompatActivity implements View.OnClickListener {
    TextView mUsername;
    ImageButton mSend;
    EditText mMessage;
    Socket mSocket;
    ActionBar mActionbar;

    private static String mUser;

    private RecyclerView mRecyclerView, mOnlineUsersRecyclerView;
    private ChatMessageAdapter mChatMessageAdapter;
    private OnlineUsersAdapter mOnlineUsersAdapter;
    public static List<ChatMessage> chatMessages = new ArrayList<>();
    public static List<String> mOnlineUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        mUser = getIntent().getStringExtra("USER");

//        mUsername = (TextView) findViewById(R.id.tv_username);
        mSend = (ImageButton) findViewById(R.id.imbtn_send);
        mMessage = (EditText) findViewById(R.id.ev_message);
        mActionbar = getSupportActionBar();

        mActionbar.setTitle(mUser);
//        mUsername.setText(mUser);
        mSocket = MainActivity.getSoc().getmSocket();
        mSend.setOnClickListener(this);

        mChatMessageAdapter = new ChatMessageAdapter();
        mOnlineUsersAdapter = new OnlineUsersAdapter(mOnlineUsers);

        RecyclerView.LayoutManager mLayoutManagerChatMessages = new LinearLayoutManager(getApplicationContext());
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_chat_messages);
        mRecyclerView.setLayoutManager(mLayoutManagerChatMessages);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mChatMessageAdapter);

        RecyclerView.LayoutManager mLayoutManagerOnlineUsers = new LinearLayoutManager(getApplicationContext());
        mOnlineUsersRecyclerView = (RecyclerView) findViewById(R.id.rv_online_users);
        mOnlineUsersRecyclerView.setLayoutManager(mLayoutManagerOnlineUsers);
        mOnlineUsersRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mOnlineUsersRecyclerView.setAdapter(mOnlineUsersAdapter);

        setupEventListeners();
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.imbtn_send){
            mSocket.emit("send message", mMessage.getText().toString());
            mMessage.setText("");
        }

    }

    public static String getmUser() {
        return mUser;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSocket.disconnect();
        mSocket.off("new message");
        finish();
    }

    private void setupEventListeners(){
        mSocket.on("new message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject newMessage = (JSONObject) args[0];
                try {
                    String username = newMessage.getString("user");
                    String message = newMessage.getString("msg");
                    chatMessages.add(new ChatMessage(username, message));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatMessageAdapter.setList();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mSocket.on("get users", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray onlineUsersJson = (JSONArray) args[0];
                //mOnlineUsers.clear();
                for (int i = 0; i < onlineUsersJson.length(); i++) {
                    try {
                        mOnlineUsers.add(onlineUsersJson.getString(i) );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mOnlineUsersAdapter.setList(mOnlineUsers);
                    }
                });
            }
        });
    }
}
