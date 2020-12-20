package com.wechat.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

//时间转换工具类
public class DateUtils {
    //时间戳转换为yyyy-MM-dd HH:mm:ss
    public static String transformDate(long currentTimeMillis){
        //声明格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取时间
        Date date = new Date(currentTimeMillis);
        //进行转换
        String time = simpleDateFormat.format(date);
        return time;
    }

    //获取当前yyyy-MM-dd HH:mm:ss时间
    public static String getNow(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
