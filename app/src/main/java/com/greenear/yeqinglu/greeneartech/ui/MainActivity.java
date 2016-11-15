package com.greenear.yeqinglu.greeneartech.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.greenear.yeqinglu.greeneartech.map.Map;
import com.greenear.yeqinglu.greeneartech.R;

public class MainActivity extends AppCompatActivity {

    private Button bms_data;
    private Button map;
    private Button battery_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bms_data = (Button)findViewById(R.id.bms_data);
        map = (Button)findViewById(R.id.map);
        battery_status = (Button)findViewById(R.id.battery_status);

        bms_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BmsData.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Map.class);
                startActivity(intent);
            }
        });

        battery_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BatteryStatus.class);
                startActivity(intent);
            }
        });

    }


}
