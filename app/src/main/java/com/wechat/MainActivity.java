package com.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wechat.db.MyDBOpenHelper;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.Friends;
import com.wechat.entity.User;
import com.wechat.fragments.Fragment1;
import com.wechat.fragments.Fragment2;
import com.wechat.fragments.Fragment3;
import com.wechat.fragments.Fragment4;
import com.wechat.otherlayout.Search;
import com.wechat.tool.CheckObjectIsNullUtils;

import java.util.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteHelper sqLiteHelper;

    //底部导航栏的四个按钮
    private TextView bottom_bar_1;
    private TextView bottom_bar_2;
    private TextView bottom_bar_3;
    private TextView bottom_bar_4;

    //对应四个按钮的消息红点提示
    private TextView bottom_bar_1_notification;
    private TextView bottom_bar_2_notification;
    private TextView bottom_bar_3_notification;
    private TextView bottom_bar_4_notification;

    //对应四个按钮的碎片对象
    private Fragment1 fg1 = new Fragment1();
    private Fragment2 fg2 = new Fragment2();
    private Fragment3 fg3 = new Fragment3();
    private Fragment4 fg4 = new Fragment4();

    private RelativeLayout topBar;//顶部状态栏
    private LinearLayout bottomBar;//底部状态栏

    private TextView topTitle;//顶部的文字标题
    private ImageButton wechatAddButton;//顶部的添加按钮

    private MyDBOpenHelper dbOpenHelper;//数据库操作类
    private SQLiteDatabase db;

    private ImageButton search;//顶部状态栏


    private User user;//用户对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = new User();

        sqLiteHelper = new SQLiteHelper(this);

        sqLiteHelper.showAllData(); //测试，打印数据库所有信息，便于调试

        checkLoginIn();//启动应用确定检查是否登录

        initControl();//加载绑定事件

        notificationStatus();//底部状态栏的小红点

        replaceFragment(new Fragment1());//打开应用时加载第一个碎片
        bottom_bar_1.performClick();//打开应用时，模拟一次点击，选中第一个
    }


    //初始化一些控件，绑定点击事件
    public void initControl() {
        bottom_bar_1 = (TextView) findViewById(R.id.bottom_bar_1);
        bottom_bar_2 = (TextView) findViewById(R.id.bottom_bar_2);
        bottom_bar_3 = (TextView) findViewById(R.id.bottom_bar_3);
        bottom_bar_4 = (TextView) findViewById(R.id.bottom_bar_4);

        topBar = (RelativeLayout) findViewById(R.id.top_toolbar);
        bottomBar = (LinearLayout) findViewById(R.id.bottom_bar);

        topTitle = (TextView) findViewById(R.id.top_toolbar_title);
        wechatAddButton = (ImageButton) findViewById(R.id.wechat_add);

        //绑定点击事件
        bottom_bar_1.setOnClickListener(this);
        bottom_bar_2.setOnClickListener(this);
        bottom_bar_3.setOnClickListener(this);
        bottom_bar_4.setOnClickListener(this);
        wechatAddButton.setOnClickListener(this);


        search = (ImageButton)findViewById(R.id.wechat_search1);
        search.setOnClickListener(this);
    }

    //监听所有点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_bar_1://微信
                bottomBar1();
                break;
            case R.id.bottom_bar_2://通讯录
                bottomBar2();
                break;
            case R.id.bottom_bar_3://发现
                bottomBar3();
                break;
            case R.id.bottom_bar_4://我
                bottomBar4();
                break;
            case R.id.wechat_add://顶部悬浮框
                initPopWindow(view);//显示悬浮框
                break;
            case R.id.wechat_search1://顶部搜索
                search();
            default:
                break;
        }
    }

    //重置所有文本的选中状态
    private void clearSelected() {
        bottom_bar_1.setSelected(false);
        bottom_bar_2.setSelected(false);
        bottom_bar_3.setSelected(false);
        bottom_bar_4.setSelected(false);
    }

    //隐藏与显示小红点
    public void notificationStatus() {
        bottom_bar_1_notification = (TextView) findViewById(R.id.bottom_bar_1_notification);
        bottom_bar_2_notification = (TextView) findViewById(R.id.bottom_bar_2_notification);
        bottom_bar_3_notification = (TextView) findViewById(R.id.bottom_bar_3_notification);
        bottom_bar_4_notification = (TextView) findViewById(R.id.bottom_bar_4_notification);


        bottom_bar_1_notification.setVisibility(View.INVISIBLE);//隐藏
        bottom_bar_2_notification.setVisibility(View.INVISIBLE);
        bottom_bar_3_notification.setVisibility(View.INVISIBLE);
        bottom_bar_4_notification.setVisibility(View.INVISIBLE);
    }

    //重新加载新的碎片
    public void replaceFragment(Fragment fragment) {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.weChat_Menu_content, fragment);
        fTransaction.commit();
    }


    //四个按钮执行的事件
    public void bottomBar1() {
        clearSelected();//清除所有按钮选中
        bottom_bar_1.setSelected(true);//设置当前按钮选中
        replaceFragment(fg1);//更新当前的碎片
        topBar.setVisibility(View.VISIBLE);//显示当前顶部状态栏
        topTitle.setText("微信");

        //加载聊天记录和消息列表
    }

    public void bottomBar2() {
        clearSelected();
        bottom_bar_2.setSelected(true);
        replaceFragment(fg2);
        topBar.setVisibility(View.VISIBLE);//显示当前顶部状态栏
        topTitle.setText("通讯录");
    }

    public void bottomBar3() {
        clearSelected();
        bottom_bar_3.setSelected(true);
        replaceFragment(fg3);
        topBar.setVisibility(View.VISIBLE);//显示当前顶部状态栏
        topTitle.setText("发现");
    }

    public void bottomBar4() {
        clearSelected();
        bottom_bar_4.setSelected(true);
        replaceFragment(fg4);
        topBar.setVisibility(View.GONE); //我的页面不显示顶部状态栏
    }

    //显示悬浮窗
    private void initPopWindow(View v) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_list, null, false);
        RelativeLayout addFreind = (RelativeLayout)view.findViewById(R.id.add_list_text2);

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //这些为了点击非PopupWindow区域，PopupWindow会消失的
        //如果没有下面的代码的话，你会发现，当你把PopupWindow显示出来了
        //无论你按多少次后退键 PopupWindow并不会关闭，而且退不出程序
        // 加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效

        popWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        int i = popWindow.getContentView().getMeasuredWidth();
//        设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v,-i+120, 40);

        addFreind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();//隐藏
                search();
            }
        });
    }


    //启动应用要检查是否有登录用户
    public void checkLoginIn(){
        if(CheckObjectIsNullUtils.objCheckIsNull(sqLiteHelper.selectLoginIn())){
            Intent toLogin = new Intent(MainActivity.this,LoginActivity.class);
            toLogin.putExtra("useridOrphone","");
            startActivity(toLogin);
            finish();
        }else{
            //加载聊天记录
            user = sqLiteHelper.selectLoginIn();//返回user的信息
        }
    }

    //搜索联系人、好友
    public void search(){
        Intent intent = new Intent(MainActivity.this, Search.class);
        intent.putExtra("userId",user.getUserId());
        startActivity(intent);
    }

}






