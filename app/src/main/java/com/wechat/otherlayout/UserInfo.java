package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

import org.w3c.dom.Text;

public class UserInfo extends AppCompatActivity {

    private User user;//搜索的用户对象
    private SQLiteHelper sqLiteHelper;

    private TextView nickName;//昵称
    private TextView area;//地区
    private TextView introduction;//个性签名
    private ImageButton back;//返回

    private LinearLayout addFriend;// 发送消息 / 添加到通讯录的LinearLayout
    private TextView userSearchOption;// 发送消息 / 添加到通讯录的文字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        sqLiteHelper = new SQLiteHelper(this);

        //获取前一个活动传递过来的User对象
        user  = (User)getIntent().getSerializableExtra("userInfo");

        addFriend = (LinearLayout)findViewById(R.id.add_friend);
        userSearchOption = (TextView)findViewById(R.id.user_search_option);

        seachtInfoAndFill(user);//查询数据并填充
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userSearchOption.getText().equals("添加到通讯录")){
                    String userid = sqLiteHelper.selectLoginIn().getUserId();
                    addFriend(userid,user.getUserId());
                    Toast t = Toast.makeText(UserInfo.this,null,Toast.LENGTH_LONG);
                    t.setText("添加好友成功");
                    t.show();
                    userSearchOption.setText("发送消息");
                }else if(userSearchOption.getText().equals("发送消息")){
                    //暂未实现
                }
            }
        });

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

        String loginUserId = sqLiteHelper.selectLoginIn().getUserId();
        String friendId = user.getUserId();

        //是朋友关系就不显示“添加到通讯录”，而是显示“发送消息”
        //搜索自己也显示“发送消息”
        if(loginUserId.equals(friendId) || sqLiteHelper.isFriend(loginUserId,friendId)){
            userSearchOption.setText("发送消息");
        }else{
            userSearchOption.setText("添加到通讯录");
        }
    }


    public void addFriend(String userId,String friendId){
        sqLiteHelper.addFriend(userId,friendId);
    }
}
