package com.wechat.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;
import com.wechat.otherlayout.MyInfo;
import com.wechat.otherlayout.Setting;

//碎片4
public class Fragment4 extends Fragment {

    private SQLiteHelper sqLiteHelper;

    private MainActivity mainActivity;

    private User users;//用户信息

    private TextView nickname;
    private TextView userid;

    private ImageView headimg;
    private byte[] img;
    private Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_4,container,false);

        mainActivity = (MainActivity)getActivity();//获取指定活动对象

        sqLiteHelper = new SQLiteHelper(mainActivity);

        users = sqLiteHelper.selectLoginIn();//获取当前登录的用户信息

        //显示当前登录的userid以及其他信息
        nickname= (TextView)view.findViewById(R.id.fragment4_user_info_nickname);
        userid = (TextView)view.findViewById(R.id.fragment4_user_info_userid);
        headimg= (ImageView)view.findViewById(R.id.fragment4_headimg);
        nickname.setText(users.getNickname());
        userid.setText(users.getUserId());

        //设置头像，将二进制转换为bitmap
        img = users.getAvatar();
        bitmap = BitmapFactory.decodeByteArray(img,0,img.length);

        headimg.setImageBitmap(bitmap);

        //跳转到个人信息页
        LinearLayout myInfo = (LinearLayout)view.findViewById(R.id.goto_my_info);
        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, MyInfo.class);
                startActivity(intent);
            }
        });

        //跳转到设置页面
        RelativeLayout setting = (RelativeLayout)view.findViewById(R.id.fragment4_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, Setting.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        //设置头像，将二进制转换为bitmap
        img = sqLiteHelper.selectLoginIn().getAvatar();
        bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        headimg.setImageBitmap(bitmap);
    }
}
