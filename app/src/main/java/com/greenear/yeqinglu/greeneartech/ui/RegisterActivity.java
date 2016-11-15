package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;

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
    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        isRegister();
        initData();
        initView();
    }

    private void isRegister()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == IS_FINISHED)
                {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    private void initData()
    {
        userInfo = new UserInfo();
        context = this.getApplicationContext();
        mQueue = Volley.newRequestQueue(context);
        user = new User(context,userInfo,mQueue,handler);
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
                    cancel();
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

        if (!password.equals(password_ens))
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

    private void cancel()
    {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
