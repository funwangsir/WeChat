package com.wechat.otherlayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MyInfo extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    private User user;

    private ImageView headimg;
    private TextView nickname;
    private TextView userphone;
    private TextView userid;
    private TextView realname;

    private  ImageButton back;


    private static final int ALBUM_OK = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        sqLiteHelper = new SQLiteHelper(this);

        init();

        changeHeadImg();//更换头像

        back();

    }
    public void init(){
        user = sqLiteHelper.selectLoginIn();
        headimg = (ImageView)findViewById(R.id.myinfo_headimg);
        nickname = (TextView)findViewById(R.id.myinfo_mynickname);
        userphone = (TextView)findViewById(R.id.myinfo_userphone);
        userid = (TextView)findViewById(R.id.myinfo_userid);
        realname = (TextView)findViewById(R.id.myinfo_realname);

        byte[] img = user.getAvatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        headimg.setImageBitmap(bitmap);

        nickname.setText(user.getNickname());
        userphone.setText(user.getUserPhone());
        userid.setText(user.getUserId());
        realname.setText(user.getName());
    }

    public void changeHeadImg(){
        headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                albumIntent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, ALBUM_OK);
            }
        });
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
                headimg.setImageBitmap(bitmap);
                sqLiteHelper.changeAvator(user,bitmap);//数据库更新

                Toast t = Toast.makeText(this, null, Toast.LENGTH_SHORT);
                t.setText("微信头像已更换");
                t.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void back(){
        back = (ImageButton)findViewById(R.id.myinfo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
