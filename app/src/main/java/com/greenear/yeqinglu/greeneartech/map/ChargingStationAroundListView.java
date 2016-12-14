package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.greenear.yeqinglu.greeneartech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeqing.lu on 2016/12/13.
 */

public class ChargingStationAroundListView extends FrameLayout {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;


    private ArrayList<String> arrayList;
    private Context context;

    public ChargingStationAroundListView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //获取到LayoutInflater的实例,加载布局
        LayoutInflater.from(context).inflate(R.layout.charging_station_around_listview, this);

        listView = (ListView)findViewById(R.id.chargin_station_around_listview);

    }


    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
