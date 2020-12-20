package com.wechat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.otherlayout.MyMoments;

//碎片3
public class Fragment3 extends Fragment {
    private MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_3,container,false);

        mainActivity = (MainActivity)getActivity();//获取指定活动对象
        RelativeLayout gotoMoments = (RelativeLayout)view.findViewById(R.id.goto_moments);
        gotoMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomoments = new Intent(mainActivity, MyMoments.class);
                startActivity(gotomoments);
            }
        });

        return view;
    }
}
