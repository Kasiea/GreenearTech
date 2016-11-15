package com.greenear.yeqinglu.greeneartech.model;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonUserToken;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.interf.BaseUser;
import com.greenear.yeqinglu.greeneartech.net.API;
import com.greenear.yeqinglu.greeneartech.ui.LoginActivity;
import com.greenear.yeqinglu.greeneartech.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeqing.lu on 2016/11/14.
 */

public class User implements BaseUser {

    private Context context;
    private UserInfo userInfo;
    private RequestQueue requestQueue;
    private int IS_FINISHED = 1;
    private Handler handler;

    public User(Context context, UserInfo userInfo, RequestQueue mQueue, Handler handler) {
        this.context = context;
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;
    }

    @Override
    public void login() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.LOG_IN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonUserToken jsonReturn = fast_json.parseObject(response, JsonUserToken.class);
                        userInfo.token = jsonReturn.getData().getToken();
                        Toast.makeText(context ,"登陆成功！ token = " + userInfo.token ,Toast.LENGTH_SHORT).show();

                        //转入主界面
                        Message message = Message.obtain(handler);
                        message.what = 1;
                        message.sendToTarget();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                        Toast.makeText(context, R.string.login_fail,Toast.LENGTH_SHORT).show();
                    }
                })
        {

            //匿名类重写方法，用来传输post数据
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                     map.put("username", userInfo.name);
                     map.put("password", userInfo.password);
//                map.put("username", "bhermann");
//                map.put("password", "123456");
                return map;
            }};

        //将这个StringRequest对象添加到RequestQueue里
        requestQueue.add(stringRequest);
    }

    @Override
    public void register() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonUserToken jsonReturn = fast_json.parseObject(response, JsonUserToken.class);
                        userInfo.token = jsonReturn.getData().getToken();
                        Toast.makeText(context ,"注册成功！ token = " + userInfo.token ,Toast.LENGTH_SHORT).show();

                        //转入主界面
                        Message message = Message.obtain(handler);
                        message.what = 1;
                        message.sendToTarget();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                        Toast.makeText(context, R.string.registr_fail,Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", userInfo.name);
                map.put("password", userInfo.password);
                return map;
            }};

        requestQueue.add(stringRequest);
    }
}
