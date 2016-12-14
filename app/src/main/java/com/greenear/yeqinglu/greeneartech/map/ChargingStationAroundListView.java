package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.greenear.yeqinglu.greeneartech.R;
import java.util.ArrayList;


/**
 * Created by yeqing.lu on 2016/12/13.
 */

public class ChargingStationAroundListView extends FrameLayout {

    private Context context;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;


    public ChargingStationAroundListView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //获取到LayoutInflater的实例,加载布局
        LayoutInflater.from(context).inflate(R.layout.charging_station_around_listview, this);

        listView = (ListView)findViewById(R.id.chargin_station_around_listview);

    }


    //配置ListView数据
    public void setArrayList(ArrayList<String> arrayList) {
        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

}
