package com.greenear.yeqinglu.greeneartech.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.greenear.yeqinglu.greeneartech.model.MyApplication;

/**
 * Created by yeqing.lu on 2017/3/13.
 */

public class NetworrkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null&& networkInfo.isAvailable()){
            Toast.makeText(context, "network is available",
                    Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "network is unavailable",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
