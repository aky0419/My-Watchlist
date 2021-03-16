package com.example.tastytrademobilechallenge.Screens.mainWatchLists;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Screens.mainWatchLists.adapters.WatchListAdapter;
import com.example.tastytrademobilechallenge.Screens.watchlistDetail.WatchListItemViewModel;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    RecyclerView watchListRv;
    ImageButton addWatchListBtn;

    WatchListAdapter mWatchListAdapter;
    CompositeDisposable compositeDisposable;

    WatchListItemViewModel watchListItemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        watchListRv = findViewById(R.id.main_lv);
        addWatchListBtn = findViewById(R.id.main_imagebtn);

        compositeDisposable = new CompositeDisposable();


        mWatchListAdapter = new WatchListAdapter(this);
        watchListRv.setAdapter(mWatchListAdapter);

        watchListRv.setLayoutManager(new LinearLayoutManager(this));
        watchListItemViewModel = new ViewModelProvider(this).get(WatchListItemViewModel.class);

        compositeDisposable.add(watchListItemViewModel.addDefaultWatchListIfNotExist());

        watchListItemViewModel.getWatchListsWithSymbols().observe(this, mWatchListAdapter::setNodes);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_imagebtn:
                CreateWatchListDialog dialog = new CreateWatchListDialog(this);
                dialog.setViewModel(watchListItemViewModel);
                dialog.show();
                dialog.setDialogSize();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }
}