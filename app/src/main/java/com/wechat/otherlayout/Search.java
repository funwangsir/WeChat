package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wechat.LoginActivity;
import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;
import com.wechat.tool.CheckObjectIsNullUtils;

import java.io.SequenceInputStream;

public class Search extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;

    private TextView cancel;//取消搜索，返回主页

    private TextView showMyID;//显示当前用户的id

    private EditText searchText;//搜索框

    private TextView searchShowText;//下面实时显示搜索内容

    private LinearLayout showUserInfos;//用于点击搜索用户的LinearLayout

    private User user;//当前登录的用户id
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sqLiteHelper = new SQLiteHelper(this);

        cancel = (TextView)findViewById(R.id.cancel_search);
        showMyID = (TextView)findViewById(R.id.show_my_id);
        searchText = (EditText)findViewById(R.id.search_text);
        searchShowText = (TextView)findViewById(R.id.search_show_text);
        showUserInfos = (LinearLayout)findViewById(R.id.show_user_infos);


        cancel();//点击取消，返回主页

        showId(); //显示自己的id

        searchTextChange();//更新底部的搜索信息

        showUserInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(searchShowText.getText().toString());//搜索用户
            }
        });
    }

    public void cancel(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showId(){
        //当前登录用户的userId
        user = sqLiteHelper.selectLoginIn();
        if(!CheckObjectIsNullUtils.objCheckIsNull(user)){
            showMyID.setText("我的微信号："+user.getUserId());
        }
    }

    public void searchTextChange(){
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入前的事件
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //输入中的事件
                searchShowText.setText(searchText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //输入后的事件
            }
        });
    }

    public void search(String userIdOrPhone){
        //通过微信号或者手机号查询到他的用户信息
        User u = sqLiteHelper.getUserInfoByuserIdOrPhone(userIdOrPhone);
        if(!CheckObjectIsNullUtils.objCheckIsNull(u)){//用户存在，跳转到用户个人信息页
            Intent intent = new Intent(Search.this,UserInfo.class);
            intent.putExtra("userId",u.getUserId());//User被序列化后可以传递
            startActivity(intent);
        }else{//用户不存在
            Toast t = Toast.makeText(Search.this,null,Toast.LENGTH_LONG);
            t.setText("用户不存在");
            t.show();
        }
    }


}
