package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class ChargingStationInfo extends FrameLayout {

    private ImageView info_img;
    private TextView info_name;
    private TextView info_dis;
    private TextView info_zan;

    public ChargingStationInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取到LayoutInflater的实例,加载布局
        LayoutInflater.from(context).inflate(R.layout.charging_station_info, this);

        info_img = (ImageView)findViewById(R.id.id_info_img);
        info_name = (TextView)findViewById(R.id.id_info_name);
        info_dis = (TextView)findViewById(R.id.id_info_dis);
        info_zan = (TextView)findViewById(R.id.id_info_zan);

    }

    public void setInfo_img(ImageView info_img) {
        this.info_img = info_img;
    }

    public void setInfo_name(TextView info_name) {
        this.info_name = info_name;
    }

    public void setInfo_dis(TextView info_dis) {
        this.info_dis = info_dis;
    }

    public void setInfo_zan(TextView info_zan) {
        this.info_zan = info_zan;
    }
}
