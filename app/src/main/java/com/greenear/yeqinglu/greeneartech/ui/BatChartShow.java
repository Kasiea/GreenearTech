package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.Chart;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;

import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by yeqing.lu on 2016/11/17.
 */

public class BatChartShow extends Activity {

    public Context context;
    private ColumnChartData columnData;
    private ColumnChartView columnChart_Volt;
    private ColumnChartView columnChart_Soh;
    private ColumnChartView columnChart_Soc;
    private Chart chart;

    private Bms bms;
    private SharedPreData sharedPreData;
    private UserInfo userInfo;
    private RequestQueue requestQueue;
    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.bat_chart_show);

        updateChart();
        initView();
        initData();
        setChart();
        
    }

    public void updateChart()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                updateChart();
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
        bms = user.getBms();

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
}
