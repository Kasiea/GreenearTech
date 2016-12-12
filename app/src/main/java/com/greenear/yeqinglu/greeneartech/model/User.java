package com.greenear.yeqinglu.greeneartech.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBatInfo;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBatQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBms;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBmsBatData;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBmsBatQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBmsInfo;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonBmsQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonStationQuery;
import com.greenear.yeqinglu.greeneartech.JsonData.JsonUserToken;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.interf.BaseUser;
import com.greenear.yeqinglu.greeneartech.net.API;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;
import com.greenear.yeqinglu.greeneartech.ui.LoginActivity;
import com.greenear.yeqinglu.greeneartech.ui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.baidu.location.h.a.i;

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


    public User(Context context, UserInfo userInfo, RequestQueue mQueue, Handler handler) {
        this.context = context;
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;

        bms = new Bms();
        bat = new Bat();
    }

    public User( UserInfo userInfo, RequestQueue mQueue, Handler handler, SharedPreData sharedPreData) {
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;
        this.sharedPreData = sharedPreData;

        bms = new Bms();
        bat = new Bat();
    }

    public User(Context context, UserInfo userInfo, RequestQueue mQueue, Handler handler, SharedPreData sharedPreData) {
        this.context = context;
        this.userInfo = userInfo;
        this.requestQueue = mQueue;
        this.handler = handler;
        this.sharedPreData = sharedPreData;

        bms = new Bms();
        bat = new Bat();
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
    public Bms getBms(String bms_id)
    {
        String GET_BMS = API.BMS_QUERY + "bms_id=" + bms_id + "&cnt=1" + "&token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBmsQuery jsonReturn = fast_json.parseObject(response, JsonBmsQuery.class);

                        bms.setId(jsonReturn.getData().get(0).getId());
                        bms.setBms_id(jsonReturn.getData().get(0).getBms_id());
                        bms.setSoc(jsonReturn.getData().get(0).getSoc());
                        bms.setSoh(jsonReturn.getData().get(0).getSoh());
                        bms.setVol(jsonReturn.getData().get(0).getVol());
                        bms.setRes(jsonReturn.getData().get(0).getRes());
                        bms.setLongitude(jsonReturn.getData().get(0).getLongitude());
                        bms.setLatitude(jsonReturn.getData().get(0).getLatitude());
                        bms.setAltitde(jsonReturn.getData().get(0).getAltitude());
                        bms.setLocate_mode(jsonReturn.getData().get(0).getLocate_mode());
                        bms.setSatellite(jsonReturn.getData().get(0).getSatellite());
                        bms.setTemp(jsonReturn.getData().get(0).getTemp());
                        bms.setCurrent(jsonReturn.getData().get(0).getCurrent());
                        bms.setCharge(jsonReturn.getData().get(0).getCharge());

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
    public Bat getBat(String bat_id, final int num) {
        final Bat bms_bat = new Bat();
        String GET_BAT = API.BAT_QUERY + "bat_id="+ bat_id + "&cnt=1" + "&token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBatQuery jsonReturn = fast_json.parseObject(response, JsonBatQuery.class);

                        bms_bat.setId(jsonReturn.getData().get(0).getId());
                        bms_bat.setBat_id(jsonReturn.getData().get(0).getBat_id());
                        bms_bat.setSoc(jsonReturn.getData().get(0).getSoc());
                        bms_bat.setSoh(jsonReturn.getData().get(0).getSoh());
                        bms_bat.setVol(jsonReturn.getData().get(0).getVol());
                        bms_bat.setRes(jsonReturn.getData().get(0).getRes());

                        //得到BAT数据
                        Message message = Message.obtain(handler);
                        message.what = IS_FINISHED;
                        message.arg1 = num;
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
        return bms_bat;
    }

    public Bms getBms_Bat() {
        String GET_BAT = API.BMS_BAT_QUERY + "&"+"token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();
                        JsonBmsBatData jsonReturn = fast_json.parseObject(response, JsonBmsBatData.class);


                        int size = jsonReturn.getData().size();
                        for(int i= 0; i<size; i++)
                        {
                            Bat bat = new Bat();
                            bat.setId(jsonReturn.getData().get(i).getId());
                            bat.setBat_id(jsonReturn.getData().get(i).getBat_id());
                            bat.setSoc(jsonReturn.getData().get(i).getSoc());
                            bat.setSoh(jsonReturn.getData().get(i).getSoh());
                            bat.setVol(jsonReturn.getData().get(i).getVol());
                            bat.setRes(jsonReturn.getData().get(i).getRes());

                            bms.getBats().add(i, bat);
                        }

                        //得到BMS_BAT数据
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

    public Location getLocation()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, API.BMS_QUERY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBmsQuery jsonReturn = fast_json.parseObject(response, JsonBmsQuery.class);

                        location.setAltitude(jsonReturn.getData().get(0).getAltitude());
                        location.setLatitude(jsonReturn.getData().get(0).getLatitude());
                        location.setLongitude(jsonReturn.getData().get(0).getLongitude());
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

    public Bms getBmsInfo()
    {
        String GET_BMS = API.BMS_INFO + "&"+"token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBmsInfo jsonReturn = fast_json.parseObject(response, JsonBmsInfo.class);

                        bms.setBms_id(jsonReturn.getData().get(0).getId());

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

    public Bms getBatInfo(String bms_id)
    {
        String GET_BMS = API.BAT_INFO + "bms_id=" + bms_id + "&token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonBatInfo jsonReturn = fast_json.parseObject(response, JsonBatInfo.class);

                        ArrayList<Bat> bats= new ArrayList<Bat>();

                        for(int i = 0; i<4; i++) {
                            Bat bat = new Bat();
                            bat.setBat_id(jsonReturn.getData().get(i).getId());
                            bats.add(0, bat);
                        }
                        bms.setBats(bats);

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

    public CharingStationAround getChargingStation(String longitude, String latitude)
    {
        final CharingStationAround charingStationAround = new CharingStationAround();

        String GET_BMS = API.BAT_INFO + "lon=" + longitude + "lat=" + latitude + "radius=5000" + "&token=" + userInfo.getToken();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_BMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        JsonStationQuery jsonReturn = fast_json.parseObject(response, JsonStationQuery.class);

                        int size = jsonReturn.getData().size();
                        for (int i = 0; i < size; i ++ )
                        {
                            charingStationAround.setId(jsonReturn.getData().get(i).getId());
                            charingStationAround.setLongitude(jsonReturn.getData().get(i).getLongitude());
                            charingStationAround.setLatitude(jsonReturn.getData().get(i).getLatitude());
                            charingStationAround.setTotal(jsonReturn.getData().get(i).getTotal());
                            charingStationAround.setAvailable(jsonReturn.getData().get(i).getAvailable());
                        }

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
        return charingStationAround;
    }
}
