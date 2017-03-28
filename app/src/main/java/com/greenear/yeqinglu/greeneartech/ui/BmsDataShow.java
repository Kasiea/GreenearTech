package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.map.MapActivity;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;
import com.shinelw.library.ColorArcProgressBar;


/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class BmsDataShow extends AppCompatActivity {

    private TextView bms_id_tv;
    private TextView bms_soc_tv;
    private TextView bms_soh_tv;
    private TextView bms_vol_tv;
    private TextView bms_res_tv;
    private TextView bms_temp_tv;
    private TextView bms_current_tv;
    private TextView bms_charge_tv;

    private ColorArcProgressBar bms_soc_bar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private User user;

    private Handler handler;
    private int IS_FINISHED = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data_show);

        updateUI();
        initView();
        initData();
        getBms();
        refresh();

    }

    public void initView()
    {
//        bms_id_tv = (TextView)findViewById(R.id.bms_id);
//        bms_soc_tv = (TextView)findViewById(R.id.bms_soc);
        bms_soh_tv = (TextView)findViewById(R.id.bms_soh);
        bms_vol_tv = (TextView)findViewById(R.id.bms_vol);
        bms_res_tv = (TextView)findViewById(R.id.bms_res);
        bms_temp_tv = (TextView)findViewById(R.id.bms_temp);
        bms_current_tv = (TextView)findViewById(R.id.bms_current);
        bms_charge_tv = (TextView)findViewById(R.id.bms_charge);

        bms_soc_bar = (ColorArcProgressBar) findViewById(R.id.bms_bar);

        swipeRefreshLayout =  (SwipeRefreshLayout)findViewById(R.id.bms_swipe_refresh);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add NavigationView
        drawerLayout = (DrawerLayout)findViewById(R.id.bms_drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.bms_nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }

        navigationView_config();
    }

    public void initData()
    {
        user = new User(handler);
    }

    public void getBms()
    {
         user.getBms("10");
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
//        bms_id_tv.setText(user.bms.getId());
//        bms_soc_tv.setText(user.bms.getSoc());
        bms_soh_tv.setText(user.bms.getSoh());
        bms_vol_tv.setText(user.bms.getVol());
        bms_res_tv.setText(user.bms.getRes());
        bms_temp_tv.setText(user.bms.getTemp());
        bms_current_tv.setText(user.bms.getCurrent());
        bms_charge_tv.setText(user.bms.getCharge());

        bms_soc_bar.setCurrentValues(Float.parseFloat(user.bms.getSoc()));
    }

    //下拉刷新数据
    public void refresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                user.getBms("10");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void navigationView_config()
    {
        navigationView.setCheckedItem(R.id.profile);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.profile:
                        Intent intent1 = new Intent(BmsDataShow.this, UserInfoActivity.class);
                        startActivity(intent1);
                        Toast.makeText(BmsDataShow.this, "I am here!", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.graph:
                        Intent intent2 = new Intent(BmsDataShow.this, BatChartShow.class);
                        startActivity(intent2);
                        break;

                    case R.id.map:
                        Intent intent3 = new Intent(BmsDataShow.this, MapActivity.class);
                        startActivity(intent3);
                        break;

                    default:
                }
//                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }




}
