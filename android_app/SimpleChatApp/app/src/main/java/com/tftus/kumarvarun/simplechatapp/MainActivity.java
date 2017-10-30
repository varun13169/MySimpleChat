package com.tftus.kumarvarun.simplechatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.Ack;
import com.tftus.kumarvarun.simplechatapp.SocketUtils.SocketInit;

public class MainActivity extends AppCompatActivity {

    private static SocketInit soc;
    private Button mLogin;
    private EditText mUsername;
    private Intent intent;

    public static SocketInit getSoc() {
        return soc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin = (Button) findViewById(R.id.btn_login);
        mUsername = (EditText) findViewById(R.id.ev_username);

        soc = new SocketInit("http://192.168.108.182:3000");
        soc.getmSocket().connect();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, ChatWindow.class);
                intent.putExtra("USER", mUsername.getText().toString());
                //soc.emitEvent("new user",mUsername.getText().toString());
                //soc = new SocketInit("http://192.168.108.182:3000");
                soc.getmSocket().emit("new user", mUsername.getText().toString(), new Ack() {
                    @Override
                    public void call(Object... args) {
                        boolean x = (boolean)args[0];
                        if(x){
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }


}
