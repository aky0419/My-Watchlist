package com.example.tastytrademobilechallenge.Screens.HistroryGraphScreen;

import android.content.Intent;
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
import com.example.tastytrademobilechallenge.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class HistoryGraphActivity extends AppCompatActivity {
    private static final String TAG = "HistoryGraphActivity";
    TextView symbol, askPrice, bidPrice, lastPrice;
    LineChart mLineChart;
    HistoryGraphViewModel mHistoryGraphViewModel;
    String symbolName;
    MPLineChartManager mMPLineChartManager;
    
    double highestPrice;
    double lowestPrices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        mMPLineChartManager = new MPLineChartManager(mLineChart);
        mMPLineChartManager.intiChart();

        Intent intent = getIntent();
        highestPrice = Double.MIN_VALUE;
        lowestPrices = Double.MAX_VALUE;
        symbolName = intent.getStringExtra("symbol");
        symbol.setText(symbolName);

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
                getDataAndDrawGraph(historicalDataModels);
            }
        });
    }
    
    public void getDataAndDrawGraph(List<HistoricalDataModel> historicalDataModels){
        if (historicalDataModels != null) {
            Log.d(TAG, "onChanged: dataChanged");
            ArrayList<String> xValues = new ArrayList<String>();
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = 0; i < historicalDataModels.size(); i++) {
                HistoricalDataModel data = historicalDataModels.get(i);
                highestPrice = Math.max(data.getHigh(), highestPrice);
                lowestPrices = Math.min(data.getLow(), lowestPrices);
                float avePrice = (float) ((data.getHigh() + data.getLow()) / 2.0);
                values.add(new Entry(i, avePrice));
                xValues.add(data.getDate());
            }
            mMPLineChartManager.setData(values, xValues);
        }
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
        mLineChart = findViewById(R.id.graph_candle_stick_chart);
    }
}