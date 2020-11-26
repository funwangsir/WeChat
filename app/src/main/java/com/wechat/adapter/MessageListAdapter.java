package com.wechat.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wechat.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private List<MessageList> mMsgList;

    private String loginUserId;//当前登录用户的id，也就是发送消息的用户id


    static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout sendLayout;//发消息的布局

        RelativeLayout receiveLayout;//收消息的布局

        TextView sendMsg;//发送内容

        TextView receiveMsg;//接收内容

        public ViewHolder(View view) {
            super(view);
            sendLayout = (RelativeLayout) view.findViewById(R.id.send_msg_layout);
            receiveLayout = (RelativeLayout) view.findViewById(R.id.receive_msg_layout);
            sendMsg = (TextView) view.findViewById(R.id.send_msg_content);
            receiveMsg = (TextView) view.findViewById(R.id.receive_msg_content);
        }
    }

    public MessageListAdapter(List<MessageList> msgList,String loginUserId) {
        mMsgList = msgList;
        this.loginUserId = loginUserId;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageList ms = mMsgList.get(position);//获取RecyclerView中的当前的对象
        //发送该条消息的人为当前登录用户，就显示右边消息，反之则显示在左边
        if(ms.getMessage().getSendUserId().equals(loginUserId)){
            holder.sendLayout.setVisibility(View.VISIBLE);//显示
            holder.receiveLayout.setVisibility(View.GONE);//隐藏
            holder.sendMsg.setText(ms.getMessage().getTextMessage());//设置文本内容
        }else{
            holder.receiveLayout.setVisibility(View.VISIBLE);
            holder.sendLayout.setVisibility(View.GONE);
            holder.receiveMsg.setText(ms.getMessage().getTextMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}

