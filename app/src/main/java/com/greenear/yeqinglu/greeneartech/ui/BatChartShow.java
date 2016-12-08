package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.Bat;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.Chart;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * Created by yeqing.lu on 2016/11/17.
 */

public class BatChartShow extends Activity {

    public Context context;
    private ColumnChartView columnChart_Volt;
    private ColumnChartView columnChart_Soh;
    private ColumnChartView columnChart_Soc;
    private Chart chart;

    private Bms bms;
    private Bat bat1;
    private Bat bat2;
    private Bat bat3;
    private Bat bat4;

    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;

    private SharedPreData sharedPreData;
    private UserInfo userInfo;
    private RequestQueue requestQueue;
    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;

    private ExecutorService executorService = Executors.newFixedThreadPool(4);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bat_chart_show);

        updateChart();
        initView();
        initData();


    }

    public void updateChart()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == IS_FINISHED) {
                    if(setBmsBat(msg.arg1)== 4)
//                    bms.getBats().add(0, bat1);
//                    bms.getBats().add(1, bat2);
//                    bms.getBats().add(2, bat3);
//                    bms.getBats().add(3, bat4);
                    setChart();
                }
            }
        };
    }

    public void initView()
    {
        columnChart_Volt = (ColumnChartView)findViewById(R.id.volt_chart);
        columnChart_Soc = (ColumnChartView)findViewById(R.id.soc_chart);
        columnChart_Soh = (ColumnChartView)findViewById(R.id.soh_chart);
    }

    public void initData()
    {
        context = this.getApplicationContext();
        bms = new Bms();
        userInfo = new UserInfo();
        requestQueue = Volley.newRequestQueue(context);
        sharedPreData = new SharedPreData(context, userInfo);
        user = new User(userInfo, requestQueue, handler, sharedPreData);
        user.getInfo();
//        bms = user.getBms();
//        bms =user.getBms_Bat();

        bat1 = user.getBat("15", 0);
        SystemClock.sleep(2000);
        bat2 = user.getBat("13", 1);
        SystemClock.sleep(2000);
        bat3 = user.getBat("4", 2);
        SystemClock.sleep(2000);
        bat4 = user.getBat("1", 3);



    }

    public void setChart()
    {
        chart = new Chart(bms, columnChart_Volt);
        chart.showBatVolt();
        chart.setColumnChartView(columnChart_Soh);
        chart.showBatSoh();
        chart.setColumnChartView(columnChart_Soc);
        chart.showBatSoc();
    }

    public int setBmsBat(int num)
    {
        if(num == 0) {
            bms.getBats().add(0, bat1);
            i = 1;
        }
        if(num == 1) {
            bms.getBats().add(1, bat2);
            j = i;
        }

        if(num == 2) {
            bms.getBats().add(2, bat3);
            k = 1;
        }
        if(num == 3) {
            bms.getBats().add(3, bat4);
            m = 1;
        }
        return i+j+k+m;
    }
}
