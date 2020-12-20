package com.wechat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;

    private final String userId = String.valueOf(System.currentTimeMillis());//当前时间戳作为微信号


    private LinearLayout uploadHeadImg;
    private ImageView headImg;
    private EditText re_Phone;
    private EditText re_name;
    private EditText re_nickName;
    private EditText re_Pwd;
    private EditText re_Pwd_again;

    private Button re_submit;

    private TextView toLogin;

    private static final int ALBUM_OK = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sqLiteHelper = new SQLiteHelper(this);

        uploadHeadImg = (LinearLayout)findViewById(R.id.re_upload_headimg);
        headImg = (ImageView)findViewById(R.id.re_headimg);
        re_Phone = (EditText) findViewById(R.id.re_Phone);
        re_name = (EditText)findViewById(R.id.re_name);
        re_nickName = (EditText) findViewById(R.id.re_nickName);
        re_Pwd = (EditText) findViewById(R.id.re_Pwd);
        re_Pwd_again = (EditText) findViewById(R.id.re_Pwd_again);
        re_submit = (Button)findViewById(R.id.registerSubmit);


        //上传头像
        uploadHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, ALBUM_OK);
            }
        });

        //提交注册按钮
        re_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registedToLogin();
            }
        });

        toLogin = (TextView)findViewById(R.id.toLogin);

        //跳转到登录
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(toLoginActivity);
            }
        });

    }

    //数据校验是否合法
    public boolean formatIsRight(){
        if(re_Phone.getText().toString() == "" || re_Phone.getText().toString().equals("")
                || re_name.getText().toString() == "" || re_name.getText().toString().equals("")
                || re_nickName.getText().toString() == "" || re_nickName.getText().toString().equals("")
                || re_Pwd.getText().toString() == "" || re_Pwd.getText().toString().equals("")
                || re_Pwd_again.getText().toString() == "" || re_Pwd_again.getText().toString().equals("") ){
            Toast t = Toast.makeText(RegisterActivity.this,null,Toast.LENGTH_SHORT);
            t.setText("所有选项均需要填写");
            t.show();
            return false;
        }
        if( !re_Pwd.getText().toString().equals(re_Pwd_again.getText().toString()) ){
            Toast t = Toast.makeText(RegisterActivity.this,null,Toast.LENGTH_SHORT);
            t.setText("两次输入密码不相同");
            t.show();
            return false;
        }
        return true;
    }

    //将数据添加到数据库
    public boolean registered(){
        if(formatIsRight()){//数据校验合法后，进行注册

            User user = new User();
            user.setUserId(userId);
            user.setPassword(re_Pwd.getText().toString());
            user.setName(re_name.getText().toString());
            user.setNickname(re_nickName.getText().toString());

            headImg.setDrawingCacheEnabled(true);
            Bitmap bitmap = headImg.getDrawingCache();

    //            Bitmap bitmap = ((BitmapDrawable) ((ImageView) headImg).getDrawable()).getBitmap();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            headImg.setDrawingCacheEnabled(false);
            user.setAvatar(os.toByteArray());
//            user.setAvatar(new byte[]{});
            user.setGender("男");
            user.setArea("深圳-微信的老家");
            user.setIntroduction("我是测试用户，要啥子个性嘛");
            user.setUserPhone(re_Phone.getText().toString());
            user.setLoginStatus("loginOut");

            sqLiteHelper.registered(user);

            Log.e("RegisterActivity:测试", "注册成功");
            return true;
        }
        return false;
    }

    //注册成功跳转到登录Activity
    public void registedToLogin(){
        if(registered()){

            Toast t = Toast.makeText(RegisterActivity.this,null,Toast.LENGTH_LONG);
            t.setText("注册完成，欢迎使用微信，您的微信号为“"+userId+"”ଘ(੭ˊᵕˋ)੭");
            t.show();

            Intent toLoginActivity = new Intent(RegisterActivity.this,LoginActivity.class);
            //注册后将微信号传递给登录，填充到登录的账号框
            toLoginActivity.putExtra("useridOrphone",userId);
            startActivity(toLoginActivity);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ALBUM_OK == requestCode) {

            ContentResolver cr = this.getContentResolver();
            Uri uri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                Log.e("图片信息", " onActivityResult "
                        + data.getData().toString());//此处用Log.e，仅是为了查看红色Log方便
                headImg.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
