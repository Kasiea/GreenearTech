package com.greenear.yeqinglu.greeneartech.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.map.TargetPostion;
import com.greenear.yeqinglu.greeneartech.model.CharingStationAround;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;
import com.greenear.yeqinglu.greeneartech.model.User;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;
import com.greenear.yeqinglu.greeneartech.service.SharedPreData;
import com.greenear.yeqinglu.greeneartech.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeqing.lu on 2016/11/17.
 */

public class MapActivity extends Activity {

    public BaiduMap mBaiduMap = null;
    public MapView mMapView = null;

    //定位
    public LocationClient mLocationClient;
    public TargetPostion targetPosition;
    public FloatingActionButton my_location;

    //搜索附近充电桩
    public FloatingActionButton charingStationAround_btn;
    public ChargingStation chargingStation;
    public ChargingStationAroundListView chargingStationAroundListView;

    //搜索具体位置附近充电桩
    private Button search_specific_cg_btn;

    //显示覆盖物具体信息，自定义view
    public ChargingStationInfo chargingStationInfo;

    //用户信息相关
    private User user;

    //Net相关
    private Handler handler;
    private int IS_FINISHED = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_activity);

        updateCharingStationAround();//刷新附近充电桩

        initView();
        initData();

        setConfig();

         /* start：启动定位SDK。 stop：关闭定位SDK。调用start之后只需要等待定位结果自动回调即可。
        开发者定位场景如果是单次定位的场景，在收到定位结果之后直接调用stop函数即可。
        如果stop之后仍然想进行定位，可以再次start等待定位结果回调即可。
        如果开发者想按照自己逻辑请求定位，可以在start之后按照自己的逻辑请求locationclient.
                requestLocation()函数，会主动触发定位SDK内部定位逻辑，等待定位回调即可。*/
        mLocationClient.start();

        setChargingStationInfoInvisible();//监听点击地图后Info界面隐藏

    }


    //初始化数据
    public void initData()
    {
        mBaiduMap = mMapView.getMap();

        //定位
        mLocationClient = new LocationClient(MyApplication.getContext());
        targetPosition = new TargetPostion(MyApplication.getContext(), mBaiduMap, mLocationClient);

        //添加覆盖物
        chargingStation = new ChargingStation(mBaiduMap, MapActivity.this, chargingStationInfo, chargingStationAroundListView);

        //获取用户信息
        user = new User(handler);

    }


    //初始化视图
    public void initView()
    {
        mMapView = (MapView) findViewById(R.id.bmapView);
        my_location = (FloatingActionButton) findViewById(R.id.my_location);

        //添加覆盖物信息相关：充电桩信息，列表，按钮
        chargingStationInfo = (ChargingStationInfo)findViewById(R.id.showChargerInfo);
        chargingStationAroundListView = (ChargingStationAroundListView)findViewById(R.id.charging_station_around_listview);
        charingStationAround_btn = (FloatingActionButton) findViewById(R.id.charging_station_around);

        //搜索具体位置附近充电桩
        search_specific_cg_btn = (Button)findViewById(R.id.specific_location_search);

    }


    //配置相关
    public void setConfig()
    {
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //普通地图
        mBaiduMap.setTrafficEnabled(true);//打开实时交通图
        mBaiduMap.setMyLocationEnabled(true); // 开启定位图层
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(
                new MapStatus.Builder().zoom(15).overlook(0).rotate(90).build()));//设置缩放级别及俯视角度

        mMapView.showZoomControls(false);//隐藏缩放标志

        //定位位置设置
        targetPosition.initData();//初始化对象数据
        targetPosition.initLocation();//定义定位参数
        setMyLocation();//定位监听

        //搜索充电桩设置
//        chargingStation.add();//添加覆盖物
        getCharingStationAround();//搜索附近充电桩
        chargingStation.setChangingStationInfoShow();//覆盖物信息窗口显示

        //搜索具体位置充电桩监听
        searchSpecificCG();

    }


    //更新附近充电桩信息
    public void updateCharingStationAround()
    {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == IS_FINISHED) {
                    chargingStation.addCharingStationAround(user.charingStationArounds);//更新附近充电桩图标
                    chargingStation.addChargingStationListview(user.charingStationArounds);//更新附近充电桩列表
                }
            }
        };
    }


    //获取自己的位置Button
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


    //获取附近充电桩Button
    public void getCharingStationAround()
    {
        charingStationAround_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.getChargingStation(targetPosition.myLongitude, targetPosition.myLatitude);
            }
        });
    }

    //监听点击地图后充电桩站详见页面隐藏
    public void setChargingStationInfoInvisible()
    {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //设置动画效果
                TranslateAnimation tAnima = new TranslateAnimation(0,0,0,800);
                tAnima.setDuration(600);
                chargingStationInfo.setAnimation(tAnima);
                chargingStationInfo.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    //搜索具体位置充电桩
    public void searchSpecificCG(){
        search_specific_cg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), SearchSpecificLocation.class);
                startActivity(intent);
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
        targetPosition.isFirstIn = true;
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
