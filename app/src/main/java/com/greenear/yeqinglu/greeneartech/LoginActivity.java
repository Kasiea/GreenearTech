package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsBatteryVolt;
import java.util.HashMap;

/**
 * Created by yeqing.lu on 2016/10/19.
 */

public class LoginActivity extends Activity {

    private EditText et_username;
    private EditText et_password;
    private String token;
    private Button login;
    private CheckBox save_info;
    private FileService fileService;
    private SharedPreData sharedPreData;
    private UserInfo userInfo;


    public RequestQueue mQueue;
    public Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        context = this.getApplicationContext();
        mQueue = Volley.newRequestQueue(context);

        et_username = (EditText)findViewById(R.id.username);
        et_password = (EditText)findViewById(R.id.password);
        save_info = (CheckBox)findViewById(R.id.save_info);
        login = (Button)findViewById(R.id.login);

        //初始化文件服务
//        fileService = new FileService(this);

//        try {
//            Map<String,String >map = fileService.getUserInfo("private.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //用户信息File
        sharedPreData = new SharedPreData(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo.name = et_username.getText().toString().trim();
                userInfo.password = et_password.getText().toString().trim();

                if(TextUtils.isEmpty(userInfo.name)||TextUtils.isEmpty(userInfo.password))
                {
                    Toast.makeText(LoginActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
                }
                if (save_info.isChecked())
                {
                    Toast.makeText(LoginActivity.this,R.string.remember_password,Toast.LENGTH_SHORT).show();
                   try {
                       //存储用户信息
                       sharedPreData.save("user_info", userInfo.name,userInfo.password,userInfo.token);
                       Toast.makeText(LoginActivity.this, R.string.success,Toast.LENGTH_SHORT).show();
                   }
                   catch (Exception e){
                       e.printStackTrace();
                       Toast.makeText(LoginActivity.this, R.string.fail,Toast.LENGTH_SHORT).show();
                   }
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login_in() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.1.5/laravel-bms/public/api/data/bat-vol/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);
                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        BmsBatteryVolt bmsBatteryVolt = fast_json.parseObject(response, BmsBatteryVolt.class);
//                        bms_bat_vol_1.setText("第一节电池电压:"+"  "+ bmsBatteryVolt.getBms_bat_vol_1());
//                        bms_bat_vol_2.setText("第二节电池电压:"+"  "+ bmsBatteryVolt.getBms_bat_vol_2());
//                        bms_bat_vol_3.setText("第三节电池电压:"+"  "+ bmsBatteryVolt.getBms_bat_vol_3());
//                        bms_bat_vol_4.setText("第四节电池电压:"+"  "+ bmsBatteryVolt.getBms_bat_vol_4());
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                })
             {

             //匿名类重写方法，用来传输post数据
            @Override
            protected java.util.Map<String, String> getPostParams() throws AuthFailureError
            {
                java.util.Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return super.getPostParams();
            }};

        //将这个StringRequest对象添加到RequestQueue里
        mQueue.add(stringRequest);
    }
}


