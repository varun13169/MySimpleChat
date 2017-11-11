package com.tftus.kumarvarun.simplechatapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tftus.kumarvarun.simplechatapp.R;

import java.util.List;

/**
 * Created by varun on 12/11/17.
 */

public class OnlineUsersAdapter extends RecyclerView.Adapter<OnlineUsersAdapter.OnlineUserViewHolder> {
    private List<String> mOnlineUsers;

    public OnlineUsersAdapter(List<String> mOnlineUsers) {
        this.mOnlineUsers = mOnlineUsers;
    }

    @Override
    public OnlineUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_user , parent ,false);
        return new OnlineUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OnlineUserViewHolder holder, int position) {
        holder.onlineUser.setText(mOnlineUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mOnlineUsers.size();
    }

    public void setList(List<String> mOnlineUsers) {
        this.mOnlineUsers = mOnlineUsers;
        notifyDataSetChanged();
    }

    public class OnlineUserViewHolder extends RecyclerView.ViewHolder{
        public TextView onlineUser;
        public OnlineUserViewHolder(View itemView) {
            super(itemView);
            onlineUser = (TextView) itemView.findViewById(R.id.tv_online_user);
        }
    }
}
