package com.greenear.yeqinglu.greeneartech;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button bms_data;
    private Button map;
    private Button battery_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data);

        bms_data = (Button)findViewById(R.id.bms_data);
        map = (Button)findViewById(R.id.map);
        battery_status = (Button)findViewById(R.id.battery_status);

    }


}
