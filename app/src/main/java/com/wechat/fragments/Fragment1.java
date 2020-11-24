package com.wechat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.wechat.R;


//碎片1
public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_1,container,false);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        return view;
    }
}
