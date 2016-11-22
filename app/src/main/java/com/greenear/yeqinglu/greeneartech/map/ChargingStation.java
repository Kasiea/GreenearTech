package com.greenear.yeqinglu.greeneartech.map;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.greenear.yeqinglu.greeneartech.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class ChargingStation {

    private BaiduMap baiduMap;

    //覆盖物相关
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerLy;


    public ChargingStation(BaiduMap baiduMap)
    {
        this.baiduMap = baiduMap;
    }

    private void initMarker()
    {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
//        mMarkerLy = (RelativeLayout)findViewById(R.id.id_marker_ly);
    }


    public void add()
    {
        addOverlays(Info.infos);
    }

    //添加覆盖物
    private void addOverlays(List<Info> infos)
    {
        initMarker();

        baiduMap.clear();
        LatLng latLng = null ;
        Marker marker = null;
        OverlayOptions options;
        for (Info info:infos)
        {
            //经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongtitude());
            //图标
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info",info);
            marker.setExtraInfo(arg0);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
    }

}
