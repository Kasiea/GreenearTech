package com.greenear.yeqinglu.greeneartech.others;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by yeqing.lu on 2016/11/7.
 */

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    //onCreate()方法会在服务创建的时候调用
    public void onCreate() {
        super.onCreate();
    }

    @Override
    //onStartCommand()方法会在每次服务启动的时候调用
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //start task
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    //onDestroy()方法会在服务销毁的时候调用
    public void onDestroy() {
        super.onDestroy();
    }
}
