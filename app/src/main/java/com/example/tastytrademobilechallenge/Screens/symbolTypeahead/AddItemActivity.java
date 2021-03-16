package com.example.tastytrademobilechallenge.Screens.symbolTypeahead;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Screens.symbolTypeahead.adapters.SearchSymbolLvAdapter;

public class AddItemActivity extends AppCompatActivity {

    EditText mEditText;
    ListView mListView;
    AddItemViewModel mAddItemViewModel;
    SearchSymbolLvAdapter mAdapter;
    String listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEditText = findViewById(R.id.add_item_et);
        mListView = findViewById(R.id.add_item_lv);
        mAddItemViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);

        Intent intent = getIntent();
        listName = intent.getStringExtra("watchListName");

        mAdapter = new SearchSymbolLvAdapter(this, listName);
        mAdapter.setViewModel(mAddItemViewModel);
        mListView.setAdapter(mAdapter);

        mAddItemViewModel.getSymbolsFromOneWatchList(listName).observe(this, mAdapter::setExistingSymbolList);
        mAddItemViewModel.symbols.observe(this, mAdapter::setData);

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeOfRightDrawable = mEditText.getRight()
                            - mEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (motionEvent.getRawX() >= leftEdgeOfRightDrawable) {
                        mEditText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mAddItemViewModel.searchSymbolsByIEXApi(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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