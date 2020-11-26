package com.wechat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wechat.R;
import com.wechat.entity.User;
import com.wechat.fragments.Fragment2;
import com.wechat.otherlayout.UserInfo;

import java.util.*;

//好友列表适配器，适配好友信息和头像图片
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private List<FriendList> mFriendList;

    private Context context;//当前的活动对象

    static class ViewHolder extends RecyclerView.ViewHolder{
        View friendListView;
        ImageView headImg;
        TextView friendName;

        public ViewHolder(View view){
            super(view);
            friendListView = view;
            headImg = (ImageView)view.findViewById(R.id.head_img);
            friendName = (TextView)view.findViewById(R.id.friend_name);
        }
    }

    public FriendListAdapter(List<FriendList> friendLists,Context context){
        mFriendList = friendLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.friendListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                FriendList f = mFriendList.get(position);//获取对象数据
                //传递user对象跳转到个人信息页面
                User user = f.getUser();

                Intent intent = new Intent(context, UserInfo.class);
                intent.putExtra("userInfo",user);
                context.startActivity(intent);
            }
        });
        viewHolder.headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                FriendList f = mFriendList.get(position);//获取对象数据
                //传递user对象跳转到个人信息页面
                User user = f.getUser();

                Intent intent = new Intent(context, UserInfo.class);
                intent.putExtra("userInfo",user);
                context.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FriendList friendList = mFriendList.get(position);
        holder.headImg.setImageResource(friendList.getImageId());
        holder.friendName.setText(friendList.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
