package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.adapter.MessageList;
import com.wechat.adapter.MessageListAdapter;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.Message;
import com.wechat.entity.User;
import com.wechat.tool.TransformDPandPX;

import java.util.ArrayList;
import java.util.List;

public class Talk extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    private User sendUser;//发送消息的用户，即当前登录的用户
    private User receiveUser;//接收消息的用户
    private ImageView back;
    private TextView receiveMsgName;

    private EditText sendMsgEdit;

    private ImageView sendMore;
    private Button sendButton;

    private TextView sendContent;
    private TextView receiveContent;


    private List<MessageList> msgList;//消息列表（包含头像信息）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        init();

        backActivity();//返回上一个Activity

        changeSendButton();//发送消息的按钮的显示与隐藏

        loadMessageList();//实时加载聊天记录

        sendMessage();//点击发送按钮发送消息
    }

    public void  init(){
        sqLiteHelper = new SQLiteHelper(this);
        sendMsgEdit = (EditText)findViewById(R.id.send_edit);
        receiveMsgName = (TextView)findViewById(R.id.receive_msg_name);
        sendMore = (ImageView)findViewById(R.id.send_more);
        sendButton = (Button)findViewById(R.id.send_button);


        //获取当前登录的user
        sendUser = sqLiteHelper.selectLoginIn();

        //获取传递过来的User对象
        String receiveUserid = getIntent().getStringExtra("receiveUserId");
        receiveUser = sqLiteHelper.getUserInfo(receiveUserid);
        receiveMsgName.setText(receiveUser.getName());

    }

    public void backActivity(){
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Talk.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void changeSendButton(){
        sendMsgEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(sendMsgEdit.getText().toString().equals("")){
                    sendMore.setVisibility(View.VISIBLE);//可见
                    sendButton.setVisibility(View.GONE);//不可见 - 不占据位置
                }else {
                    sendMore.setVisibility(View.GONE);//不可见 - 不占据位置
                    sendButton.setVisibility(View.VISIBLE);//可见
                }
            }
        });
    }

    public void loadMessageList(){
        //查询出聊天记录
        List<Message> messageList = sqLiteHelper.selectMessage(sendUser.getUserId(),receiveUser.getUserId());
        msgList = new ArrayList<>();
        for (Message m:messageList) {
            msgList.add(new MessageList(sendUser,receiveUser,m));//将当前登录用户的user传递过去
        }

        //将数据绑定到RecyclerView中
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.send_msg_list);//绑定RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        MessageListAdapter adapter = new MessageListAdapter(msgList,sendUser.getUserId());
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(){

        //点击发送按钮，发送消息
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();

                message.setSendUserId(sendUser.getUserId());
                message.setReceiveUserId(receiveUser.getUserId());
                message.setTextMessage(sendMsgEdit.getText().toString());//发送消息内容
                message.setImageMessage("");
                message.setCreateTime(String.valueOf(System.currentTimeMillis()));//当前时间戳

                sqLiteHelper.sendMessage(message);//存入数据库
                loadMessageList();//更新加载聊天记录
                sendMsgEdit.setText("");
            }
        });
    }
}
