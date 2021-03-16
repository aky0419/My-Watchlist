package com.example.tastytrademobilechallenge.Screens.symbolTypeahead.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.SymbolAutocompleteModel;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;
import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Screens.symbolTypeahead.AddItemActivity;
import com.example.tastytrademobilechallenge.Screens.symbolTypeahead.AddItemViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchSymbolLvAdapter extends BaseAdapter {
    Context mContext;
    List<SymbolAutocompleteModel> symbols = new ArrayList<>();
    AddItemViewModel mAddItemViewModel;
    String listName;
    Set<String> existingSymbols;

    public SearchSymbolLvAdapter(Context context, String listName) {
        mContext = context;
        this.listName = listName;
        existingSymbols = new HashSet<>();
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
        holder.company.setText(symbols.get(i).getSecurityName());
        if (existingSymbols.contains(symbols.get(i).getSymbol())) {
            holder.addBtn.setImageResource(R.drawable.ic_baseline_check_24);
            holder.addBtn.setEnabled(false);
            holder.symbol.setTextColor(Color.GRAY);
            holder.company.setTextColor(Color.GRAY);
        } else {
            holder.addBtn.setImageResource(R.drawable.ic_baseline_add_24);
            holder.addBtn.setEnabled(true);
            holder.symbol.setTextColor(Color.BLACK);
            holder.company.setTextColor(Color.BLACK);
            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Symbol symbol = new Symbol(symbols.get(i).getSymbol(), 0, 0, 0, 0);
                    mAddItemViewModel.addTicker(symbol, listName);
                    ((AddItemActivity) mContext).finish();
                }
            });
        }

        return view;
    }

    public void setViewModel(AddItemViewModel vm) {
        mAddItemViewModel = vm;
    }

    public void setData(List<SymbolAutocompleteModel> symbolAutocompleteModels) {
        symbols = symbolAutocompleteModels;
        notifyDataSetChanged();
    }

    public void setExistingSymbolList(WatchListWithSymbols watchListWithSymbols) {
        this.existingSymbols = watchListWithSymbols.symbols.stream().map(symbol -> symbol.getSymbol()).collect(Collectors.toSet());
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
