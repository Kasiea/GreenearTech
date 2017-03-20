package com.greenear.yeqinglu.greeneartech.map;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class ChargingStationInfo extends FrameLayout {

    public LinearLayout charger_info;
    public ImageView info_img;
    public TextView info_name;
    public TextView info_dis;
    public TextView avialable_cg_num;
    public TextView total_cg_num;
    public ImageButton back_btn;
    public ImageButton guide_btn;

    public ChargingStationInfo(final Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取到LayoutInflater的实例,加载布局
        LayoutInflater.from(context).inflate(R.layout.charging_station_info, this);

        charger_info = (LinearLayout)findViewById(R.id.charger_info);
        info_img = (ImageView)findViewById(R.id.id_info_img);
        info_name = (TextView)findViewById(R.id.id_info_name);
        avialable_cg_num = (TextView)findViewById(R.id.avaliable_cg_num);
        total_cg_num = (TextView)findViewById(R.id.total_cg_num);
        info_dis = (TextView)findViewById(R.id.id_info_dis);
        back_btn = (ImageButton) findViewById(R.id.back);
        guide_btn = (ImageButton) findViewById(R.id.guide);


        //回退按钮
        back_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置动画效果
                TranslateAnimation tAnima = new TranslateAnimation(0,0,0,800);
                tAnima.setDuration(600);
                charger_info.setAnimation(tAnima);
                charger_info.setVisibility(View.INVISIBLE);
            }
        });

        guide_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), BDGuide.class);
                context.startActivity(intent);
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

}
