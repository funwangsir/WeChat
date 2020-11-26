package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private ImageView back;
    private TextView receiveMsgName;

    private EditText sendMsgEdit;

    private ImageView sendMore;
    private Button sendButton;

    private TextView sendContent;
    private TextView receiveContent;

    //每次聊天都要获取到发送和接收方的userid
    private String sendUserId;//发送者的id就是当前登录的userid
    private String receiveUserId;

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



        User receiveUser = (User)getIntent().getSerializableExtra("receiveUser");
        receiveMsgName.setText(receiveUser.getName());
        sendUserId = sqLiteHelper.selectLoginIn().getUserId();//当前登录用户id
        receiveUserId = receiveUser.getUserId();//上一个活动传递过来的用户id

    }

    public void backActivity(){
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        List<Message> messageList = sqLiteHelper.selectMessage(sendUserId,receiveUserId);
        msgList = new ArrayList<>();
        for (Message m:messageList) {
            msgList.add(new MessageList(R.drawable.ic_default_img,m));
        }

        //将数据绑定到RecyclerView中
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.send_msg_list);//绑定RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        MessageListAdapter adapter = new MessageListAdapter(msgList,sendUserId);
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(){

        //点击发送按钮，发送消息
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();

                message.setSendUserId(sendUserId);
                message.setReceiveUserId(receiveUserId);
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
