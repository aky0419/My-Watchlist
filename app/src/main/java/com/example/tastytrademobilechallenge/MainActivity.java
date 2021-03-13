package com.example.tastytrademobilechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tastytrademobilechallenge.DataBase.DBManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    ExpandableListView watchListLv;
    ImageButton addWatchListBtn;
    List<String> watchList;
    HashMap<String, List<String>> itemList;

    WatchListAdapter mWatchListAdapter;
    CompositeDisposable compositeDisposable;

    private StockPriceService stockPriceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watchListLv = findViewById(R.id.main_lv);
        addWatchListBtn = findViewById(R.id.main_imagebtn);
        watchList = new ArrayList<>();
        itemList = new HashMap<>();
        // need to change to real data


        mWatchListAdapter = new WatchListAdapter(this, watchList, itemList);
        watchListLv.setAdapter(mWatchListAdapter);

        watchListLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ItemDetailsActivity.class);
                intent.putExtra("listName", watchList.get(i));
                startActivity(intent);
                return true;
            }
        });

        watchListLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return false;
            }
        });
        compositeDisposable = new CompositeDisposable();
        stockPriceService = new StockPriceService();
    }

    private void updateUI() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        insertDataFromDB();
        fetchData();
    }

    private void fetchData() {
        Log.d(TAG, "fetchData: fetch data from api");
        compositeDisposable.add(Observable.interval(0, 5, TimeUnit.SECONDS)
                .flatMap((Function<Long, ObservableSource<?>>) aLong -> stockPriceService.getResponse("AAPL", "pk_c3ce2b10dc92443a8eb298e501c2121a"))
                .observeOn(AndroidSchedulers.mainThread()).subscribe(result -> {
                    List<StockPriceModel> stockPriceModels = ((List<StockPriceModel>) result);
                    updateUI();
                }));
    }

    public void insertDataFromDB() {
        List<String> watchListFromDB = DBManger.getWatchLists();
        watchList.clear();
        itemList.clear();
        watchList.addAll(watchListFromDB);
        for (String list : watchListFromDB) {
            itemList.put(list, new ArrayList<>());
            itemList.get(list).addAll(DBManger.getItemsList(list));
        }
        // this does not work, need to check how to get it work
        mWatchListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_imagebtn:
                CreateWatchListDialog dialog = new CreateWatchListDialog(this);
                dialog.show();
                dialog.setDialogSize();
                break;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}