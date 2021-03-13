package com.example.tastytrademobilechallenge.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tastytrademobilechallenge.Adapters.ItemRecyclerViewAdapter;
import com.example.tastytrademobilechallenge.DataBase.DBManger;
import com.example.tastytrademobilechallenge.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity {
    TextView listNameTv, listCountTv;
    ImageButton addItemBtn;
    RecyclerView itemsRv;
    ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    List<String> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listNameTv = findViewById(R.id.item_detail_name_tv);
        listCountTv = findViewById(R.id.item_detail_count_tv);
        addItemBtn = findViewById(R.id.item_detail_add_item_btn);
        itemsRv = findViewById(R.id.item_detail_rv);

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();

        String listName = intent.getStringExtra("listName");

        listNameTv.setText(listName);

        itemsList = new ArrayList<>();

        itemsRv.setLayoutManager(new LinearLayoutManager(this));
        itemsList.addAll(DBManger.getItemsList(listName));

        listCountTv.setText(itemsList.size() + " items");

        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(itemsList, this);
        itemsRv.setAdapter(itemRecyclerViewAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String itemToDelete = itemsList.get(viewHolder.getAdapterPosition());
                DBManger.deleteItemFromItemTb(itemToDelete);
                Snackbar.make(findViewById(R.id.item_detail_rv), "Deleted a symbol", Snackbar.LENGTH_SHORT).setAction("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBManger.addItem(listName, itemToDelete);

                    }
                }).show();

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}