package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.wechat.R;
import com.wechat.tool.TransformDPandPX;

public class Talk extends AppCompatActivity {

    private ImageView back;

    private EditText sendMsgEdit;

    private ImageView sendMore;
    private Button sendButton;

    private TextView sendContent;
    private TextView receiveContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        sendMsgEdit = (EditText)findViewById(R.id.send_edit);
        sendMore = (ImageView)findViewById(R.id.send_more);
        sendButton = (Button)findViewById(R.id.send_button);


        sendContent = (TextView)findViewById(R.id.send_msg_content);
        receiveContent = (TextView)findViewById(R.id.receive_msg_content);

        backActivity();//返回上一个Activity

        setMsgContentMaxWidth();//聊天最大宽度限制

        changeSendButton();//发送消息的按钮的显示与隐藏

        sendMessage();//发送消息
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

    public void setMsgContentMaxWidth(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel =outMetrics.widthPixels;//获取屏幕宽度（px像素单位）

        sendContent.setMaxWidth( widthPixel-128);
        receiveContent.setMaxWidth( widthPixel-128);

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

    public void sendMessage(){

    }
}
