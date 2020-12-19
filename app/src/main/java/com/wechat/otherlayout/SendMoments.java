package com.wechat.otherlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.Moments;

public class SendMoments extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    EditText editSend;
    Button buttonSend;
    Moments moments; //朋友圈信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_moments);

        sqLiteHelper = new SQLiteHelper(this);

        changeSendButton();//监听文本更改按钮

        publish();//发布内容


        //返回按钮
        ImageView back = (ImageView)findViewById(R.id.send_moments_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void changeSendButton(){
        editSend = (EditText)findViewById(R.id.edit_send_moments);
        buttonSend = (Button)findViewById(R.id.button_send_moments);
        editSend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editSend.getText().toString().equals("")){
                    buttonSend.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    buttonSend.setTextColor(Color.parseColor("#c1c1c1"));
                    buttonSend.setClickable(false);//设置为不可点击
                }else {
                    buttonSend.setBackgroundColor(Color.parseColor("#07C160"));
                    buttonSend.setTextColor(Color.parseColor("#ffffff"));
                    buttonSend.setClickable(true);//设置为可点击
                }
            }
        });
    }

    public void publish(){
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moments = new Moments();
                moments.setUserId(sqLiteHelper.selectLoginIn().getUserId());//当前登录用户的微信号
                moments.setTextContent(editSend.getText().toString());
                moments.setImageContent("");
                moments.setPublishTime(String.valueOf(System.currentTimeMillis()));//将当前时间戳存入数据库，读取时转换为当前时间
                moments.setLikeUserId("");
                moments.setLikeNum(0);
                sqLiteHelper.publishMoments(moments);

                Toast t = Toast.makeText(SendMoments.this,null,Toast.LENGTH_LONG);
                t.setText("朋友圈发布成功");
                t.show();


                Intent toMoments = new Intent(SendMoments.this, MyMoments.class);
                startActivity(toMoments);

                finish();


            }
        });
    }
}
