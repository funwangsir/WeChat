package com.wechat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;
import com.wechat.otherlayout.Setting;

//碎片4
public class Fragment4 extends Fragment {

    private SQLiteHelper sqLiteHelper;



    private Button loginOut;

    private MainActivity mainActivity;

    private User users;//用户信息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_4,container,false);

        mainActivity = (MainActivity)getActivity();//获取指定活动对象

        sqLiteHelper = new SQLiteHelper(mainActivity);

        users = mainActivity.getUserInfo();//获取当前登录的用户信息

        //显示当前登录的userid以及其他信息
        TextView nickname = (TextView)view.findViewById(R.id.fragment4_user_info_nickname);
        TextView userid = (TextView)view.findViewById(R.id.fragment4_user_info_userid);
        nickname.setText(users.getNickname());
        userid.setText(users.getUserId());

        RelativeLayout setting = (RelativeLayout)view.findViewById(R.id.fragment4_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(mainActivity, Setting.class);
                startActivity(setting);
            }
        });


        return view;
    }
}
