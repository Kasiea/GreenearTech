package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.User;

/**
 * Created by yeqing.lu on 2016/12/13.
 */

public class UserInfoActivity extends Activity {

    private User user;

    private TextView user_info;
    private Button reLogin_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        initView();
        initData();
        reLogin();
    }

    public void initView()
    {
        user_info = (TextView)findViewById(R.id.user_id);
        reLogin_btn = (Button)findViewById(R.id.re_login);
    }

    public void initData()
    {
        user = new User();
        user_info.setText(user.userInfo.getName());
    }

    public void reLogin()
    {
        reLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
