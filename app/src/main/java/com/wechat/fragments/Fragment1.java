package com.wechat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.adapter.TalkList;
import com.wechat.adapter.TalkListAdapter;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.Friends;
import com.wechat.entity.Message;
import com.wechat.entity.User;
import com.wechat.otherlayout.Talk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//碎片1
public class Fragment1 extends Fragment {

    private MainActivity mainActivity;

    private SQLiteHelper sqLiteHelper;

    private List<TalkList> talkLists;

    private User loginUser;//获取当前登录用户的信息

    private  Message message;


    private  RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private TalkListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_1,container,false);
        mainActivity = (MainActivity)getActivity();//获取指定活动对象

        sqLiteHelper = new SQLiteHelper(mainActivity);

        getTalkList();//获取聊天信息

        //绑定到RecylerView
        recyclerView = (RecyclerView)view.findViewById(R.id.talk_list);
        llm = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(llm);
        adapter = new TalkListAdapter(talkLists,mainActivity);//填充到RecylerView的适配器
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTalkList();//获取聊天信息
        //返回就更新
        TalkListAdapter newadapter = new TalkListAdapter(talkLists,mainActivity);//填充到RecylerView的适配器
        recyclerView.setAdapter(newadapter);
    }

    public void getTalkList(){
        loginUser  = sqLiteHelper.selectLoginIn();
        List<User> friends;//朋友列表
        friends = sqLiteHelper.selectFriends(loginUser.getUserId());//好友列表
        talkLists = new ArrayList<>();
        for (User f:friends ) {
            //遍历获取当前用户的所有朋友信息，查询出有聊天记录的消息列表
            message = new Message();
            message = sqLiteHelper.selectNewMessage(loginUser.getUserId(),f.getUserId());
            //名字就是当前朋友的名字、消息就是查询出来的消息
            talkLists.add(new TalkList(f,message));
        }
        Collections.sort(talkLists);//按照消息事件对主界面的聊天列表进行排序
    }
}
