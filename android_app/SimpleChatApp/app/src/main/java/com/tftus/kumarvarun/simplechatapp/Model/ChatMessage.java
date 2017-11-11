package com.tftus.kumarvarun.simplechatapp.Model;

/**
 * Created by varun on 11/11/17.
 */

public class ChatMessage {
    private String username;
    private String message;

    public ChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
