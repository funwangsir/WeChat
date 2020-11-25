package com.wechat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.otherlayout.Talk;


//碎片1
public class Fragment1 extends Fragment {

    private MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_1,container,false);
        mainActivity = (MainActivity)getActivity();//获取指定活动对象


        Button b = (Button)view.findViewById(R.id.talg_test);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, Talk.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
