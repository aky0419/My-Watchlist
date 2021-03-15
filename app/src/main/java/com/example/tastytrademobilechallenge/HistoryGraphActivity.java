package com.example.tastytrademobilechallenge;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tastytrademobilechallenge.Models.HistoricalDataModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HistoryGraphActivity extends AppCompatActivity {
    private static final String TAG = "HistoryGraphActivity";
    TextView symbol, askPrice, bidPrice, lastPrice;
    LineChart mCandleStickChart;
    HistoryGraphViewModel mHistoryGraphViewModel;
    String symbolName;

    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合

    private LineDataSet set1;
    double highestPrice;
    double lowestPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_graph);
        //初始化View
        initView();
        Intent intent = getIntent();
        highestPrice = Double.MIN_VALUE;
        lowestPrices = Double.MAX_VALUE;
        symbolName = intent.getStringExtra("symbol");
        mHistoryGraphViewModel = new ViewModelProvider(this).get(HistoryGraphViewModel.class);
        mHistoryGraphViewModel.getHistoricalDataList(symbolName);
        mHistoryGraphViewModel.historicalDataList.observe(this, new Observer<List<HistoricalDataModel>>() {
            @Override
            public void onChanged(List<HistoricalDataModel> historicalDataModels) {
                Log.d(TAG, "onChanged: datachanged");
                ArrayList<Entry> values = new ArrayList<Entry>();
                for (int i= 0; i<historicalDataModels.size(); i++){
                    HistoricalDataModel data = historicalDataModels.get(i);
                    highestPrice = Math.max(data.getHigh(), highestPrice);
                    lowestPrices = Math.min(data.getLow(), lowestPrices);
                    float avePrice = (float) (data.getHigh() + data.getLow() / 2.0);
                    values.add(new Entry(i, avePrice));
                }
                setXAxis((float) historicalDataModels.size());
                setYAxis((float) lowestPrices, (float) highestPrice);
                setData(values);
            }
        });


    }

    private void initView() {

        symbol = findViewById(R.id.graph_symbol_name);
        askPrice = findViewById(R.id.graph_symbol_ask_price);
        bidPrice = findViewById(R.id.graph_symbol_bid_price);
        lastPrice = findViewById(R.id.graph_symbol_last_price);
        mCandleStickChart = findViewById(R.id.graph_candle_stick_chart);
        mCandleStickChart.setBackgroundColor(Color.WHITE);

        //后台绘制
        mCandleStickChart.setDrawGridBackground(false);
        //设置描述文本
        mCandleStickChart.getDescription().setEnabled(false);
        //设置支持触控手势
        mCandleStickChart.setTouchEnabled(true);
        //设置缩放
        mCandleStickChart.setDragEnabled(true);
        //设置推动
        mCandleStickChart.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        mCandleStickChart.setPinchZoom(true);

        mCandleStickChart.getAxisRight().setEnabled(false);


        //默认动画
        mCandleStickChart.animateX(10);
        //刷新
        //mChart.invalidate();

        // 得到这个文字
        Legend l = mCandleStickChart.getLegend();

        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);
    }

    //传递数据集
    private void setData(ArrayList<Entry> values) {
        if (mCandleStickChart.getData() != null && mCandleStickChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mCandleStickChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mCandleStickChart.getData().notifyDataChanged();
            mCandleStickChart.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "1M Historical Price");

            // 在这里设置线
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                //Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                //set1.setFillDrawable(drawable);
                set1.setFillColor(Color.WHITE);
            } else {
                set1.setFillColor(Color.YELLOW);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //谁知数据
            mCandleStickChart.setData(data);
        }
    }

    public void setXAxis(float XAxisMax) {
        XAxis xAxis = mCandleStickChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置x轴的最大值
        xAxis.setAxisMaximum(XAxisMax);
        //设置x轴的最小值
        xAxis.setAxisMinimum(0f);

    }

    public void setYAxis(float YAxisMin, float YAxisMax){
        YAxis leftAxis = mCandleStickChart.getAxisLeft();

        leftAxis.setAxisMaximum(YAxisMax);
        //y轴最小
        leftAxis.setAxisMinimum(YAxisMin);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);
    }


}