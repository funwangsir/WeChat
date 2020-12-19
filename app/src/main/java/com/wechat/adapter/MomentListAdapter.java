package com.wechat.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wechat.R;
import com.wechat.entity.User;
import com.wechat.otherlayout.UserInfo;

import java.util.List;

public class MomentListAdapter extends RecyclerView.Adapter<MomentListAdapter.ViewHolder> {

    private List<MomentList> mMomentList;

    private Context context;

    static class ViewHolder extends  RecyclerView.ViewHolder{
        View momentsListView;
        ImageView headImg;
        TextView userName;
        TextView momentsContent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            momentsListView = itemView;
            headImg = (ImageView) itemView.findViewById(R.id.moments_headimg);
            userName = (TextView)itemView.findViewById(R.id.moments_username);
            momentsContent = (TextView)itemView.findViewById(R.id.moments_content);
        }
    }
    public MomentListAdapter(List<MomentList> momentslist,Context context){
        mMomentList = momentslist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moments_list,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.momentsListView.setOnClickListener(new View.OnClickListener() {//点击整个元素监听事件
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                MomentList m = mMomentList.get(position);//获取对象数据
                //传递user对象跳转到个人信息页面
                User user = m.getUser();
                Intent intent = new Intent(context, UserInfo.class);
                intent.putExtra("userInfo",user);
                context.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MomentList momentList = mMomentList.get((position));
        holder.headImg.setImageResource(momentList.getImgId());
        holder.userName.setText(momentList.getUser().getName());
        holder.momentsContent.setText(momentList.getMoments().getTextContent());
    }

    @Override
    public int getItemCount() {
        return mMomentList.size();
    }

}
