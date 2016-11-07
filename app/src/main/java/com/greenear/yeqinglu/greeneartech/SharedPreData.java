package com.greenear.yeqinglu.greeneartech;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yeqing.lu on 2016/11/7.
 */

public class SharedPreData {

    public Context context;
    public UserInfo userInfo;

    public void save(String filename,String name, String password, String token)
    {
        //调用 SharedPreferences 对象的 edit()方法来获取一个 SharedPreferences.Editor 对象。
        SharedPreferences.Editor editor= (SharedPreferences.Editor) context.getSharedPreferences(filename,Context.MODE_PRIVATE);
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
