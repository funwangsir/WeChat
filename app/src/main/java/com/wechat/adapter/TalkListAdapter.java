package com.wechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wechat.R;
import com.wechat.entity.User;
import com.wechat.otherlayout.Talk;

import java.util.List;

//主界面聊天列表
public class TalkListAdapter extends RecyclerView.Adapter<TalkListAdapter.ViewHolder> {

    private List<TalkList> mTalkList;

    private String loginUserId;//当前登录用户的id，也就是发送消息的用户id

    private Context context;//当前的活动对象

    static class ViewHolder extends RecyclerView.ViewHolder {

        View TalkListView;//用于点击事件

        ImageView headImg;

        TextView userName;

        TextView newMessage;


        public ViewHolder(View view) {
            super(view);
            TalkListView = view;
            headImg = (ImageView) view.findViewById(R.id.talk_head_img);
            userName = (TextView) view.findViewById(R.id.talk_list_username);
            newMessage = (TextView) view.findViewById(R.id.talk_list_lastmessage);
        }
    }

    public TalkListAdapter(List<TalkList> taskList,Context context) {
        mTalkList = taskList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talk_list,parent,false);

        final ViewHolder viewHolder = new ViewHolder(view);
        //监听每个聊天记录的点击事件
        viewHolder.TalkListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                TalkList t = mTalkList.get(position);//获取当前元素的TalkList对象
                User user = t.getUser();//取得其中的User对象集合

                Intent intent = new Intent(context, Talk.class);//跳转到聊天界面
                intent.putExtra("receiveUserId",user.getUserId());
                context.startActivity(intent);


            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TalkList tl = mTalkList.get(position);//获取RecyclerView中的当前的对象
        byte[] img = tl.getUser().getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        holder.headImg.setImageBitmap(bitmap);
        holder.userName.setText(tl.getUser().getName());
        String newMessage = tl.getNewMessage().getTextMessage();
        if(newMessage.length() > 10) newMessage = newMessage.substring(0,10)+"...";
        holder.newMessage.setText(newMessage);
    }

    @Override
    public int getItemCount() {
        return mTalkList.size();
    }
}