package com.tftus.kumarvarun.simplechatapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tftus.kumarvarun.simplechatapp.ChatWindow;
import com.tftus.kumarvarun.simplechatapp.Model.ChatMessage;
import com.tftus.kumarvarun.simplechatapp.R;

import java.util.List;

import static com.tftus.kumarvarun.simplechatapp.ChatWindow.chatMessages;

/**
 * Created by varun on 11/11/17.
 */

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessgeViewHolder> {
    private List<ChatMessage> mChatMessages;

    public ChatMessageAdapter() {
        mChatMessages = chatMessages;
    }
    public void setList(){
        mChatMessages = chatMessages;
        notifyDataSetChanged();
    }


    @Override
    public ChatMessgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.chat_message,parent, false);
        return new ChatMessgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatMessgeViewHolder holder, int position) {
        ChatMessage message = mChatMessages.get(position);
        holder.username.setText(message.getUsername());
        holder.message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return mChatMessages.size();
    }

    public class ChatMessgeViewHolder extends RecyclerView.ViewHolder{
        public TextView username, message;

        public ChatMessgeViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.tv_message_username);
            message = (TextView) itemView.findViewById(R.id.tv_message_message);
        }
    }
}
