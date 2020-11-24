package com.wechat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wechat.LoginActivity;
import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

//碎片4
public class Fragment4 extends Fragment {

    private SQLiteHelper sqLiteHelper;

    private TextView accountInfo;//账号信息

    private Button loginOut;

    private MainActivity mainActivity;

    private User users;//用户信息

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_4,container,false);

        mainActivity = (MainActivity)getActivity();//获取指定活动对象

        sqLiteHelper = new SQLiteHelper(mainActivity);


        //显示当前登录的userid以及其他信息
        accountInfo = (TextView)view.findViewById(R.id.account_info);
        users = mainActivity.getUserInfo();
        accountInfo.setText(users.getUserId()+","+users.getName()+","+users.getNickname());


        //退出登录
        loginOut = (Button)view.findViewById(R.id.login_out);
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginout = new Intent(mainActivity, LoginActivity.class);

                //修改登录状态
                sqLiteHelper.userLoginOut(users.getUserId());

                //退出登录，要将当前的useriId传递过去，便于在登录框显示用户id
                toLoginout.putExtra("userId",users.getUserId());
                startActivity(toLoginout);
            }
        });
        return view;
    }
}
