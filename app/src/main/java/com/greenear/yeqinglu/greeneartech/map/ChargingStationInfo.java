package com.greenear.yeqinglu.greeneartech.map;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class ChargingStationInfo extends FrameLayout {

    public LinearLayout charger_info;
    public ImageView info_img;
    public TextView info_name;
    public TextView info_dis;
    public TextView info_zan;
    public Button back_btn;

    public ChargingStationInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取到LayoutInflater的实例,加载布局
        LayoutInflater.from(context).inflate(R.layout.charging_station_info, this);

        charger_info = (LinearLayout)findViewById(R.id.charger_info);
        info_img = (ImageView)findViewById(R.id.id_info_img);
        info_name = (TextView)findViewById(R.id.id_info_name);
        info_dis = (TextView)findViewById(R.id.id_info_dis);
        info_zan = (TextView)findViewById(R.id.id_info_zan);
        back_btn = (Button)findViewById(R.id.back);

        //回退按钮
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                charger_info.setVisibility(View.INVISIBLE);
            }
        });

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