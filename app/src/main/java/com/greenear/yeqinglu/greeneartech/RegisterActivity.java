package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.greenear.yeqinglu.greeneartech.model.User;

import java.util.HashMap;

/**
 * Created by yeqing.lu on 2016/11/5.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_password_ens;
    private Button register;
    private Button cancel;

    private UserInfo userInfo;
    private RequestQueue mQueue;
    private Context context;
    private boolean REGISTER_STATUS;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initData();
        initView();
    }

    public void initData()
    {
        userInfo = new UserInfo();
        context = this.getApplicationContext();
        mQueue = Volley.newRequestQueue(context);
        REGISTER_STATUS = false;
        user = new User(context,userInfo,mQueue,REGISTER_STATUS);
    }
    public void initView()
    {
        et_username = (EditText)findViewById(R.id.username);
        et_email = (EditText)findViewById(R.id.email);
        et_password = (EditText)findViewById(R.id.password);
        et_password_ens = (EditText)findViewById(R.id.password_ens);
        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);

        register.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                if(check())
                {
                    register();
                }
                break;
            case R.id.cancel:
                break;
            default:
                break;
        }
    }

    public boolean check()
    {
        String username = et_username.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String password_ens = et_password_ens.getText().toString().trim();

        if(TextUtils.isEmpty(username)||TextUtils.isEmpty(email)||
                TextUtils.isEmpty(password)||TextUtils.isEmpty(password_ens))
        {
            Toast.makeText(RegisterActivity.this,"缺少必填信息！",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password!=password_ens)
        {
            Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void register()
    {
        userInfo.name = et_username.getText().toString();
        userInfo.password = et_password.getText().toString();
        user.register();
    }
}
