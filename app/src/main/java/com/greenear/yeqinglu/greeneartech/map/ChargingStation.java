package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

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
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.CharingStationAround;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class ChargingStation {

    private Context context;
    private BaiduMap baiduMap;

    //覆盖物相关
    private BitmapDescriptor mMarker;
    private BitmapDescriptor mClickedMarker;
    private ChargingStationInfo chargingStationInfo;//充电桩具体信息
    private ChargingStationAroundListView chargingStationAroundListView;//附近充电桩列表

    //目的地位置数据存储
    public SharedPreData sharedPreData;

    //存储上一次点击的marker图标
    public Marker clicked_marker = null;


    public ChargingStation(BaiduMap baiduMap,  Context context, ChargingStationInfo chargingStationInfo, ChargingStationAroundListView
                           chargingStationAroundListView)
    {
        this.baiduMap = baiduMap;
        this.chargingStationInfo = chargingStationInfo;
        this.context = context;
        this.chargingStationAroundListView = chargingStationAroundListView;
    }


    //覆盖物图标设置
    private void initMarker()
    {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        mClickedMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker_clicked);
    }


    //添加覆盖物
    public void add()
    {
        addOverlays(Info.infos);
    }


    //添加周围充电桩Marker
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

        //将地图显示在最后一个marker的位置
//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
//        baiduMap.setMapStatus(msu);
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


        //显示周围充电桩具体信息：图片，距离等
    public void setChangingStationInfoShow()
    {
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //复原上一次点击的marker图标
                if (clicked_marker != null)
                {
                    clicked_marker.setIcon(mMarker);
                }

                Bundle extraInfo = marker.getExtraInfo();
                CharingStationAround charingStationAround = (CharingStationAround) extraInfo.getSerializable("charingStationAround");

                //经纬度地址反编码地理位置
                showStationAdress(Float.parseFloat(charingStationAround.getLatitude()), Float.parseFloat(charingStationAround.getLongitude()));

//                显示副窗口具体信息
                chargingStationInfo.info_img.setImageResource(R.drawable.charging_station);
                chargingStationInfo.info_dis.setText("5000m以内");
                chargingStationInfo.info_name.setText("充电桩" + "ID = " + charingStationAround.getId());
                chargingStationInfo.total_cg_num.setText(charingStationAround.getTotal());
                chargingStationInfo.avialable_cg_num.setText(charingStationAround.getAvailable());

//                显示气泡信息
                InfoWindow infoWindow;
                TextView tv = new TextView(context);
                tv.setBackground(context.getResources().getDrawable(R.drawable.tip));
                tv.setPadding(30,20,30,50);
                tv.setText("ID=" + charingStationAround.getId());

                //改变点击后的图标
                marker.setIcon(mClickedMarker);
                //存储上一次点击的marker图标
                clicked_marker = marker;

                //目的地位置数据存储
                sharedPreData = new SharedPreData();
                sharedPreData.saveDestPosition(charingStationAround.getLatitude(), charingStationAround
                .getLongitude());

                final LatLng latLng = marker.getPosition();
                Point p = baiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                LatLng ll = baiduMap.getProjection().fromScreenLocation(p);

                infoWindow = new InfoWindow(tv, ll,1);
                baiduMap.showInfoWindow(infoWindow);

                //设置动画效果
                AlphaAnimation aAnima = new AlphaAnimation(0.0f, 1.0f);//从全不透明变为全透明
                aAnima.setDuration(500);
                chargingStationInfo.setAnimation(aAnima);

                //设置动画效果
//                TranslateAnimation tAnima = new TranslateAnimation(0,-800,0,0);
//                tAnima.setDuration(600);
//                chargingStationInfo.setAnimation(tAnima);

                chargingStationInfo.setVisibility(View.VISIBLE);
                chargingStationInfo.charger_info.setVisibility(View.VISIBLE);

                return true;
            }
        });
    }


    //显示周围充电桩列表
    public void addChargingStationListview(ArrayList<CharingStationAround> charingStationArounds)
    {
//        ArrayList<String> arrayList = new ArrayList<String>();
//        for(int i = 0; i < charingStationArounds.size(); i ++) {
//            arrayList.add(i, "附近充电桩"  + "ID=" + charingStationArounds.get(i).getId());
//            charingStationArounds.get(0).getId();
//        }
        chargingStationAroundListView.setDetailListView(charingStationArounds, chargingStationInfo);//设置ListView
    }

    //经纬度地址反编码地理位置
    public void showStationAdress(float latitude, float longitude)
    {
        LatLng latLng = new LatLng(latitude, longitude);
        // 创建地理编码检索实例
        GeoCoder geoCoder = GeoCoder.newInstance();

        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null
                        || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                    Toast.makeText(MyApplication.getContext(), "抱歉，未能找到结果",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(MyApplication.getContext(),
                        "位置：" + reverseGeoCodeResult.getAddress(), Toast.LENGTH_LONG)
                        .show();

            }
        };
        geoCoder.setOnGetGeoCodeResultListener(listener);
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));

    }

}
