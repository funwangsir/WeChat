package com.wechat.adapter;

import android.content.ContentValues;
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
        ImageView optionMoments;//点赞、评论

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            momentsListView = itemView;
            headImg = (ImageView) itemView.findViewById(R.id.moments_headimg);
            userName = (TextView)itemView.findViewById(R.id.moments_username);
            momentsContent = (TextView)itemView.findViewById(R.id.moments_content);
            optionMoments = (ImageView)itemView.findViewById(R.id.option_comments);
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
        //点击整个元素监听事件
        viewHolder.momentsListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //点赞、评论悬浮框
        viewHolder.optionMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //点击头像跳转到个人信息页
        viewHolder.headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                MomentList m = mMomentList.get(position);//获取对象数据
                //传递user对象跳转到个人信息页面
                Intent intent = new Intent(context, UserInfo.class);
                intent.putExtra("userId",m.getUser().getUserId());
                context.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MomentList momentList = mMomentList.get((position));
        byte[] img = momentList.getUser().getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray( img,0, img.length);
        holder.headImg.setImageBitmap(bitmap);
        holder.userName.setText(momentList.getUser().getName());
        holder.momentsContent.setText(momentList.getMoments().getTextContent());
    }

    @Override
    public int getItemCount() {
        return mMomentList.size();
    }

}
