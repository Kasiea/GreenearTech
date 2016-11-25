package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.map.Map;
import com.greenear.yeqinglu.greeneartech.map.MyOrientationListener;
import com.greenear.yeqinglu.greeneartech.model.Location;

import java.util.List;

import static com.greenear.yeqinglu.greeneartech.R.id.my_location;

/**
 * Created by yeqing.lu on 2016/11/19.
 */

public class TargetPostion {
    //需要获取的一些参数
    private double myLatitude;
    private double myLongitude;
    private float myAccuracy;
    public BDLocation myLocation;
    public MyOrientationListener myOritentationListener;

    //LocationClient类必须在主线程中声明。需要Context类型的参数。
    //Context需要时全进程有效的context,推荐用getApplicationConext获取全进程有效的context
    //LocationClient进行定位的一些设置
    private Context context;
    private BaiduMap baiduMap;
    private LocationClient locationClient;
    private BDLocationListener myListener = new MyLocationListener();
    private static boolean isFirstIn = true;
    private BitmapDescriptor mCurrentMarker;
    private float mCurrentX;//方向


    public TargetPostion(Context context, BaiduMap baiduMap, LocationClient locationClient) {
        this.context = context;
        this.baiduMap = baiduMap;
        this.locationClient = locationClient;
    }

    public void initData() {
        locationClient.registerLocationListener(myListener);    //注册监听函数
        mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.mark);//自定义图标

        myOritentationListener = new MyOrientationListener(context);
        //设置方向监听器
        myOritentationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;

                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder().
                        direction(mCurrentX)//改变方向
                        .accuracy(getMyAccuracy())
                        .latitude(getMyLatitude())
                        .longitude(getMyLongitude()).build();

                // 设置定位数据
                baiduMap.setMyLocationData(locData);

                //自定义图标
                MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, mCurrentMarker);
                baiduMap.setMyLocationConfigeration(config);

            }
        });
    }

    public double getMyLatitude() {
        return myLatitude;
    }

    public double getMyLongitude() {
        return myLongitude;
    }

    public float getMyAccuracy()
    {
        return myAccuracy;
    }

    public void getMyPosition()
    {
        //定位到我的位置
        LatLng latLng = new LatLng(getMyLatitude(),getMyLongitude());
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(msu);
    }


    /*设置定位参数包括：定位模式（高精度定位模式，低功耗定位模式和仅用设备定位模式），
   返回坐标类型，是否打开GPS，是否返回地址信息、位置语义化信息、POI信息等等。*/
    public void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setOpenAutoNotifyMode();
        locationClient.setLocOption(option);
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
            if (location == null || baiduMap == null) {
                return;
            }

            myLocation = location;
            myLatitude = location.getLatitude();
            myLongitude = location.getLongitude();
            myAccuracy = location.getRadius();

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder().
                    direction(mCurrentX)//改变方向
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection())//获取方向
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            // 设置定位数据
            baiduMap.setMyLocationData(locData);

            //自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS, true, mCurrentMarker);
            baiduMap.setMyLocationConfigeration(config);

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
            if (isFirstIn) {
                LatLng my_locate = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(my_locate);
                //更新位置
                baiduMap.animateMapStatus(u);
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


}
