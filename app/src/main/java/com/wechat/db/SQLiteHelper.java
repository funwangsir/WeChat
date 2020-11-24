package com.wechat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wechat.entity.User;

import java.util.List;

//数据库常用操作类
public class SQLiteHelper {
    private MyDBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    private User user;

    public SQLiteHelper(Context context) {
        dbOpenHelper  = new MyDBOpenHelper(context,"myWechat.db",null,1);
        db = dbOpenHelper.getWritableDatabase();
    }

    /**
     * 登录验证，通过userId或者userPhone验证用户是否存在，同时验证密码
     * @param account 账号
     * @param pw 密码
     * @return 找到了该记录就返回userId，否则返回fail
     */
    public String UserisExist(String account,String pw){
        String[] para = new String[]{account,pw};//where的条件参数
        String[] columns = new String[]{"userId"};//要查询的列

        //查询微信号
        Cursor cursor1 = db.query("user",
                null,//查询所有列
                "userId=? and password=?",
                para,null,null,null);

        //查询手机号
        Cursor cursor2 = db.query("user",
                null,
                "userPhone=? and password=?",
                para,null,null,null);
        if(cursor1.getCount() != 0){//通过userId查询到该条记录

            return account;//直接返回account
        }else if(cursor2.getCount() != 0){//通过手机号查询到该条记录
            String userId = null;
            Cursor getUserIdByphone = db.query("user",
                    columns,
                    "userPhone=? and password=?",
                    para,null,null,null);
            if(getUserIdByphone.moveToFirst()){//只有一条信息，不需要遍历
                userId = getUserIdByphone.getString(getUserIdByphone.getColumnIndex("userId"));
            }
            return userId;
        }
        else{//账号不存在
            return "fail";
        }
    }


    /**
     * 登录成功的用户，需要将loginStatus修改为LoginIn
     * @param userId 要修改状态的userId
     */
    public void userLoginIn(String userId){
        ContentValues values = new ContentValues();
        values.put("loginStatus","loginIn");//登陆状态设置为loginIn
        //修改条件为userId
        db.update("user",values,"userId=?",new String[]{userId});
    }


    /**
     * 推退出登录的用户，需要将loginStatus修改为LoginOut
     * @param userId 要修改状态的userId
     */
    public void userLoginOut(String userId){
        ContentValues values = new ContentValues();
        values.put("loginStatus","loginOut");//登陆状态设置为loginOut

        //修改条件为userId
        db.update("user",values,"userId=?",new String[]{userId});
    }


    /**
     * 用户注册
     * @param user 用户数据的实体集合
     */
    public void registered(User user){
        //插入数据
        ContentValues values = new ContentValues();//使用ContentValues对象来封装数据
        values.put("userId",user.getUserId());//字段-微信号 值-当前时间戳
        values.put("password",user.getPassword());//密码
        values.put("name",user.getName());//真实姓名
        values.put("nickname",user.getNickname());//昵称
        values.put("avatar",user.getAvatar());//头像
        values.put("gender",user.getGender());//性别由身份证识别，无需输入
        values.put("area",user.getArea());//地区由身份证识别，默认未户籍所在城市
        values.put("introduction",user.getIntroduction());//自我介绍
        values.put("userPhone",user.getUserPhone());//手机号
        values.put("loginStatus",user.getLoginStatus());//登录状态
        db.insert("user",null,values);//插入数据库
    }


    /**
     *  查询数据库中状态为LoginIn的用户
     *  如果不存在已登录的用户，返回null
     *  否则返回已登录的userId
     * @return 已登录的user信息集合
     */
    public User selectLoginIn(){
        User user = new User();
        Cursor cursor = db.query("user",
                null,
                "loginStatus=?",
                new String[]{"loginIn"},null,null,null);

        if(cursor.getCount() != 0){
            if(cursor.moveToFirst()){
                user.setUserId( cursor.getString(cursor.getColumnIndex("userId")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
                user.setUserPhone(cursor.getString(cursor.getColumnIndex("userPhone")));
                //其他信息后面补上
            }
        }
        return user;
    }

    /**
     * 通过UserId查询user的所有信息
     * @param userid
     * @return
     */
    public User getUserInfo(String userid){
        user = new User();

        //从数据库查询出其他信息并存储到User对象中
        Cursor cursor = db.query("user",
                null,
                "userId=?",
                new String[]{userid},null,null,null);
        if(cursor.getCount() != 0){
            if(cursor.moveToFirst()){
                user.setUserId( cursor.getString(cursor.getColumnIndex("userId")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
                user.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
                user.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                user.setArea(cursor.getString(cursor.getColumnIndex("area")));
                user.setIntroduction(cursor.getString(cursor.getColumnIndex("introduction")));
                user.setUserPhone(cursor.getString(cursor.getColumnIndex("userPhone")));
                user.setLoginStatus(cursor.getString(cursor.getColumnIndex("loginStatus")));
            }
        }
        return user;
    }

    /**
     * 通过userid或者phone查询用户信息
     * 两个条件都要去查询数据库，任一满足就返回用户信息
     * @param useridOrphoe
     * @return
     */
    public User getUserInfoByuserIdOrPhone(String useridOrphoe){
        user = new User();

        Cursor cursorUser = db.query("user",
                null,
                "userId=?",
                new String[]{useridOrphoe},null,null,null);
        Cursor cursorPhone = db.query("user",
                null,
                "userPhone=?",
                new String[]{useridOrphoe},null,null,null);

        if(cursorUser.getCount() != 0){
            if(cursorUser.moveToFirst()){
                user.setUserId( cursorUser.getString(cursorUser.getColumnIndex("userId")));
                user.setPassword(cursorUser.getString(cursorUser.getColumnIndex("password")));
                user.setName(cursorUser.getString(cursorUser.getColumnIndex("name")));
                user.setNickname(cursorUser.getString(cursorUser.getColumnIndex("nickname")));
                user.setAvatar(cursorUser.getString(cursorUser.getColumnIndex("avatar")));
                user.setGender(cursorUser.getString(cursorUser.getColumnIndex("gender")));
                user.setArea(cursorUser.getString(cursorUser.getColumnIndex("area")));
                user.setIntroduction(cursorUser.getString(cursorUser.getColumnIndex("introduction")));
                user.setUserPhone(cursorUser.getString(cursorUser.getColumnIndex("userPhone")));
                user.setLoginStatus(cursorUser.getString(cursorUser.getColumnIndex("loginStatus")));
            }
        }else if(cursorPhone.getCount() != 0){
            if(cursorPhone.moveToFirst()){
                user.setUserId( cursorPhone.getString(cursorPhone.getColumnIndex("userId")));
                user.setPassword(cursorPhone.getString(cursorPhone.getColumnIndex("password")));
                user.setName(cursorPhone.getString(cursorPhone.getColumnIndex("name")));
                user.setNickname(cursorPhone.getString(cursorPhone.getColumnIndex("nickname")));
                user.setAvatar(cursorPhone.getString(cursorPhone.getColumnIndex("avatar")));
                user.setGender(cursorPhone.getString(cursorPhone.getColumnIndex("gender")));
                user.setArea(cursorPhone.getString(cursorPhone.getColumnIndex("area")));
                user.setIntroduction(cursorPhone.getString(cursorPhone.getColumnIndex("introduction")));
                user.setUserPhone(cursorPhone.getString(cursorPhone.getColumnIndex("userPhone")));
                user.setLoginStatus(cursorPhone.getString(cursorPhone.getColumnIndex("loginStatus")));
            }
        }

        return user;
    }

    //测试打印数据库所有数据，便于调试
    public void showAllData(){
        Cursor user = db.query("user",null,null,null,null,null,null);
        if(user.moveToFirst()){
            do{
                Log.e("user表", "userId="+user.getString(user.getColumnIndex("userId")));
                Log.e("user表", "password="+user.getString(user.getColumnIndex("password")));
                Log.e("user表", "name="+user.getString(user.getColumnIndex("name")));
                Log.e("user表", "nickname="+user.getString(user.getColumnIndex("nickname")));
                Log.e("user表", "avatar="+user.getString(user.getColumnIndex("avatar")));
                Log.e("user表", "gender="+user.getString(user.getColumnIndex("gender")));
                Log.e("user表", "area="+user.getString(user.getColumnIndex("area")));
                Log.e("user表", "introduction="+user.getString(user.getColumnIndex("introduction")));
                Log.e("user表", "userPhone="+user.getString(user.getColumnIndex("userPhone")));
                Log.e("user表", "loginStatus="+user.getString(user.getColumnIndex("loginStatus")));
                Log.e("user表", "------------------------");
            }while (user.moveToNext());
        }
    }
}
