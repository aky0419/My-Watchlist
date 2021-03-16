package com.example.tastytrademobilechallenge.Screens.HistroryGraphScreen;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 线形图表
 */
public class MPLineChartManager {
    //线形表
    private LineChart mLineChart;
    private LineDataSet set1;

    public MPLineChartManager(LineChart lineChart) {
        this.mLineChart = lineChart;
    }

    public void intiChart(){
        mLineChart.setBackgroundColor(Color.WHITE);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-40f);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.setNoDataText("No Data Found");
        mLineChart.setDrawGridBackground(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setTouchEnabled(true);
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setPinchZoom(true);
        mLineChart.setExtraOffsets(0, 0, 0, 30);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.animateX(10);
    }

    public void setData(ArrayList<Entry> values, ArrayList<String> xValues) {
        if (mLineChart.getData() != null && mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(6f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(2f);
            set1.setFormSize(15.f);
            set1.setFillColor(Color.WHITE);
            LineData data = new LineData(set1);
            mLineChart.setData(data);
            mLineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
            mLineChart.invalidate();
            mLineChart.refreshDrawableState();
        }
    }


}
