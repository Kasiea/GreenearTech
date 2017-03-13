package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.net.NetworrkChangeReceiver;

/**
 * Created by yeqing.lu on 2017/3/1.
 */

public class WelcomeActivity extends Activity {

    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;
    private boolean isLogined = false;

    private IntentFilter intentFilter;
    private NetworrkChangeReceiver networrkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                checkUserInfo();

                //网络判断相关
                intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                networrkChangeReceiver = new NetworrkChangeReceiver();
                registerReceiver(networrkChangeReceiver, intentFilter);


                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);

                 if (!isLogined) {
                setContentView(R.layout.activity_welcome);
                Handler handler = new Handler();
                //当计时结束,跳转至主界面
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        WelcomeActivity.this.finish();
                    }
                }, 3000);
            }
    }

    private void isLogin()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == IS_FINISHED)
                {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    //登录成功后销毁Activity
                    finish();
                }
            }
        };
    }

    private void initData()
    {
        user = new User(handler);
    }

    private void checkUserInfo()
    {
        isLogin();
        initData();
        if (!"".equals(user.userInfo.name))
        {
            user.login();
            isLogined = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networrkChangeReceiver);
    }
}
