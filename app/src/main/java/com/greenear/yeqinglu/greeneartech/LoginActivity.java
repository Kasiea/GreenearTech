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
import com.greenear.yeqinglu.greeneartech.JsonData.JsonUserToken;
import com.greenear.yeqinglu.greeneartech.model.User;

import java.util.*;
import java.util.Map;

/**
 * Created by yeqing.lu on 2016/10/19.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText et_username;
    private EditText et_password;
    private CheckBox save_info;
    private Button login_btn;
    private Button register_btn;

    private FileService fileService;
    private SharedPreData sharedPreData;
    private String filename = "user_info";

    private User user;
    private UserInfo userInfo;
    private RequestQueue mQueue;
    private Context context;
    private boolean LOGIN_STATUS = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initData();
        initView();
        saveData();
    }

    private void initData()
    {
        userInfo = new UserInfo();
        context = this.getApplicationContext();
        mQueue = Volley.newRequestQueue(context);
        user = new User(context, userInfo, mQueue, LOGIN_STATUS);
        sharedPreData = new SharedPreData(context);
    }

    private void initView()
    {
        et_username = (EditText)findViewById(R.id.username_log);
        et_password = (EditText)findViewById(R.id.password_log);
        save_info = (CheckBox)findViewById(R.id.save_info);
        login_btn= (Button)findViewById(R.id.login);
        register_btn = (Button)findViewById(R.id.register);

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if(login())
                {
                    saveInfo();
                }
                break;
            case R.id.register:
                register();
                break;
            default:
                break;
        }
    }

    public boolean login()
    {
        userInfo.name = et_username.getText().toString();
        userInfo.password = et_password.getText().toString();

        if(TextUtils.isEmpty(userInfo.name)||TextUtils.isEmpty(userInfo.password))
        {
            Toast.makeText(LoginActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
            return false;
        }

        else
        {
            //登陆
            if(user.login())
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
                return true;
        }

    }

    public void saveInfo()
    {
        if (save_info.isChecked())
        {
            Toast.makeText(LoginActivity.this,R.string.remember_password,Toast.LENGTH_SHORT).show();
            try {
                //存储用户信息
                sharedPreData.save(filename, userInfo.name,userInfo.password,userInfo.token);
                Toast.makeText(LoginActivity.this, R.string.success,Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, R.string.fail,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveData()
    {
        //初始化文件服务
//        fileService = new FileService(this);

//        try {
//            Map<String,String >map = fileService.getUserInfo("private.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //用户信息File
        sharedPreData = new SharedPreData(this);

    }

    public void register()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}


