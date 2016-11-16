package com.greenear.yeqinglu.greeneartech.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBatQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBms;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBmsQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonUserToken;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.interf.BaseUser;
import com.greenear.yeqinglu.greeneartech.net.API;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;
import com.greenear.yeqinglu.greeneartech.ui.LoginActivity;
import com.greenear.yeqinglu.greeneartech.ui.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yeqing.lu on 2016/11/14.
 */

public class User implements BaseUser {

    private Context context;
    private RequestQueue requestQueue;
    private int IS_FINISHED = 1;
    private Handler handler;

    public UserInfo userInfo;
    public Bms bms;
    public Bat bat;
    public Location location;

    private SharedPreData sharedPreData;
    private String filename = "user_info";



    public User( UserInfo userInfo, RequestQueue mQueue, Handler handler) {
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;
    }

    public User(Context context, UserInfo userInfo, RequestQueue mQueue, Handler handler) {
        this.context = context;
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;
    }

    public User(Context context, UserInfo userInfo, RequestQueue mQueue, Handler handler, SharedPreData sharedPreData) {
        this.context = context;
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;
        this.sharedPreData = sharedPreData;
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
                        message.what = IS_FINISHED;
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
                        message.what = IS_FINISHED;
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

    @Override
    public void saveInfo() {
        sharedPreData.save(filename, userInfo.name,userInfo.password,userInfo.token);
    }

    @Override
    public UserInfo getInfo() {
        return  sharedPreData.load(filename);
    }

    @Override
    public Bms getBms()
    {
        bms = new Bms();
        String GET_BMS = API.BMS_QUERY + "&"+"token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBmsQuery jsonReturn = fast_json.parseObject(response, JsonBmsQuery.class);

                        bms.setId(jsonReturn.getData().getId());
                        bms.setBms_id(jsonReturn.getData().getBms_id());
                        bms.setSoc(jsonReturn.getData().getSoc());
                        bms.setSoh(jsonReturn.getData().getSoh());
                        bms.setVol(jsonReturn.getData().getVol());
                        bms.setRes(jsonReturn.getData().getRes());
                        bms.setLongitude(jsonReturn.getData().getLongitude());
                        bms.setLatitude(jsonReturn.getData().getLatitude());
                        bms.setAltitde(jsonReturn.getData().getAltitude());
                        bms.setLocate_mode(jsonReturn.getData().getLocate_mode());
                        bms.setSatellite(jsonReturn.getData().getSatellite());
                        bms.setTemp(jsonReturn.getData().getTemp());
                        bms.setCurrent(jsonReturn.getData().getCurrent());
                        bms.setCharge(jsonReturn.getData().getCharge());

                        //得到BMS数据
                        Message message = Message.obtain(handler);
                        message.what = IS_FINISHED;
                        message.sendToTarget();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        requestQueue.add(stringRequest);
        return bms;
    }

    @Override
    public Bat getBat() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.BMS_QUERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBatQuery jsonReturn = fast_json.parseObject(response, JsonBatQuery.class);

                        bat.setId(jsonReturn.getData().getId());
                        bat.setBat_id(jsonReturn.getData().getBat_id());
                        bat.setSoc(jsonReturn.getData().getSoc());
                        bat.setSoh(jsonReturn.getData().getSoh());
                        bat.setVol(jsonReturn.getData().getVol());
                        bat.setRes(jsonReturn.getData().getRes());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        requestQueue.add(stringRequest);
        return bat;
    }

    public Location getLocation()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.BMS_QUERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBmsQuery jsonReturn = fast_json.parseObject(response, JsonBmsQuery.class);

                        location.setAltitude(jsonReturn.getData().getAltitude());
                        location.setLatitude(jsonReturn.getData().getLatitude());
                        location.setLongitude(jsonReturn.getData().getLongitude());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        requestQueue.add(stringRequest);
        return location;
    }
}
