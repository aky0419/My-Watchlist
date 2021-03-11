package com.example.tastytrademobilechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ExpandableListView watchListLv;
    FloatingActionButton addWatchListBtn;
    List<String> watchList;
    HashMap<String, List<String>> itemList;

    WatchListAdapter mWatchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watchListLv = findViewById(R.id.main_lv);
        addWatchListBtn = findViewById(R.id.main_fab);
        watchList = new ArrayList<>();
        itemList = new HashMap<>();
            watchList.add("My First List");
            itemList.put(watchList.get(0), new ArrayList<>());
            itemList.get(watchList.get(0)).add("AAPL");
         itemList.get(watchList.get(0)).add("MSFT");
        itemList.get(watchList.get(0)).add("MSFT");
        itemList.get(watchList.get(0)).add("MSFT");
        itemList.get(watchList.get(0)).add("MSFT");
        itemList.get(watchList.get(0)).add("MSFT");


        mWatchListAdapter = new WatchListAdapter(this, watchList, itemList);
        watchListLv.setAdapter(mWatchListAdapter);

        addWatchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a dialog to create new list

            }
        });
    }


}