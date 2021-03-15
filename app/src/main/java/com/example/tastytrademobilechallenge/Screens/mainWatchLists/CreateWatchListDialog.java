package com.example.tastytrademobilechallenge.Screens.mainWatchLists;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.Screens.watchlistDetail.WatchListItemViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class CreateWatchListDialog extends Dialog implements View.OnClickListener {
    EditText createEt;
    Button createBtn;
    TextView cancelTv;
    CompositeDisposable mCompositeDisposable;
    WatchListItemViewModel mWatchListItemViewModel;

    public CreateWatchListDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_new_list);
        createEt = findViewById(R.id.dialog_create_new_list_et);
        createBtn = findViewById(R.id.dialog_create_new_list_btn);
        cancelTv = findViewById(R.id.dialog_create_new_list_tv);

        createBtn.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
        createBtn.setEnabled(false);
        mCompositeDisposable = new CompositeDisposable();

        createEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() == 0) {
                    createBtn.setEnabled(false);
                    createBtn.setBackgroundColor(createBtn.getContext().getResources().getColor(R.color.green_grey));
                } else {
                    createBtn.setEnabled(true);
                    createBtn.setBackgroundColor(createBtn.getContext().getResources().getColor(R.color.green));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setViewModel(WatchListItemViewModel viewModel) {
        mWatchListItemViewModel = viewModel;
    }

    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        Display display = window.getWindowManager().getDefaultDisplay();
        attributes.width = display.getWidth();
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_create_new_list_btn:
                mCompositeDisposable.add(mWatchListItemViewModel.addNewWatchList(new WatchList(createEt.getText().toString())));
                cancel();
                break;
            case R.id.dialog_create_new_list_tv:
                cancel();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
