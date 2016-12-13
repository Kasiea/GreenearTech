package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.CharingStationAround;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class ChargingStation {

    private BaiduMap baiduMap;
    private Context context;

    //覆盖物相关
    private BitmapDescriptor mMarker;
    private ChargingStationInfo chargingStationInfo;


    public ChargingStation(BaiduMap baiduMap, ChargingStationInfo chargingStationInfo, Context context)
    {
        this.baiduMap = baiduMap;
        this.chargingStationInfo = chargingStationInfo;
        this.context = context;
    }

    private void initMarker()
    {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
    }


    //添加覆盖物
    public void add()
    {
        addOverlays(Info.infos);
    }

    public void addCharingStationAround(ArrayList<CharingStationAround> charingStationArounds)
    {
        initMarker();

        baiduMap.clear();
        LatLng latLng = null ;
        Marker marker = null;
        OverlayOptions options;

        for (CharingStationAround charingStationAround :charingStationArounds) {
            //经纬度
            latLng = new LatLng(new Float(charingStationAround.getLatitude()), new Float(charingStationAround.getLongitude()));
            //图标
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("charingStationAround",charingStationAround);
            marker.setExtraInfo(arg0);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);
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

    //覆盖物信息窗口显示
//    public void setChangingStationInfoShow()
//    {
//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//
//                Bundle extraInfo = marker.getExtraInfo();
//                Info info = (Info)extraInfo.getSerializable("info");
//
//                chargingStationInfo.info_img.setImageResource(info.getImagId());
//                chargingStationInfo.info_dis.setText(info.getDistance());
//                chargingStationInfo.info_name.setText(info.getName());
//                chargingStationInfo.info_zan.setText(info.getZan()+"");
//
//                InfoWindow infoWindow;
//                TextView tv = new TextView(context);
//                tv.setBackground(context.getResources().getDrawable(R.drawable.tip));
//                tv.setPadding(30,20,30,50);
//                tv.setText(info.getName());
//
//                final LatLng latLng = marker.getPosition();
//                Point p = baiduMap.getProjection().toScreenLocation(latLng);
//                p.y -= 47;
//                LatLng ll = baiduMap.getProjection().fromScreenLocation(p);
//
//                infoWindow = new InfoWindow(tv, ll,1);
//                baiduMap.showInfoWindow(infoWindow);
//
//                chargingStationInfo.setVisibility(View.VISIBLE);
//                chargingStationInfo.charger_info.setVisibility(View.VISIBLE);
//                return true;
//
//            }
//        });

        //显示周围充电桩信息
    public void setChangingStationInfoShow()
    {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Bundle extraInfo = marker.getExtraInfo();
                CharingStationAround charingStationAround = (CharingStationAround) extraInfo.getSerializable("charingStationAround");

//                显示副窗口具体信息
                chargingStationInfo.info_img.setImageResource(R.drawable.charging_station);
                chargingStationInfo.info_dis.setText("5000m以内");
                chargingStationInfo.info_name.setText("充电桩" + "ID = " + charingStationAround.getId() );
                chargingStationInfo.info_zan.setText("100");

//                显示气泡信息
                InfoWindow infoWindow;
                TextView tv = new TextView(context);
                tv.setBackground(context.getResources().getDrawable(R.drawable.tip));
                tv.setPadding(30,20,30,50);
                tv.setText("ID=" + charingStationAround.getId());

                final LatLng latLng = marker.getPosition();
                Point p = baiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                LatLng ll = baiduMap.getProjection().fromScreenLocation(p);

                infoWindow = new InfoWindow(tv, ll,1);
                baiduMap.showInfoWindow(infoWindow);

                chargingStationInfo.setVisibility(View.VISIBLE);
                chargingStationInfo.charger_info.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

}
