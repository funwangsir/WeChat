package com.wechat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDBOpenHelper extends SQLiteOpenHelper {

    private Context myContext;

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        //调用父类创建数据库myWechat.db
        super(context, name, factory, version);
        this.myContext = context;
    }

    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        //建表
        //用户信息表
        db.execSQL("CREATE TABLE IF NOT EXISTS user (" +
                "  userId text(16) PRIMARY KEY NOT NULL," +
                "  password TEXT(16)," +
                "  name TEXT(16) NOT NULL," +
                "  nickname TEXT(16)," +
                "  avatar blob(1024)," +
                "  gender TEXT(4)," +
                "  area TEXT(100)," +
                "  introduction TEXT(60)," +
                "  userPhone TEXT(20)," +
                "  loginStatus TEXT(10)" +
                ");");

        //好友列表
        db.execSQL("CREATE TABLE IF NOT EXISTS friends (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "  userId text(16) NOT NULL," +
                "  userFriendId text(16)" +
                ");");

        //朋友圈信息表表
        db.execSQL("CREATE TABLE IF NOT EXISTS moments (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "  userId text NOT NULL," +
                "  textContent TEXT(1000)," +
                "  imageContent blob," +
                "  publishTime TEXT(19) NOT NULL," +
                "  likeNum INTEGER," +
                "  likeUserId text" +
                ");");

        //聊天消息表
        db.execSQL("CREATE TABLE IF NOT EXISTS message (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "  sendUserId text(16)," +
                "  receiveUserId text(16)," +
                "  textMessage TEXT(1000)," +
                "  imageMessage blob," +
                "  createTime TEXT(19)" +
                ");");

        Log.e("数据库onCreate操作", "该应用第一次安装，执行建表成功！！！！！！！！！！！！！！！" );
    }
    //软件版本号发生改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("ALTER TABLE xxx ADD xx VARCHAR(12) ");
    }


}
