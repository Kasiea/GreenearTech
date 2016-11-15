package com.greenear.yeqinglu.greeneartech.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.LocalSearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.greenear.yeqinglu.greeneartech.R;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yeqing.lu on 2016/9/22.
 */

public class Map<S, S1> extends Activity {

    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    PoiSearch mPoiSearch = null;

    MyLocationConfiguration.LocationMode mCurrentMode;

    //定位
    //LocationClient类必须在主线程中声明。需要Context类型的参数。
    //Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
    //LocationClient进行定位的一些设置
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public BDLocation my_location = null;
    private boolean isFirstIn = true;
    public Button MyLocationBt = null;
    private double mLatitude;
    private double mLongtitude;
    public BitmapDescriptor mCurrentMarker;
    public MyOrientationListener myOritentationListener;
    private float mCurrentX;

    //POI
    public ListView nearby_lv = null;
    public TextView nearby_txt = null;
    public Button poi = null;

    //导航
    private final static String authBaseArr[] =
            { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION };
    private final static int authBaseRequestCode = 1;
    private final static String authComArr[] = { Manifest.permission.READ_PHONE_STATE };
    private String mSDCardPath = null;
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
    private String authinfo = null;
    private BNRoutePlanNode.CoordinateType mCoordinateType = null;
    private boolean hasInitSuccess = false;
    private boolean hasRequestComAuth = false;
    private final static int authComRequestCode = 2;
    public static List<Activity> activityList = new LinkedList<Activity>();
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private Button guid = null;

    //覆盖物相关
    private BitmapDescriptor mMarker;
    public Button add_overlay;
    private RelativeLayout mMarkerLy;

    //画圆
    private CircleOptions circle;
    private Button add_circle = null;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    // showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    // showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);

        activityList.add(this);

        //定位
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类，配置
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        MyLocationBt = (Button)findViewById(R.id.my_location); //注册位置按钮
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.mark);//自定义图标
        myOritentationListener = new MyOrientationListener(this.getApplicationContext());
        //设置方向监听器
        myOritentationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {

            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });


        //定位监听
        MyLocationBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定位到我的位置
                LatLng latLng = new LatLng(mLatitude,mLongtitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);

            }
        });

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //初始化ListView
//        nearby_lv = (ListView)findViewById(R.id.nearby_lv);

        //POI搜索
//        poi = (Button)findViewById(R.id.poi);
//        nearby_txt = (TextView)findViewById(R.id.nerby_txt);

        //导航
//        guid = (Button)findViewById(R.id.guid);


        //设置地图类型
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //普通地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);//空白地图

        //设置交通，热力图
        mBaiduMap.setTrafficEnabled(true);//打开实时交通图
//        mBaiduMap.setBaiduHeatMapEnabled(true);  //城市热力图

        //自定义Maker坐标点
//        LatLng p1 = new LatLng(31.1, 121.2);
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.car3);//构建Marker图标
//        OverlayOptions option = new MarkerOptions().position(p1).icon(bitmap);//构建MarkerOption，用于在地图上添加Marker
//        mBaiduMap.addOverlay(option);//在地图上添加Marker，并显示


//        //第一步，设置可拖拽：
//        OverlayOptions options = new MarkerOptions()
//                .position(p1)  //设置marker的位置
//                .icon(bitmap)  //设置marker图标
//                .zIndex(9)  //设置marker所在层级
//                .draggable(true);  //设置手势拖拽
//        //将marker添加到地图上
//        Marker marker = (Marker) (mBaiduMap.addOverlay(options));
//
//        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//            public void onMarkerDrag(Marker marker) {
//                //拖拽中
//            }
//            public void onMarkerDragEnd(Marker marker) {
//                //拖拽结束
//            }
//            public void onMarkerDragStart(Marker marker) {
//                //开始拖拽
//            }
//        });


//        //定义动画效果的Maker坐标点
//        final LatLng point = new LatLng(39.963175, 116.400244);
//        //设置动画效果
//        BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.bd1);
//        BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.bd2);
//        BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.drawable.bd3);
//
//        // 通过marker的icons设置一组图片，再通过period设置多少帧刷新一次图片资源
//        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//        giflist.add(bdA);
//        giflist.add(bdB);
//        giflist.add(bdC);
//        OverlayOptions ooD = new MarkerOptions().position(point).icons(giflist)
//                .zIndex(0).period(10);
//        Marker mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

        //设置区域覆盖
        //定义多边形的五个顶点
//        LatLng pt1 = new LatLng(39.93923, 116.357428);
//        LatLng pt2 = new LatLng(39.91923, 116.327428);
//        LatLng pt3 = new LatLng(39.89923, 116.347428);
//        LatLng pt4 = new LatLng(39.89923, 116.367428);
//        LatLng pt5 = new LatLng(39.91923, 116.387428);
//        List<LatLng> pts = new ArrayList<LatLng>();
//        pts.add(pt1);
//        pts.add(pt2);
//        pts.add(pt3);
//        pts.add(pt4);
//        pts.add(pt5);
//        //构建用户绘制多边形的Option对象
//        OverlayOptions polygonOption = new PolygonOptions()
//                .points(pts)
//                .stroke(new Stroke(5, 0xAA00FF00))
//                .fillColor(0xAAFFFF00);
//        //在地图上添加多边形Option，用于显示
//        mBaiduMap.addOverlay(polygonOption);


        //可放在onStart（）内
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位服务
        initLocation();
       /* start：启动定位SDK。 stop：关闭定位SDK。调用start之后只需要等待定位结果自动回调即可。
        开发者定位场景如果是单次定位的场景，在收到定位结果之后直接调用stop函数即可。
        如果stop之后仍然想进行定位，可以再次start等待定位结果回调即可。
        如果开发者想按照自己逻辑请求定位，可以在start之后按照自己的逻辑请求locationclient.
                requestLocation()函数，会主动触发定位SDK内部定位逻辑，等待定位回调即可。*/
        mLocationClient.start();




        //POI搜索周边
        //将POI搜索方法延迟或者放进BUTTON，否则会出现Permission undefined情况
        //设置POI
        //第一步，创建POI检索实例
//        mPoiSearch = PoiSearch.newInstance();
//
//        // 第二步，创建POI检索监听者；
//        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
//            public void onGetPoiResult(PoiResult result) {
//                //获取POI检索结果
//                if(result != null) {
//                    PoiInfo poiInfo = result.getAllPoi().get(1);
////                    nearby_txt.setText(poiInfo.address + poiInfo.city + poiInfo.name
////                            +poiInfo.phoneNum + poiInfo.postCode );
//                    //获取POI检索结果
////                    List<PoiInfo> allAddr = result.getAllPoi();
////                    for (PoiInfo p: allAddr) {
////                        Log.d("MainActivity", "p.name--->" + p.name +"p.phoneNum" + p.phoneNum +" -->p.address:" + p.address + "p.location" + p.location);
////                    }
//                }
//            }
//
//            public void onGetPoiDetailResult(PoiDetailResult result) {
//                //获取Place详情页检索结果
//            }
//
//            @Override
//            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
//
//            }
//        };

        // 第三步，设置POI检索监听者；
//        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
//
//        poi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 第四步，发起检索请求；
////                mPoiSearch.searchInCity((new PoiCitySearchOption())
////                        .city("上海").keyword("美食")
////                        .pageNum(10));
//
//                //附近POI搜索
//                //PS： location和radius的值一定要设置
//                mPoiSearch.searchNearby(new PoiNearbySearchOption().location(new LatLng(mLatitude,mLongtitude)).radius(1000).keyword("美食").pageNum(10));//搜索附近
//                //第五步，释放POI检索实例；
////                mPoiSearch.destroy();
//            }
//        });


        //导航
//        if (initDirs()) {
//            initNavi();
//        }
//
//        //导航触发
//        guid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (BaiduNaviManager.isNaviInited()) {
//                    routeplanToNavi(BNRoutePlanNode.CoordinateType.GCJ02);
//                }
//            }
//        });

        //添加覆盖物相关
        add_overlay = (Button)findViewById(R.id.add_overlay);
        add_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOverlays(Info.infos);
            }
        });

        initMarker();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                Info info = (Info)extraInfo.getSerializable("info");
                ImageView iv = (ImageView) mMarkerLy.findViewById(R.id.id_info_img);
                TextView distance = (TextView) mMarkerLy.findViewById(R.id.id_info_dis);
                TextView name = (TextView) mMarkerLy.findViewById(R.id.id_info_name);
                TextView zan = (TextView) mMarkerLy.findViewById(R.id.id_info_zan);

                iv.setImageResource(info.getImagId());
                distance.setText(info.getDistance());
                name.setText(info.getName());
                zan.setText(info.getZan()+"");

                InfoWindow infoWindow;
                TextView tv = new TextView(getApplicationContext());
                tv.setBackground(getResources().getDrawable(R.drawable.tip));
                tv.setPadding(30,20,30,50);
                tv.setText(info.getName());

                final LatLng latLng = marker.getPosition();
                Point p = mBaiduMap.getProjection().toScreenLocation(latLng);
                p.y -= 47;
                LatLng ll = mBaiduMap.getProjection().fromScreenLocation(p);

                infoWindow = new InfoWindow(tv, ll,1);
                mBaiduMap.showInfoWindow(infoWindow);

                mMarkerLy.setVisibility(View.VISIBLE);
                return true;

            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarkerLy.setVisibility(View.VISIBLE);
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });


        add_circle = (Button)findViewById(R.id.add_circle) ;
        //画圆
        add_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCircle();
            }
        });

    }

    private void addCircle()
    {
        //画圆
        circle = new CircleOptions();
        LatLng center = new LatLng(mLatitude, mLongtitude);
        circle.center(center);
        circle.radius(800);
        int color = Color.argb(127,255,0,255);
        circle.fillColor(color);
        Stroke stroke = new Stroke(1,0xFF00FF00);
        circle.stroke(stroke);
        mBaiduMap.addOverlay(circle);
    }

    private void initMarker()
    {
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        mMarkerLy = (RelativeLayout)findViewById(R.id.id_marker_ly);
    }

    //添加覆盖物
    private void addOverlays(List<Info>infos)
    {
        mBaiduMap.clear();
        LatLng latLng = null ;
        Marker marker = null;
        OverlayOptions options;
        for (Info info:infos)
        {
            //经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongtitude());
            //图标
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info",info);
            marker.setExtraInfo(arg0);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

    /*设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），
    返回坐标类型，是否打开GPS，是否返回地址信息、位置语义化信息、POI信息等等。*/
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }


    /*BDLocationListener接口有1个方法需要实现：
            1.接收异步返回的定位结果，参数是BDLocation类型参数。
    BDLocation类，封装了定位SDK的定位结果，在BDLocationListener的
    onReceive方法中获取。通过该类用户可以获取error code，位置的坐标，
    精度半径等信息。*/
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mBaiduMap == null) {
                return;
            }

            my_location = location;
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder().
                    direction(mCurrentX)//改变方向
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection())//获取方向
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            //自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);

            //圆圈
//            circle = new CircleOptions();
//            LatLng center = new LatLng(mLatitude, mLongtitude);
//            circle.center(center);
//            circle.radius(800);
//            int color = Color.argb(127,255,0,255);
//            circle.fillColor(color);
//            Stroke stroke = new Stroke(1,0xFF00FF00);
//            circle.stroke(stroke);
//            mBaiduMap.addOverlay(circle);

            //自动定位到本身该位置
            //根据位置移动
            if(isFirstIn) {
                LatLng my_locate = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(my_locate);
                //更新位置
                mBaiduMap.animateMapStatus(u);
                isFirstIn = false;
            }

            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }


    }

    //导航相关
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }


    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /*在使用SDK前，应该先进行百度服务授权和引擎初始化。已经内置了tts播报功能，
    如果需要使用自己的tts来进行播报，需要传入对应tts回调。*/
    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {

            if (!hasBasePhoneAuth()) {

                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;

            }
        }

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                Map.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(Map.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
                Toast.makeText(Map.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
                Toast.makeText(Map.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(Map.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, ttsPlayStateListener);

    }

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            // showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            // showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };

    private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private boolean hasBasePhoneAuth() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCompletePhoneAuth() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        mCoordinateType = coType;
        if (!hasInitSuccess) {
            Toast.makeText(Map.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }
        // 权限申请
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // 保证导航功能完备
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                    Toast.makeText(Map.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;
        switch (coType) {
            case GCJ02: {

//                sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", null, coType);
//                eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", null, coType);
//                break;

                //制定起始点和终点的导航
                sNode = new BNRoutePlanNode(my_location.getLongitude(),my_location.getLatitude(),"昂立大厦" , null, coType);
                eNode = new BNRoutePlanNode(121.2, 31.1, "佘山", null, coType);
                break;
            }
            case WGS84: {
                sNode = new BNRoutePlanNode(116.300821, 40.050969, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.397491, 39.908749, "北京天安门", null, coType);
                break;
            }
            case BD09_MC: {
                sNode = new BNRoutePlanNode(12947471, 4846474, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(12958160, 4825947, "北京天安门", null, coType);
                break;
            }
            case BD09LL: {
                sNode = new BNRoutePlanNode(116.30784537597782, 40.057009624099436, "百度大厦", null, coType);
                eNode = new BNRoutePlanNode(116.40386525193937, 39.915160800132085, "北京天安门", null, coType);
                break;
            }
            default:
                ;
        }
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
        }
    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
        /*
         * 设置途径点以及resetEndNode会回调该接口
         */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(Map.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(Map.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }



    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);

            return 1;
        }

        @Override
        public void phoneHangUp() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
            // TODO Auto-generated method stub
            Log.e("test_TTS", "getTTSState");
            return 1;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(Map.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routeplanToNavi(mCoordinateType);
        }

    }


    public void onGetSearchResult(CloudSearchResult result, int error) {
        //在此处理相应的检索结果
        LocalSearchInfo info = new LocalSearchInfo();
        info.ak = "B266f735e43ab207ec152deff44fec8b";
//此处info.ak为服务端ak，非Adnroid sdk端ak， 且此服务端ak和Adnroid sdk端ak 是在同一个账户。
        info.geoTableId = 31869;
// info.geoTableId 是存储在于info.ak相同开发账户中。
        info.tags = "测试";
        info.q = "天安门";
        info.region = "北京市";
        CloudManager.getInstance().localSearch(info);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
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


    @Override
    public void onStart() {
        super.onStart();



        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();;
        //开启方向传感器
        myOritentationListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();

        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //停止方向传感器
        myOritentationListener.stop();


    }
}
