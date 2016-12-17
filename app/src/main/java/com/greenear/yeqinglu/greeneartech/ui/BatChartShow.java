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
import com.greenear.yeqinglu.greeneartech.model.MyApplication;
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

    private ColumnChartView columnChart_Volt;
    private ColumnChartView columnChart_Soh;
    private ColumnChartView columnChart_Soc;
    private Chart chart;

    private RequestQueue requestQueue;
    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;


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
        requestQueue = Volley.newRequestQueue(MyApplication.getContext());
        user = new User(handler);
        user.getBms_Bat();
    }

    public void setChart()
    {
        chart = new Chart(user.bms, columnChart_Volt);
        chart.showBatVolt();
        chart.setColumnChartView(columnChart_Soh);
        chart.showBatSoh();
        chart.setColumnChartView(columnChart_Soc);
        chart.showBatSoc();
    }

}
