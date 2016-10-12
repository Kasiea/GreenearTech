package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsBatteryVolt;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsGpsQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsSocQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsSohQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsWorkStatus;

import java.net.URL;

/**
 * Created by yeqing.lu on 2016/9/22.
 */

public class BmsData extends Activity {

    public Context context;
    public RequestQueue mQueue;
    public URL url;

    private TextView bms_data;
    private TextView bms_info_id;
    private TextView bms_env_temp;
    private TextView bms_bat_curr;
    private TextView bms_bat_dc_status;
    private TextView bms_time;
    private TextView bms_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data);
        context = this.getApplicationContext();

        bms_data = (TextView)findViewById(R.id.bms_data);

        bms_info_id = (TextView)findViewById(R.id.bms_info_id);
        bms_env_temp = (TextView)findViewById(R.id.bms_env_temp);
        bms_bat_curr = (TextView)findViewById(R.id.bms_bat_curr);
        bms_bat_dc_status = (TextView)findViewById(R.id.bms_bat_dc_status);
        bms_time = (TextView)findViewById(R.id.bms_time);
        bms_id = (TextView)findViewById(R.id.bms_id);




        getBmsWorkStatus();
//        getBmsGps();
//        getBmsBatteryVolt();
//        getBmsSoc();
//        getBmsSoh();
    }



    public void getBmsWorkStatus() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bms-status/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        BmsWorkStatus bmsWorkStatus = fast_json.parseObject(response, BmsWorkStatus.class);
                        bms_info_id.setText("bms_info_id:"+"  "+bmsWorkStatus.getBms_info_id());
                        bms_env_temp.setText("环境温度:"+"  "+bmsWorkStatus.getBms_env_temp());
                        bms_bat_curr.setText("电池电流:"+"  "+bmsWorkStatus.getBms_bat_curr());
                        bms_bat_dc_status.setText("电池充放电状态:"+"  "+bmsWorkStatus.getBms_bat_dc_status());
                        bms_time.setText("BMS时间信息:"+"  "+bmsWorkStatus.getBms_time());
                        bms_id.setText("BMS_ID:"+"  "+bmsWorkStatus.getBms_id());

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

    public void getBmsBatteryVolt() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bat-vol/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        BmsBatteryVolt bmsBatteryVolt = fast_json.parseObject(response, BmsBatteryVolt.class);
                        bms_data.setText(bmsBatteryVolt.getBms_bat_vol_1() + "" +bmsBatteryVolt.getBms_bat_vol_2());

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

    public void getBmsSoc() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bat-soc/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        BmsSocQuery bmsSocQuery = fast_json.parseObject(response, BmsSocQuery.class);
                        bms_data.setText(bmsSocQuery.getBms_bat_soc_1() + "" +bmsSocQuery.getBms_bat_soc_2());

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


    public void getBmsSoh() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bat-soh/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        BmsSohQuery bmsSohQuery = fast_json.parseObject(response, BmsSohQuery.class);
                        bms_data.setText(bmsSohQuery.getBms_bat_soh_1() + "" +bmsSohQuery.getBms_bat_soh_2());

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


    public void getBmsGps() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bms-gps/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个Gson对象
                        BmsGpsQuery bmsGpsQuery = fast_json.parseObject(response, BmsGpsQuery.class);
                        bms_data.setText(bmsGpsQuery.getBms_gps_longitude() + "" +bmsGpsQuery.getBms_gps_altitude());

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
