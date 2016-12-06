package com.greenear.yeqinglu.greeneartech.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.map.Map;
import com.greenear.yeqinglu.greeneartech.map.MapActivity;


public class MainActivity extends AppCompatActivity {

    private Button bms_data;
    private Button map;
    private Button battery_status;
    private Button user_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        bms_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BmsDataShow.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        battery_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BatChartShow.class);
                startActivity(intent);
            }
        });

        user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserInfoShow.class);
                startActivity(intent);
            }
        });

    }

    public void initView()
    {
        bms_data = (Button)findViewById(R.id.bms_data);
        map = (Button)findViewById(R.id.map);
        battery_status = (Button)findViewById(R.id.battery_status);
        user_info = (Button)findViewById(R.id.user_info);
    }


}
