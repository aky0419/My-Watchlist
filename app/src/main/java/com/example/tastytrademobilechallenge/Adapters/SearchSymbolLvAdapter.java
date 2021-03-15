package com.example.tastytrademobilechallenge.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tastytrademobilechallenge.AddItemViewModel;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Screens.AddItemActivity;
import com.example.tastytrademobilechallenge.Screens.ItemDetailsActivity;
import com.example.tastytrademobilechallenge.Symbol;
import com.example.tastytrademobilechallenge.WatchListItemViewModel;
import com.example.tastytrademobilechallenge.WatchListSymbolCrossRef;

import java.util.ArrayList;
import java.util.List;

public class SearchSymbolLvAdapter extends BaseAdapter {
    Context mContext;
    List<SymbolAutocompleteModel> symbols = new ArrayList<>();
    AddItemViewModel mAddItemViewModel;
    String listName;

    public SearchSymbolLvAdapter(Context context, String listName) {
        mContext = context;
        this.listName = listName;
    }

    @Override
    public int getCount() {
        return symbols.size();
    }

    @Override
    public Object getItem(int i) {
        return symbols.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_add_symbol_lv, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.symbol.setText(symbols.get(i).getSymbol());
        holder.company.setText(symbols.get(i).getName());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Symbol symbol = new Symbol(symbols.get(i).getSymbol(), 0, 0, 0);
                mAddItemViewModel.addTicket(symbol, listName);
                ((AddItemActivity)mContext).finish();
            }
        });
        return view;
    }

    public void setViewModel(AddItemViewModel vm) {
        mAddItemViewModel = vm;
    }

    public void setData(List<SymbolAutocompleteModel> symbolAutocompleteModels) {
        symbols = symbolAutocompleteModels;
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView symbol, company;
        ImageButton addBtn;

        public ViewHolder(View view) {
            symbol = view.findViewById(R.id.item_searchlv_title);
            company = view.findViewById(R.id.item_searchlv_company_name);
            addBtn = view.findViewById(R.id.item_searchlv_add_item_btn);
        }
    }
}
