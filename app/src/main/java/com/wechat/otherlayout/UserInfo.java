package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

import org.w3c.dom.Text;

public class UserInfo extends AppCompatActivity {

    private User user;
    private SQLiteHelper sqLiteHelper;

    private TextView nickName;
    private TextView area;
    private TextView introduction;


    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        user  = (User)getIntent().getSerializableExtra("userInfo");
        seachtInfoAndFill(user);//查询数据并填充


        back = (ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//销毁当前活动
            }
        });
    }

    public void seachtInfoAndFill(User user){
        nickName = (TextView)findViewById(R.id.user_info_nickName);
        area = (TextView)findViewById(R.id.user_info_area);
        introduction = (TextView)findViewById(R.id.user_info_introduction);

        nickName.setText(user.getNickname());
        area.setText(user.getArea());
        introduction.setText(user.getIntroduction());

    }
}
