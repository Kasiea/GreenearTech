package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.User;

/**
 * Created by yeqing.lu on 2016/12/13.
 */

public class UserInfoActivity extends Activity {

    private TextView user_info;

    private Context context;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        initView();
        initData();
    }

    public void initView()
    {
        user_info = (TextView)findViewById(R.id.user_id);
    }

    public void initData()
    {
        context = getApplicationContext();
        user = new User(context);
        user_info.setText(user.userInfo.getName());
    }
}
