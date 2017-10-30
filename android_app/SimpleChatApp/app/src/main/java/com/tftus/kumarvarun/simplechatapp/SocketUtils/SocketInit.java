package com.tftus.kumarvarun.simplechatapp.SocketUtils;

import android.text.TextUtils;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by kumar.varun on 10/25/2017.
 */

public class SocketInit {

    private Socket mSocket;
    public SocketInit(String server) {
        {
            try {
                mSocket = IO.socket(server);
            } catch (URISyntaxException e) {}
        }
    }

    public void emitEvent(String event,String message) {
        mSocket.emit(event, message);
    }

    public Socket getmSocket() {
        return mSocket;
    }
}
