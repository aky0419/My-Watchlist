package com.example.tastytrademobilechallenge.Screens.watchlistDetail;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.Models.WatchListSymbolCrossRef;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Screens.HistroryGraphScreen.HistoryGraphActivity;
import com.example.tastytrademobilechallenge.Screens.symbolTypeahead.AddItemActivity;
import com.example.tastytrademobilechallenge.Screens.watchlistDetail.adapters.ItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ItemDetailsActivity extends AppCompatActivity implements ItemRecyclerViewAdapter.OnSymbolListener {
    private static final String TAG = "ItemDetailsActivity";

    TextView listNameTv, listCountTv, symbolTextTv, askTextTv, bidTextTv, lastTextTv;
    ImageButton addItemBtn, deleteListBtn;
    RecyclerView itemsRv;
    ItemRecyclerViewAdapter symbolListAdapter;
    List<String> itemsList;
    CompositeDisposable compositeDisposable;

    WatchListWithSymbols mWatchListWithSymbols;
    WatchListItemViewModel watchListItemViewModel;
    String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listNameTv = findViewById(R.id.item_detail_name_tv);
        listCountTv = findViewById(R.id.item_detail_count_tv);
        addItemBtn = findViewById(R.id.item_detail_add_item_btn);
        itemsRv = findViewById(R.id.item_detail_rv);
        symbolTextTv = findViewById(R.id.symbolText);
        askTextTv = findViewById(R.id.askText);
        bidTextTv = findViewById(R.id.bidText);
        lastTextTv = findViewById(R.id.lastText);
        deleteListBtn = findViewById(R.id.item_detail_delete_list_btn);

        compositeDisposable = new CompositeDisposable();

        addItemBtn.setOnClickListener(this::onClick);
        deleteListBtn.setOnClickListener(this::onClick);

        Intent intent = getIntent();
        listName = intent.getStringExtra("watchListName");
        listNameTv.setText(listName);
        itemsList = new ArrayList<>();

        watchListItemViewModel = new ViewModelProvider(this).get(WatchListItemViewModel.class);

        itemsRv.setLayoutManager(new LinearLayoutManager(this));
        symbolListAdapter = new ItemRecyclerViewAdapter(this, this);
        itemsRv.setAdapter(symbolListAdapter);

        watchListItemViewModel
                .getSymbolsFromOneWatchList(listName)
                .observe(this, new Observer<WatchListWithSymbols>() {
                    @Override
                    public void onChanged(WatchListWithSymbols watchListWithSymbols) {
                        if (watchListWithSymbols != null) {
                            if (watchListWithSymbols.symbols.size() == 0) {
                                symbolTextTv.setVisibility(View.GONE);
                                askTextTv.setVisibility(View.GONE);
                                bidTextTv.setVisibility(View.GONE);
                                lastTextTv.setVisibility(View.GONE);
                                listCountTv.setText("0 items");
                            } else {
                                symbolTextTv.setVisibility(View.VISIBLE);
                                askTextTv.setVisibility(View.VISIBLE);
                                bidTextTv.setVisibility(View.VISIBLE);
                                lastTextTv.setVisibility(View.VISIBLE);
                                listCountTv.setText(watchListWithSymbols.symbols.size() + " items");
                            }
                            symbolListAdapter.setSymbols(watchListWithSymbols);
                            mWatchListWithSymbols = watchListWithSymbols;
                            watchListItemViewModel.lastWatchListWithSymbols = watchListWithSymbols;
                        }
                    }
                });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                watchListItemViewModel.deleteWatchListSymbolCrossRef(new WatchListSymbolCrossRef(listName, mWatchListWithSymbols.symbols.get(viewHolder.getAdapterPosition()).symbol));
            }

            final Drawable icon = ContextCompat.getDrawable(ItemDetailsActivity.this, R.drawable.ic_baseline_delete_forever_24);
            final Drawable background = new ColorDrawable(Color.RED);

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;

                int iconLeft, iconRight, iconTop, iconBottom;
                int backTop, backBottom, backLeft, backRight;
                backTop = itemView.getTop();
                backBottom = itemView.getBottom();
                iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                iconBottom = iconTop + icon.getIntrinsicHeight();
                if (dX < 0) {
                    backRight = itemView.getRight();
                    backLeft = itemView.getRight() + (int) dX;
                    background.setBounds(backLeft, backTop, backRight, backBottom);
                    iconRight = itemView.getRight() - iconMargin;
                    iconLeft = iconRight - icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                } else {
                    background.setBounds(0, 0, 0, 0);
                    icon.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
                icon.draw(c);
            }
        }).attachToRecyclerView(itemsRv);


    }


    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable.add(watchListItemViewModel.fetchAndUpdatePrice());
        compositeDisposable.add(watchListItemViewModel.periodicallyFetchPrice());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onSymbolClick(int position) {
        Log.d(TAG, "onSymbolClick: clicked");
        Intent intent = new Intent(this, HistoryGraphActivity.class);
        intent.putExtra("symbol", mWatchListWithSymbols.symbols.get(position).symbol);
        startActivity(intent);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_detail_add_item_btn:
                Intent intent = new Intent(ItemDetailsActivity.this, AddItemActivity.class);
                intent.putExtra("watchListName", listName);
                startActivity(intent);

                break;

            case R.id.item_detail_delete_list_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete the watchList?")
                        .setPositiveButton("Cancel", null)
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                watchListItemViewModel.deleteWatchList(mWatchListWithSymbols.mWatchList);
                                finish();
                            }
                        }).create().show();


        }
    }
}