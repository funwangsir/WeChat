package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.wechat.LoginActivity;
import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

public class Setting extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sqLiteHelper = new SQLiteHelper(this);

        //退出登录
        RelativeLayout loginOut = (RelativeLayout)findViewById(R.id.logout);
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginout = new Intent(Setting.this, LoginActivity.class);

                User user = sqLiteHelper.selectLoginIn();
                //修改登录状态
                sqLiteHelper.userLoginOut(user.getUserId());

                //退出登录，要将当前的useriId传递过去，便于在登录框显示用户id
                toLoginout.putExtra("useridOrphone",user.getUserPhone());
                startActivity(toLoginout);
            }
        });

        ImageButton settingback = (ImageButton)findViewById(R.id.setting_back);
        settingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
