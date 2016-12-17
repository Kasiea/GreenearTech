package com.greenear.yeqinglu.greeneartech.model;

import android.app.Application;
import android.content.Context;

/**
 * Created by yeqing.lu on 2016/12/17.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
