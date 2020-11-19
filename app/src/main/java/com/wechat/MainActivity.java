package com.wechat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.FrameLayout;

import com.wechat.Fragments.Fragment1;
import com.wechat.Fragments.Fragment2;
import com.wechat.Fragments.Fragment3;
import com.wechat.Fragments.Fragment4;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


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
    private Fragment1 fg1;
    private Fragment1 fg2;
    private Fragment1 fg3;
    private Fragment1 fg4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBar();

        notificationStatus();

        replaceFragment(new Fragment1());//打开应用时加载第一个碎片
        bottom_bar_1.performClick();//打开应用时，模拟一次点击，选中第一个
    }



    //初始化四个按钮的点击事件
    public void initBar() {
        bottom_bar_1 = (TextView) findViewById(R.id.bottom_bar_1);
        bottom_bar_2 = (TextView) findViewById(R.id.bottom_bar_2);
        bottom_bar_3 = (TextView) findViewById(R.id.bottom_bar_3);
        bottom_bar_4 = (TextView) findViewById(R.id.bottom_bar_4);

        bottom_bar_1.setOnClickListener(this);
        bottom_bar_2.setOnClickListener(this);
        bottom_bar_3.setOnClickListener(this);
        bottom_bar_4.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        bottom_bar_1.setSelected(false);
        bottom_bar_2.setSelected(false);
        bottom_bar_3.setSelected(false);
        bottom_bar_4.setSelected(false);
    }

    //隐藏与显示小红点
    public void notificationStatus(){
        bottom_bar_1_notification = (TextView)findViewById(R.id.bottom_bar_1_notification);
        bottom_bar_2_notification = (TextView)findViewById(R.id.bottom_bar_2_notification);
        bottom_bar_3_notification = (TextView)findViewById(R.id.bottom_bar_3_notification);
        bottom_bar_4_notification = (TextView)findViewById(R.id.bottom_bar_4_notification);


        bottom_bar_1_notification.setVisibility(View.INVISIBLE);//隐藏
        bottom_bar_2_notification.setVisibility(View.INVISIBLE);
        bottom_bar_3_notification.setVisibility(View.INVISIBLE);
        bottom_bar_4_notification.setVisibility(View.INVISIBLE);
    }

    //重新加载新的碎片
    public void replaceFragment(Fragment fragment){
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.replace(R.id.weChat_Menu_content,fragment);
        fTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_bar_1://微信
                setSelected();//清除所有按钮选中
                bottom_bar_1.setSelected(true);//设置当前按钮选中
                replaceFragment(new Fragment1());//更新当前的碎片
                break;
            case R.id.bottom_bar_2://通讯录
                setSelected();
                bottom_bar_2.setSelected(true);
                replaceFragment(new Fragment2());
                break;
            case R.id.bottom_bar_3://发现
                setSelected();
                bottom_bar_3.setSelected(true);
                replaceFragment(new Fragment3());
                break;
            case R.id.bottom_bar_4://我
                setSelected();
                bottom_bar_4.setSelected(true);
                replaceFragment(new Fragment4());
                break;
            default:
                break;
        }
    }
}