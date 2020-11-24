package com.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wechat.db.SQLiteHelper;
import com.wechat.tool.CheckObjectIsNullUtils;

public class LoginActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;//数据库操作类

    private EditText phoneOrname;
    private EditText password;

    private Button submit;

    private TextView toRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqLiteHelper = new SQLiteHelper(this);

        phoneOrname = (EditText)findViewById(R.id.phoneOrname);
        password = (EditText)findViewById(R.id.password);

        Intent getUserid = getIntent();

        //注册跳转到登录后，在登录的账号框自动填充微信号
        if(!getUserid.getStringExtra("userId").equals(""))
            phoneOrname.setText(getUserid.getStringExtra("userId"));


        submit = (Button)findViewById(R.id.login_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginIn();//进行登录和页面跳转
            }
        });

        toRegister = (TextView)findViewById(R.id.toRegister);
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toregister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(toregister);
            }
        });


    }

    //数据校验是否合法
    public boolean formatIsRight(){
        if(phoneOrname.getText().toString() == "" || phoneOrname.getText().toString().equals("")
                || password.getText().toString() == "" || password.getText().toString().equals("")){
            Toast t = Toast.makeText(LoginActivity.this,null,Toast.LENGTH_LONG);
            t.setText("所有选项均需要填写");
            t.show();
            return false;
        }
        return true;
    }



    //数据库查询校验账号密码是否正确 -
    // 如果验证正确，不管输入的手机号还是微信号，都返回微信号
    public String AccountisRight(){
        if(formatIsRight()) {//账号密码为空就不走数据库流程
            //数据库交互
            String account = phoneOrname.getText().toString();
            String pwd = password.getText().toString();
            return  sqLiteHelper.UserisExist(account,pwd);
        }
        return "fail";
    }

    //跳转到主页
    public void loginIn(){
        if(!AccountisRight().equals("fail")){

            sqLiteHelper.userLoginIn(AccountisRight());

            //跳转到主页
            Toast t = Toast.makeText(LoginActivity.this,null,Toast.LENGTH_SHORT);
            t.setText("登录成功");
            t.show();
            Intent toMainActivity = new Intent(LoginActivity.this,MainActivity.class);
            toMainActivity.putExtra("userId",AccountisRight());//传递当前userId
            startActivity(toMainActivity);
        }
        else{
            Toast t = Toast.makeText(LoginActivity.this,null,Toast.LENGTH_LONG);
            t.setText("账号密码校验失败");
            t.show();
        }
    }
}
