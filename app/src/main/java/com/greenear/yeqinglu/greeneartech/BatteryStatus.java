package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsBatteryVolt;
import com.greenear.yeqinglu.greeneartech.JsonData.BmsSohQuery;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by yeqing.lu on 2016/9/22.
 */

public class BatteryStatus extends Activity {
    public final static String[] battery = new String[]{"Battery_No.1", "Battery_No.2", "Battery_No.3",
            "Battery_No.4",};
    private ColumnChartView columnChart;
    private ColumnChartData columnData;
    List<Column> lsColumn = new ArrayList<Column>();
    private List<SubcolumnValue> lsValue;

    private ColumnChartView columnChart_R;

    public Context context;
    public RequestQueue mQueue;
    public BmsBatteryVolt bmsBatteryVolt;
    public BmsSohQuery bmsSohQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_status);
        columnChart = (ColumnChartView) findViewById(R.id.chart_vm);
        columnChart_R = (ColumnChartView)findViewById(R.id.chart_r);

        context = this.getApplicationContext();
        //获取到一个RequestQueue对象
        /*RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，
        然后按照一定的算法并发地发出这些请求。RequestQueue内部的设
        计就是非常合适高并发的，因此我们不必为每一次HTTP请求都创建
        一个RequestQueue对象，这是非常浪费资源的，基本上在每一个需
        要和网络交互的Activity中创建一个RequestQueue对象就足够了。*/
        mQueue = Volley.newRequestQueue(context);

        getBmsBatteryVolt();
        getBmsSoh();

    }

    public void getBmsBatteryVolt() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bat-vol/query/1",
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        bmsBatteryVolt = fast_json.parseObject(response, BmsBatteryVolt.class);
                        dataInit_Vm();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        //将这个StringRequest对象添加到RequestQueue里
        mQueue.add(stringRequest);
    }

    public void getBmsSoh() {
        //创建一个StringRequest对象
       /* 这里new出了一个StringRequest对象，StringRequest的构造函数需要
         传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是
         服务器响应成功的回调，第三个参数是服务器响应失败的回调。其中，
         目标服务器地址我们填写的是百度的首页，然后在响应成功的回调里打
         印出服务器返回的内容，在响应失败的回调里打印出失败的详细信息。*/
        StringRequest stringRequest = new StringRequest( "http://192.168.1.5/laravel-bms/public/api/data/bat-soh/query/1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        bms_data.setText(response);

                        JSONObject fast_json = new JSONObject();//new一个FastJson对象
                        bmsSohQuery = fast_json.parseObject(response, BmsSohQuery.class);
                        dataInit_Soh();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                });

        //将这个StringRequest对象添加到RequestQueue里
        mQueue.add(stringRequest);
    }

    private void dataInit_Vm() {
        //初始化数据
        Float[] battery_vm = new Float[]{Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_1()), Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_2()),
                Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_3()), Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_4()),};

        //每个集合显示几条柱子
        int numSubcolumns = 1;
        //显示多少个集合
        int numColumns = battery.length;


        //保存所有的柱子
        List<Column> columns = new ArrayList<Column>();
        //保存每个竹子的值
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;

        //对每个集合的柱子进行遍历
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            //循环所有柱子（list）
            for (int j = 0; j < numSubcolumns; ++j) {
                //创建一个柱子，然后设置值和颜色，并添加到list中
                values.add(new SubcolumnValue(battery_vm[j+i],
                        ChartUtils.pickColor()));
            }

            //设置X轴的柱子所对应的属性名称
            axisValues.add(new AxisValue(i).setLabel(battery[i]));
            //将每个属性的拥有的柱子，添加到Column中
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            //将每个属性得列全部添加到List中
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        //设置X轴显示在底部，并且显示每个属性的Lable
        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true)
                .setTextColor(Color.BLACK).setName("Battery").setMaxLabelChars(8));
        columnData.setAxisYLeft(new Axis().setHasLines(true)
                .setTextColor(Color.BLACK).setMaxLabelChars(2).setName("mV"));


        //最后将所有值显示在View中
        columnChart.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        //设置监听器
//        columnChart.setOnValueTouchListener(new ValueTouchListener());


        // Set selection mode to keep selected month column highlighted.
        columnChart.setValueSelectionEnabled(true);

        //设置用户与柱形图交互与否
//        columnChart.setInteractive(false);

        //设置图形缩放
        columnChart_R.setZoomEnabled(false);

        columnChart.setZoomType(ZoomType.HORIZONTAL);

    }

    private void dataInit_Soh() {
        //初始化数据
        Float[] battery_soh = new Float[]{Float.parseFloat(bmsSohQuery.getBms_bat_soh_1()), Float.parseFloat(bmsSohQuery.getBms_bat_soh_2()),
                Float.parseFloat(bmsSohQuery.getBms_bat_soh_3()),Float.parseFloat(bmsSohQuery.getBms_bat_soh_4()),};

        //每个集合显示几条柱子
        int numSubcolumns = 1;
        //显示多少个集合
        int numColumns = battery.length;


        //保存所有的柱子
        List<Column> columns = new ArrayList<Column>();
        //保存每个竹子的值
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<SubcolumnValue> values;

        //对每个集合的柱子进行遍历
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            //循环所有柱子（list）
            for (int j = 0; j < numSubcolumns; ++j) {
                //创建一个柱子，然后设置值和颜色，并添加到list中
                values.add(new SubcolumnValue(battery_soh[i+j],
                        ChartUtils.pickColor()));
            }
            //设置X轴的柱子所对应的属性名称
            axisValues.add(new AxisValue(i).setLabel(battery[i]));
            //将每个属性的拥有的柱子，添加到Column中
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            //将每个属性得列全部添加到List中
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnData = new ColumnChartData(columns);

        //设置X轴显示在底部，并且显示每个属性的Lable
        columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true)
                .setTextColor(Color.BLACK).setName("Battery").setMaxLabelChars(8));
        columnData.setAxisYLeft(new Axis().setHasLines(true)
                .setTextColor(Color.BLACK).setMaxLabelChars(2).setName("SOH"));


        //最后将所有值显示在View中
        columnChart_R.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        //设置监听器
//        columnChart_R.setOnValueTouchListener(new ValueTouchListener());


        // Set selection mode to keep selected month column highlighted.
        columnChart_R.setValueSelectionEnabled(true);

        //设置用户与柱形图交互与否
//        columnChart_R.setInteractive(false);

        //设置图形缩放
        columnChart_R.setZoomEnabled(false);

        columnChart_R.setZoomType(ZoomType.HORIZONTAL);

    }

    /**
     * 柱状图监听器
     *
     * @author 1017
     */
    private class ValueTouchListener implements
            ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex,
                                    SubcolumnValue value) {
            // generateLineData(value.getColor(), 100);
            Toast.makeText(BatteryStatus.this,"Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {

// generateLineData(ChartUtils.COLOR_GREEN, 0);

        }

    }
}

