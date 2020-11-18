package com.wechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.FrameLayout;
import com.wechat.Frame.MyFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView bottom_bar_1;
    private TextView bottom_bar_2;
    private TextView bottom_bar_3;
    private TextView bottom_bar_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBar();
        bottom_bar_1.performClick();//模拟加载进去的第一次点击，选中第一个
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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_bar_1:
                setSelected();//清除所有按钮选中
                bottom_bar_1.setSelected(true);//设置当前按钮选中
                break;
            case R.id.bottom_bar_2:
                setSelected();
                bottom_bar_2.setSelected(true);
                break;
            case R.id.bottom_bar_3:
                setSelected();
                bottom_bar_3.setSelected(true);
                break;
            case R.id.bottom_bar_4:
                setSelected();
                bottom_bar_4.setSelected(true);
                break;
            default:
                break;
        }
    }
}