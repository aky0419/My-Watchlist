package com.example.tastytrademobilechallenge;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tastytrademobilechallenge.Models.HistoricalDataModel;
import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.Screens.watchlistDetail.WatchListItemViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class HistoryGraphActivity extends AppCompatActivity {
    private static final String TAG = "HistoryGraphActivity";
    TextView symbol, askPrice, bidPrice, lastPrice;
    LineChart mCandleStickChart;
    HistoryGraphViewModel mHistoryGraphViewModel;
    String symbolName;
    CompositeDisposable compositeDisposable;
    WatchListWithSymbols mWatchListWithSymbols;
    WatchListItemViewModel watchListItemViewModel;

    private LineDataSet set1;
    double highestPrice;
    double lowestPrices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //初始化View
        initView();
        Intent intent = getIntent();
        highestPrice = Double.MIN_VALUE;
        lowestPrices = Double.MAX_VALUE;
        symbolName = intent.getStringExtra("symbol");
        symbol.setText(symbolName);
        compositeDisposable = new CompositeDisposable();



        mHistoryGraphViewModel = new ViewModelProvider(this).get(HistoryGraphViewModel.class);
        mHistoryGraphViewModel.getSymbol(symbolName).observe(this, new Observer<Symbol>() {
            @Override
            public void onChanged(Symbol symbol) {
                askPrice.setText(String.valueOf(symbol.getAskPrice()));
                bidPrice.setText(String.valueOf(symbol.getBidPrice()));
                lastPrice.setText(String.valueOf(symbol.getLastPrice()));
            }
        });
        mHistoryGraphViewModel.getHistoricalDataList(symbolName);
        mHistoryGraphViewModel.historicalDataList.observe(this, new Observer<List<HistoricalDataModel>>() {
            @Override
            public void onChanged(List<HistoricalDataModel> historicalDataModels) {
                Log.d(TAG, "onChanged: dataChanged");
                ArrayList<String> xValues = new ArrayList<String>();
                ArrayList<Entry> values = new ArrayList<>();
                for (int i = historicalDataModels.size() -1; i > 0; i--) {
                    HistoricalDataModel data = historicalDataModels.get(i);
                    highestPrice = Math.max(data.getHigh(), highestPrice);
                    lowestPrices = Math.min(data.getLow(), lowestPrices);
                    float avePrice = (float) (data.getHigh() + data.getLow() / 2.0);
                    values.add(new Entry(i, avePrice));
                    xValues.add(data.getDate());
                }

                setData(values);
//                setXAxis((float) historicalDataModels.size());
//                setYAxis((float) lowestPrices, (float) highestPrice);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        symbol = findViewById(R.id.graph_symbol_name);
        askPrice = findViewById(R.id.graph_symbol_ask_price);
        bidPrice = findViewById(R.id.graph_symbol_bid_price);
        lastPrice = findViewById(R.id.graph_symbol_last_price);
        mCandleStickChart = findViewById(R.id.graph_candle_stick_chart);
        mCandleStickChart.setBackgroundColor(Color.WHITE);

        XAxis xAxis = mCandleStickChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置x轴的最大值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mCandleStickChart.getLegend().setEnabled(false);// 不显示图例
        mCandleStickChart.getDescription().setEnabled(false);// 不显示描述
        mCandleStickChart.setScaleEnabled(false);   // 取消缩放
        mCandleStickChart.setNoDataText("暂无数据");

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
        mCandleStickChart.invalidate();

        // 得到这个文字
        Legend l = mCandleStickChart.getLegend();

        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);
        mCandleStickChart.setData(new LineData());


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
//            set1.enableDashedLine(10f, 5f, 0f);
//            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(4f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(6f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(2f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 6f}, 0f));
            set1.setFormSize(16.f);
            set1.setFillColor(Color.WHITE);
            LineData data = new LineData(set1);
            mCandleStickChart.setData(data);
            mCandleStickChart.invalidate();
        }
    }

    public void setXAxis(float XAxisMax) {
        XAxis xAxis = mCandleStickChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置x轴的最大值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(XAxisMax);
        //设置x轴的最小值
        xAxis.setAxisMinimum(0f);


    }

    public void setYAxis(float YAxisMin, float YAxisMax) {
        YAxis leftAxis = mCandleStickChart.getAxisLeft();

        leftAxis.setAxisMaximum(YAxisMax);
        //y轴最小
        leftAxis.setAxisMinimum(YAxisMin);

//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//        leftAxis.setDrawLimitLinesBehindData(true);
    }


}