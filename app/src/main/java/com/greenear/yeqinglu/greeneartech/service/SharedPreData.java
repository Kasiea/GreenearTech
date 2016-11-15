package com.greenear.yeqinglu.greeneartech.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.greenear.yeqinglu.greeneartech.model.UserInfo;

/**
 * Created by yeqing.lu on 2016/11/7.
 */

public class SharedPreData {

    private Context context;
    private UserInfo userInfo;


    public  SharedPreData(Context context, UserInfo userInfo){
        this.context = context;
        this.userInfo = userInfo;
    }

    public void save(String filename,String name, String password, String token)
    {
        //调用 SharedPreferences 对象的 edit()方法来获取一个 SharedPreferences.Editor 对象。
        SharedPreferences.Editor editor= context.getSharedPreferences(filename,Context.MODE_PRIVATE).edit();
        //向 SharedPreferences.Editor 对象中添加数据，比如添加一个布尔型数据就使用
//        putBoolean 方法，添加一个字符串则使用 putString()方法，以此类推。
        editor.putString("name", name);
        editor.putString("password", password);
        editor.putString("token", token);
        //调用 commit()方法将添加的数据提交，从而完成数据存储操作。
        editor.commit();
    }

    public UserInfo load(String filename)
    {
        SharedPreferences preferences = context.getSharedPreferences(filename,Context.MODE_PRIVATE);
        userInfo.name = preferences.getString("name","");
        userInfo.password = preferences.getString("password","");
        userInfo.token = preferences.getString("token","");

        return userInfo;
    }
}