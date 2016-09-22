package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.net.URL;

/**
 * Created by yeqing.lu on 2016/9/22.
 */

public class BmsData extends Activity {

    public Context context;
    public RequestQueue mQueue;
    public URL url;

    private TextView bms_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data);
        context = this.getApplicationContext();

        bms_data = (TextView)findViewById(R.id.bms_data);

        //获取到一个RequestQueue对象
        /*RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，
        然后按照一定的算法并发地发出这些请求。RequestQueue内部的设
        计就是非常合适高并发的，因此我们不必为每一次HTTP请求都创建
        一个RequestQueue对象，这是非常浪费资源的，基本上在每一个需
        要和网络交互的Activity中创建一个RequestQueue对象就足够了。*/
        mQueue = Volley.newRequestQueue(context);

//        gson_http();
        getHttp();
    }

    public void gson_http()
    {
        GsonRequest<Battery> gsonRequest = new GsonRequest<Battery>(Request.Method.GET,
                "http://api.qljiang.com/student/1", Battery.class,
                new Response.Listener<Battery>() {
                    @Override
                    public void onResponse(Battery battery) {
                        BatteryInfo batteryInfo = battery.getBatteryInfo();
                        bms_data.setText(batteryInfo.getName()+batteryInfo.getAge()+batteryInfo.getBirthday()+batteryInfo.getSex());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(gsonRequest);

    }

    public void getHttp() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://api.qljiang.com/student/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        bms_data.setText(response);

//                        Gson gson = new Gson();//new一个Gson对象
//                        BatteryInfo batteryInfo = new BatteryInfo();
//                        batteryInfo = gson.fromJson(response, BatteryInfo.class);
//                        bms_data.setText(batteryInfo.getName());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        //将这个StringRequest对象添加到RequestQueue里
        mQueue.add(stringRequest);
    }
}
