package com.example.tastytrademobilechallenge.Screens.symbolTypeahead;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tastytrademobilechallenge.Screens.symbolTypeahead.adapters.SearchSymbolLvAdapter;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.R;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    EditText mEditText;
    ListView mListView;
    AddItemViewModel mAddItemViewModel;
    MutableLiveData<SymbolAutocompleteModel> symbols;
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


        mAddItemViewModel.symbols.observe(this, new Observer<List<SymbolAutocompleteModel>>() {
            @Override
            public void onChanged(List<SymbolAutocompleteModel> symbolAutocompleteModels) {
                mAdapter.setData(symbolAutocompleteModels);
            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    mAddItemViewModel.searchSymbols(charSequence.toString().trim());

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