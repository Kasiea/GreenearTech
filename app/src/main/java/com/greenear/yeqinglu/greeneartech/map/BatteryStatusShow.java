package com.greenear.yeqinglu.greeneartech.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.greenear.yeqinglu.greeneartech.R;

/**
 * Created by yeqing.lu on 2016/11/22.
 */

public class BatteryStatusShow extends FrameLayout {

    public TextView battery_soc ;
    public TextView travel_distance;

    public BatteryStatusShow(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.battery_status_show, this);

        battery_soc = (TextView)findViewById(R.id.battery_status);
        travel_distance = (TextView)findViewById(R.id.travel_distance);

    }
}
