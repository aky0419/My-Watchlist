package com.example.tastytrademobilechallenge;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.RetrofitApi.StockPriceService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    RecyclerView watchListRv;
    ImageButton addWatchListBtn;

    WatchListAdapter mWatchListAdapter;
    CompositeDisposable compositeDisposable;

//    private StockPriceService stockPriceService;
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

//        stockPriceService = new StockPriceService();

        compositeDisposable.add(watchListItemViewModel.addDefaultWatchListIfNotExist());

        watchListItemViewModel.getWatchListsWithSymbols().observe(this, new Observer<List<WatchListWithSymbols>>() {
            @Override
            public void onChanged(List<WatchListWithSymbols> watchListWithSymbols) {
                mWatchListAdapter.setNodes(watchListWithSymbols);
            }
        });
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