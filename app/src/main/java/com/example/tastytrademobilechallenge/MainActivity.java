package com.example.tastytrademobilechallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.tastytrademobilechallenge.DataBase.DBManger;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ExpandableListView watchListLv;
    ImageButton addWatchListBtn;
    List<String> watchList;
    HashMap<String, List<String>> itemList;

    WatchListAdapter mWatchListAdapter;

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

        Data data = new Data.Builder().putStringArray("symbolsList", new String[]{"AAPL"}).build();
        Constraints constraints = new Constraints.Builder().setRequiresBatteryNotLow(true).build();
        //create periodic work request
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class, 5, TimeUnit.SECONDS)
                .setInputData(data)
                .setConstraints(constraints)
                .addTag("download")
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
        WorkManager.getInstance(this).getWorkInfosByTagLiveData("download").observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        insertDataFromDB();

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
}