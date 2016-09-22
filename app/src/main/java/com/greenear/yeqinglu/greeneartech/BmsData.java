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
import com.android.volley.toolbox.Volley;

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
    }

    public String gson_http()
    {
        GsonRequest<Battery> gsonRequest = new GsonRequest<Battery>(Request.Method.GET,
                "http://api.qljiang.com/student/1", Battery.class,
                new Response.Listener<Battery>() {
                    @Override
                    public void onResponse(Battery battery) {
                        BatteryInfo batteryInfo = battery.getBatteryInfo();
//                        Log.d("TAG", "city is " + weatherInfo.getCity());
//                        Log.d("TAG", "temp is " + weatherInfo.getTemp());
//                        Log.d("TAG", "time is " + weatherInfo.getTime());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        mQueue.add(gsonRequest);

        //返回bms数据
        return null;
    }
}
