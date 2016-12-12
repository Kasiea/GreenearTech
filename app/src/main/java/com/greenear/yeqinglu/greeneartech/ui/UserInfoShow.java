package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;

/**
 * Created by yeqing.lu on 2016/12/6.
 */

public class UserInfoShow extends Activity {

    private TextView user_bms_id;
    private TextView user_bat_id_1;
    private TextView user_bat_id_2;
    private TextView user_bat_id_3;
    private TextView user_bat_id_4;

    private User user;
    private UserInfo userInfo;
    private Bms bms;

    private Context context;
    private RequestQueue requestQueue;
    private Handler handler;
    private int IS_FINISHED = 1;

    private SharedPreData sharedPreData;
    private String filename = "user_battery_info";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_show);

        updateUI();
        initView();
        initData();
        getUserInfo();
    }

    public void initView()
    {
        user_bms_id = (TextView)findViewById(R.id.user_bms_id);
        user_bat_id_1 = (TextView)findViewById(R.id.user_bat_id_1);
        user_bat_id_2 = (TextView)findViewById(R.id.user_bat_id_2);
        user_bat_id_3 = (TextView)findViewById(R.id.user_bat_id_3);
        user_bat_id_4 = (TextView)findViewById(R.id.user_bat_id_4);
    }

    public void initData()
    {
        context = this.getApplicationContext();
        userInfo = new UserInfo();
        requestQueue = Volley.newRequestQueue(context);
        sharedPreData = new SharedPreData(context, userInfo);
        user = new User(userInfo, requestQueue, handler, sharedPreData);
        userInfo = user.getInfo();
        bms = new Bms();
    }

    public void getUserInfo()
    {
        user.getBmsInfo();
        bms = user.getBatInfo("10");
    }

    public void updateUI()
    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == IS_FINISHED) {
                    if (updateUserInfo()) {
                        saveBatteryInfo();
                    }
                }
            }
        };
    }

    public boolean updateUserInfo()
    {
        user_bms_id.setText(bms.getBms_id());
        user_bat_id_1.setText(bms.getBats().get(0).getBat_id());
        user_bat_id_2.setText(bms.getBats().get(1).getBat_id());
        user_bat_id_3.setText(bms.getBats().get(2).getBat_id());
        user_bat_id_4.setText(bms.getBats().get(3).getBat_id());

        return true;
    }

    public void saveBatteryInfo()
    {
        sharedPreData.saveBatteryInfo(filename, bms.getBms_id(),bms.getBats().get(0).getBat_id(),
                bms.getBats().get(1).getBat_id(), bms.getBats().get(2).getBat_id(), bms.getBats().get(3).getBat_id());
    }

}
