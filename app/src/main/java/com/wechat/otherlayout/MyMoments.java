package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wechat.R;
import com.wechat.adapter.MomentList;
import com.wechat.adapter.MomentListAdapter;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.Moments;
import com.wechat.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MyMoments extends AppCompatActivity {
    private SQLiteHelper sqLiteHelper;
    private User user;
    private Moments moments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymoments);
        sqLiteHelper = new SQLiteHelper(this);
        init();//加载朋友圈信息

        //点击发布朋友圈
        toPublish();

        //点击返回
        ImageButton back = (ImageButton)findViewById(R.id.moments_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init(){
        user = sqLiteHelper.selectLoginIn();
        moments = null;
        //显示当前用户的头像和用户名
        TextView nickname = (TextView)findViewById(R.id.moments_my_nickname);
        ImageView headimg = (ImageView)findViewById(R.id.moments_my_headimg);
        nickname.setText(user.getNickname());
        byte[] img = user.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        headimg.setImageBitmap(bitmap);

        //加载朋友圈信息
        List<Moments> momentsList = sqLiteHelper.getMoments(sqLiteHelper.selectLoginIn());

        List<MomentList> mMomentList = new ArrayList<>();//容器适配器，用于加载到RecyclerView
        for (Moments m:momentsList) {//将查询出来的数据添加到容器中
            User u = sqLiteHelper.getUserInfo(m.getUserId());//从Moments表中的userid找到user对象
            mMomentList.add(new MomentList(u, m));
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.moments_list);//绑定RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        MomentListAdapter adapter = new MomentListAdapter(mMomentList,this);
        recyclerView.setAdapter(adapter);

    }

    public void toPublish(){
        ImageButton sendMoments = (ImageButton)findViewById(R.id.send_moments);
        sendMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send = new Intent(MyMoments.this,SendMoments.class);
                startActivity(send);
            }
        });
    }
}
