package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_status);

        columnChart = (ColumnChartView) findViewById(R.id.chart_vm);
        dataInit_Vm();

        columnChart_R = (ColumnChartView)findViewById(R.id.chart_r);
        dataInit_R();
    }

    private void dataInit_Vm() {
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
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5,
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
        columnChart.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        columnChart.setValueSelectionEnabled(true);

        columnChart.setZoomType(ZoomType.HORIZONTAL);

    }

    private void dataInit_R() {
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
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5,
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
                .setTextColor(Color.BLACK).setMaxLabelChars(2).setName("R"));


        //最后将所有值显示在View中
        columnChart_R.setColumnChartData(columnData);

        // Set value touch listener that will trigger changes for chartTop.
        columnChart_R.setOnValueTouchListener(new ValueTouchListener());

        // Set selection mode to keep selected month column highlighted.
        columnChart_R.setValueSelectionEnabled(true);

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
        }

        @Override
        public void onValueDeselected() {

// generateLineData(ChartUtils.COLOR_GREEN, 0);

        }

    }
}

