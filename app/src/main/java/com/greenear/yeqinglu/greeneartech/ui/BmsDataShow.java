package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;


/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class BmsDataShow extends Activity {

    private TextView bms_id_tv;
    private TextView bms_soc_tv;
    private TextView bms_soh_tv;
    private TextView bms_vol_tv;
    private TextView bms_res_tv;
    private TextView bms_temp_tv;
    private TextView bms_current_tv;
    private TextView bms_charge_tv;

    private Context context;

    private User user;
    private UserInfo userInfo;
    private SharedPreData sharedPreData;
    private RequestQueue requestQueue;
    private Handler handler;
    private int IS_FINISHED = 1;

    private Bms bms;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data_show);

        updateUI();
        initView();
        initData();
        getBms();

    }

    public void initView()
    {
        bms_id_tv = (TextView)findViewById(R.id.bms_id);
        bms_soc_tv = (TextView)findViewById(R.id.bms_soc);
        bms_soh_tv = (TextView)findViewById(R.id.bms_soh);
        bms_vol_tv = (TextView)findViewById(R.id.bms_vol);
        bms_res_tv = (TextView)findViewById(R.id.bms_res);
        bms_temp_tv = (TextView)findViewById(R.id.bms_temp);
        bms_current_tv = (TextView)findViewById(R.id.bms_current);
        bms_charge_tv = (TextView)findViewById(R.id.bms_charge);
    }

    public void initData()
    {
        context = this.getApplicationContext();
        userInfo = new UserInfo();
        requestQueue = Volley.newRequestQueue(context);
        sharedPreData = new SharedPreData(context, userInfo);
        user = new User(userInfo, requestQueue, handler, sharedPreData);
//        userInfo = sharedPreData.load("user_info");
        userInfo = user.getInfo();
        bms = new Bms();
    }

    public void getBms()
    {
        bms = user.getBms("10");
    }

    public void updateUI()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == IS_FINISHED )
                {
                    updateBms();
                }
            }
        };
    }

    public void updateBms()
    {
        bms_id_tv.setText(bms.getId());
        bms_soc_tv.setText(bms.getSoc());
        bms_soh_tv.setText(bms.getSoh());
        bms_vol_tv.setText(bms.getVol());
        bms_res_tv.setText(bms.getRes());
        bms_temp_tv.setText(bms.getTemp());
        bms_current_tv.setText(bms.getCurrent());
        bms_charge_tv.setText(bms.getCharge());
    }




}
