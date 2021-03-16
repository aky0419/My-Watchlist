package com.example.tastytrademobilechallenge.Screens.watchlistDetail;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.HistoryGraphActivity;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Screens.symbolTypeahead.AddItemActivity;
import com.example.tastytrademobilechallenge.Screens.watchlistDetail.adapters.ItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ItemDetailsActivity extends AppCompatActivity implements ItemRecyclerViewAdapter.OnSymbolListener {
    private static final String TAG = "ItemDetailsActivity";

    TextView listNameTv, listCountTv;
    ImageButton addItemBtn;
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

        compositeDisposable = new CompositeDisposable();

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActivity.this, AddItemActivity.class);
                intent.putExtra("watchListName", listName);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        listName = intent.getStringExtra("watchListName");
        listNameTv.setText(listName);
        itemsList = new ArrayList<>();

        watchListItemViewModel = new ViewModelProvider(this).get(WatchListItemViewModel.class);

        mWatchListWithSymbols = watchListItemViewModel.getSymbolsFromOneWatchList(listName).getValue();

        itemsRv.setLayoutManager(new LinearLayoutManager(this));
        symbolListAdapter = new ItemRecyclerViewAdapter(this, this);
        itemsRv.setAdapter(symbolListAdapter);

        watchListItemViewModel
                .getSymbolsFromOneWatchList(listName)
                .observe(this, new Observer<WatchListWithSymbols>() {
                    @Override
                    public void onChanged(WatchListWithSymbols watchListWithSymbols) {
                        symbolListAdapter.setSymbols(watchListWithSymbols);
                        mWatchListWithSymbols = watchListWithSymbols;
                        listCountTv.setText(watchListWithSymbols.symbols.size() + " items");
                        watchListItemViewModel.lastWatchListWithSymbols = watchListWithSymbols;
                    }
                });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                watchListItemViewModel.deleteSymbol(mWatchListWithSymbols.symbols.get(viewHolder.getAdapterPosition()));
                //need to delete crossref
                //watchListItemViewModel.deleteWatchListSymbolCrossRef();

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
}