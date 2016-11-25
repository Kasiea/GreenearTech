package com.greenear.yeqinglu.greeneartech.map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.map.TargetPostion;

/**
 * Created by yeqing.lu on 2016/11/17.
 */

public class MapActivity extends Activity {

    public BaiduMap mBaiduMap = null;
    public MapView mMapView = null;
    public Context context;

    //定位
    public LocationClient mLocationClient;
    public TargetPostion targetPosition;
    public Button my_location;

    //添加覆盖物
    public ChargingStation chargingStation;

    //显示覆盖物具体信息，自定义view
    public ChargingStationInfo chargingStationInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_activity);

        initView();
        initData();
        targetPosition.initData();//初始化对象数据
        setConfig();
        targetPosition.initLocation();//定义定位参数

         /* start：启动定位SDK。 stop：关闭定位SDK。调用start之后只需要等待定位结果自动回调即可。
        开发者定位场景如果是单次定位的场景，在收到定位结果之后直接调用stop函数即可。
        如果stop之后仍然想进行定位，可以再次start等待定位结果回调即可。
        如果开发者想按照自己逻辑请求定位，可以在start之后按照自己的逻辑请求locationclient.
                requestLocation()函数，会主动触发定位SDK内部定位逻辑，等待定位回调即可。*/
        mLocationClient.start();

        chargingStation.setChangingStationInfoShow();//覆盖物信息窗口显示

        setMyLocation();//定位监听

    }

    //初始化数据
    public void initData()
    {
        context = this.getApplicationContext();
        mBaiduMap = mMapView.getMap();

        //定位
        mLocationClient = new LocationClient(context);
        targetPosition = new TargetPostion(context, mBaiduMap, mLocationClient);

        //添加覆盖物
        chargingStation = new ChargingStation(mBaiduMap, chargingStationInfo, context);

    }

    //初始化视图
    public void initView()
    {
        mMapView = (MapView) findViewById(R.id.bmapView);
        my_location = (Button)findViewById(R.id.my_location);

        //添加覆盖物信息视图
        chargingStationInfo = (ChargingStationInfo)findViewById(R.id.showChargerInfo);
    }

    //地图配置相关
    public void setConfig()
    {
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //普通地图
        mBaiduMap.setTrafficEnabled(true);//打开实时交通图
        mBaiduMap.setMyLocationEnabled(true); // 开启定位图层
        chargingStation.add();//添加覆盖物
    }

    public void setMyLocation()
    {
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定位到我的位置
                  targetPosition.getMyPosition();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        //开启方向传感器
        targetPosition.myOritentationListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        targetPosition.myOritentationListener.stop();//停止方向传感器
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();// 退出时销毁定位
        mBaiduMap.setMyLocationEnabled(false);// 关闭定位图层
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
