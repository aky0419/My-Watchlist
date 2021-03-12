package com.example.tastytrademobilechallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tastytrademobilechallenge.DataBase.DBManger;

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

        listNameTv = findViewById(R.id.item_detail_name_tv);
        listCountTv = findViewById(R.id.item_detail_count_tv);
        addItemBtn = findViewById(R.id.item_detail_add_item_btn);
        itemsRv = findViewById(R.id.item_detail_rv);

        Intent intent = getIntent();

        String listName = intent.getStringExtra("listName");

        listNameTv.setText(listName);

        itemsList = new ArrayList<>();

        itemsRv.setLayoutManager(new LinearLayoutManager(this));
        itemsList.addAll(DBManger.getItemsList(listName));

        listCountTv.setText(itemsList.size() + " items");

        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(itemsList, this);
        itemsRv.setAdapter(itemRecyclerViewAdapter);



    }
}