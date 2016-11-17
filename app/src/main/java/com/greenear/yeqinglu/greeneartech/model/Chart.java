package com.greenear.yeqinglu.greeneartech.model;

import android.graphics.Color;

import com.greenear.yeqinglu.greeneartech.interf.BaseChart;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * Created by yeqing.lu on 2016/11/17.
 */

public class Chart implements BaseChart {

    //data
    private Bms bms;
    public final static String[] battery = new String[]{"Battery_No.1", "Battery_No.2", "Battery_No.3",
            "Battery_No.4",};

    //ui
    private ColumnChartView columnChartView;
    private ColumnChartData columnChartData;


    public  Chart(Bms bms, ColumnChartView columnChartView, ColumnChartData columnChartData)
    {
        this.bms = bms;
        this.columnChartView = columnChartView;
        this.columnChartData = columnChartData;
    }


    @Override
    public void showBatVm() {

        //初始化数据
//        Float[] battery_vm = new Float[]{Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_1()), Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_2()),
//                Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_3()), Float.parseFloat(bmsBatteryVolt.getBms_bat_vol_4()),};

        int BmsBatNum = bms.getBatList().size();
        ArrayList<Float> battery_vm= new ArrayList<>();
        for (int i = 0; i < BmsBatNum; i++ ) {
            battery_vm.add(i, new Float(bms.getBatList().get(i).getVol()));
        }


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
                values.add(new SubcolumnValue(battery_vm.get(j+i),
                        ChartUtils.pickColor()));
            }

            //设置X轴的柱子所对应的属性名称
            axisValues.add(new AxisValue(i).setLabel(battery[i]));
            //将每个属性的拥有的柱子，添加到Column中
            //设置每个柱子的Lable是否选中，为false，表示不用选中，一直显示在柱子上
            //将每个属性得列全部添加到List中
            columns.add(new Column(values).setHasLabelsOnlyForSelected(true));
        }

        columnChartData = new ColumnChartData(columns);

        //设置X轴显示在底部，并且显示每个属性的Lable
        columnChartData.setAxisXBottom(new Axis(axisValues).setHasLines(true)
                .setTextColor(Color.BLACK).setName("Battery").setMaxLabelChars(8));
        columnChartData.setAxisYLeft(new Axis().setHasLines(true)
                .setTextColor(Color.BLACK).setMaxLabelChars(2).setName("mV"));


        //最后将所有值显示在View中
        columnChartView.setColumnChartData(columnChartData);

        // Set value touch listener that will trigger changes for chartTop.
        //设置监听器
//        columnChart.setOnValueTouchListener(new ValueTouchListener());


        // Set selection mode to keep selected month column highlighted.
        columnChartView.setValueSelectionEnabled(true);

        //设置用户与柱形图交互与否
//        columnChart.setInteractive(false);

        //设置图形缩放
        columnChartView.setZoomEnabled(false);

        columnChartView.setZoomType(ZoomType.HORIZONTAL);

    }

    @Override
    public void showBatSoh() {

    }

    @Override
    public void showBatSoc() {

    }
}
