package com.wechat.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wechat.MainActivity;
import com.wechat.R;
import com.wechat.adapter.FriendList;
import com.wechat.adapter.FriendListAdapter;
import com.wechat.db.SQLiteHelper;
import com.wechat.entity.User;

import java.util.ArrayList;
import java.util.List;

//碎片2
public class Fragment2 extends Fragment {
    private MainActivity mainActivity;
    User user;
    SQLiteHelper sqLiteHelper;
    List<FriendList> friendLists;//存储好友信息的列表
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment_2,container,false);

        mainActivity = (MainActivity)getActivity();//获取指定活动对象

        sqLiteHelper = new SQLiteHelper(mainActivity);

        showFriendList();//加载好友信息到集合列表中

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.friends_list);//绑定RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(llm);
        FriendListAdapter adapter = new FriendListAdapter(friendLists,mainActivity);//传递信息
        recyclerView.setAdapter(adapter);


        return view;
    }
    //显示好友列表信息
    public void showFriendList(){
        user = sqLiteHelper.selectLoginIn();//获取登录用户的信息
        friendLists = new ArrayList<>();
        //查询好友列表
        List<User> userFriends = sqLiteHelper.selectFriends(user.getUserId());
        for (User u: userFriends) {//将其转换为FriendList类型
            friendLists.add(new FriendList(R.drawable.ic_default_img,u));//头像均暂时使用默认图片
        }


    }
}
