package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.model.LatLng;
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
    public void setDetailListView(final ArrayList<String> arrayList, final ChargingStationInfo chargingStationInfo) {
        arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        //点击单项响应事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,"This is to show detail information", Toast.LENGTH_SHORT).show();

                //显示具体信息窗口
                chargingStationInfo.info_img.setImageResource(R.drawable.charging_station);
                chargingStationInfo.info_dis.setText("5000m以内");
                chargingStationInfo.info_name.setText(arrayList.get(position) );
                chargingStationInfo.info_zan.setText("100");

                chargingStationInfo.setVisibility(View.VISIBLE);
                chargingStationInfo.charger_info.setVisibility(View.VISIBLE);

            }
        });
    }

}
