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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.others.FileService;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;

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

    private Context context;
    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        isLogin();
        initData();
        initView();
        loadUserInfo();
        saveUserInfo();

    }

    private void isLogin()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == IS_FINISHED)
                {
                    user.saveInfo();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    //登陆成功后销毁Activity
                    finish();
                }
            }
        };
    }

    private void initData()
    {
        context = this.getApplicationContext();
        user = new User(context, handler);
    }

    private void initView()
    {
        et_username = (EditText)findViewById(R.id.username_log);
        et_password = (EditText)findViewById(R.id.password_log);
        save_info = (CheckBox)findViewById(R.id.save_info);
        login_btn= (Button)findViewById(R.id.login);
        register_btn = (Button)findViewById(R.id.register_login);

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
            case R.id.register_login:
                register();
                break;
            default:
                break;
        }
    }

    public boolean login()
    {
        user.userInfo.name = et_username.getText().toString();
        user.userInfo.password = et_password.getText().toString();

        if(TextUtils.isEmpty(user.userInfo.name)||TextUtils.isEmpty(user.userInfo.password))
        {
            Toast.makeText(LoginActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
            return false;
        }

        else
        {
            //登陆
            user.login();
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
                saveUserInfo();
                Toast.makeText(LoginActivity.this, R.string.success,Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, R.string.fail,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveUserInfo()
    {
        //初始化文件服务
//        fileService = new FileService(this);

//        try {
//            Map<String,String >map = fileService.getUserInfo("private.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //用户信息File
//        sharedPreData.save(filename, userInfo.name,userInfo.password,userInfo.token);

        user.saveInfo();
    }

    public void register()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void loadUserInfo()
    {
//        userInfo = sharedPreData.load(filename);
        et_username.setText(user.userInfo.getName());
        et_password.setText(user.userInfo.getPassword());
    }


}


