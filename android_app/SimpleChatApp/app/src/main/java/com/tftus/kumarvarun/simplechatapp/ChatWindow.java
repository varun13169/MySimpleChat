package com.tftus.kumarvarun.simplechatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;
import java.util.EventListener;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class ChatWindow extends AppCompatActivity implements View.OnClickListener {
    TextView mUsername;
    ImageButton mSend;
    EditText mMessage;
    Socket mSocket;
    private String mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        mUser = getIntent().getStringExtra("USER");

        mUsername = (TextView) findViewById(R.id.tv_username);
        mSend = (ImageButton) findViewById(R.id.imbtn_send);
        mMessage = (EditText) findViewById(R.id.ev_message);

        mUsername.setText(mUser);
        mSocket = MainActivity.getSoc().getmSocket();
        mSend.setOnClickListener(this);

        mSocket.on("new message", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String newMessage = (String) args[0];
                Toast.makeText(ChatWindow.this, newMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.imbtn_send){
            mSocket.emit("send messssage", mMessage.getText().toString(), mUser);
            mMessage.setText("");
        }

    }
}
