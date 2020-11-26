package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private TextView name;//名称
    private TextView nickName;//昵称
    private TextView wechatid;//微信号
    private TextView area;//地区
    private TextView introduction;//个性签名
    private ImageButton back;//返回

    private LinearLayout addFriendOrSendMsg;// 发送消息 / 添加到通讯录的LinearLayout
    private TextView userOptions;// 发送消息 / 添加到通讯录的文字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        sqLiteHelper = new SQLiteHelper(this);

        //获取前一个活动传递过来的User对象
        user  = (User)getIntent().getSerializableExtra("userInfo");

        addFriendOrSendMsg = (LinearLayout)findViewById(R.id.add_friend);
        userOptions = (TextView)findViewById(R.id.user_options);

        seachtInfoAndFill(user);//查询数据并填充
        //点击按钮
        addFriendOrSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userOptions.getText().equals("添加到通讯录")){
                    String userid = sqLiteHelper.selectLoginIn().getUserId();
                    addFriend(userid,user.getUserId());
                    Toast t = Toast.makeText(UserInfo.this,null,Toast.LENGTH_LONG);
                    t.setText("添加好友成功");
                    t.show();
                    userOptions.setText("发送消息");
                }else if(userOptions.getText().equals("发送消息")){
                    //跳转到聊天页面，同时将当前这个人的userId作为receiveUseId
                    Intent intent = new Intent(UserInfo.this,Talk.class);
                    intent.putExtra("receiveUser",user);//传递User整个对象
                    startActivity(intent);
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
        name = (TextView)findViewById(R.id.user_info_Name);
        nickName = (TextView)findViewById(R.id.user_info_nickname);
        wechatid = (TextView)findViewById(R.id.user_info_userid);
        area = (TextView)findViewById(R.id.user_info_area);
        introduction = (TextView)findViewById(R.id.user_info_introduction);

        name.setText(user.getName());
        nickName.setText("昵称："+user.getNickname());
        wechatid.setText("微信号："+user.getUserId());
        area.setText("地区："+user.getArea());
        introduction.setText(user.getIntroduction());

        String loginUserId = sqLiteHelper.selectLoginIn().getUserId();
        String friendId = user.getUserId();

        //是朋友关系就不显示“添加到通讯录”，而是显示“发送消息”
        //搜索自己也显示“发送消息”
        if(loginUserId.equals(friendId) || sqLiteHelper.isFriend(loginUserId,friendId)){
            userOptions.setText("发送消息");
        }else{
            userOptions.setText("添加到通讯录");
        }
    }


    public void addFriend(String userId,String friendId){
        sqLiteHelper.addFriend(userId,friendId);
    }
}
