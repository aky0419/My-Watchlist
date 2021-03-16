package com.example.tastytrademobilechallenge.Screens.watchlistDetail.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    WatchListWithSymbols mWatchListWithSymbols = new WatchListWithSymbols(new WatchList(""), new ArrayList<>());
    Context mContext;
    private OnSymbolListener mOnSymbolListener;

    public ItemRecyclerViewAdapter(Context mContext, OnSymbolListener onSymbolListener) {
        this.mContext = mContext;
        this.mOnSymbolListener = onSymbolListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_watchlist_lv, parent, false);
        return new ItemViewHolder(view, mOnSymbolListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Symbol symbol = mWatchListWithSymbols.symbols.get(position);
            itemViewHolder.symbol.setText(symbol.getSymbol());
            itemViewHolder.bidPrice.setText(String.valueOf(symbol.getBidPrice()));
            itemViewHolder.askPrice.setText(String.valueOf(symbol.getAskPrice()));
            itemViewHolder.lastPrice.setText(String.valueOf(symbol.getLastPrice()));
            if(symbol.isPositive()){
                itemViewHolder.symbol.setBackground(ContextCompat.getDrawable(mContext, R.drawable.symbol_green_bg));
            }else{
                itemViewHolder.symbol.setBackground(ContextCompat.getDrawable(mContext, R.drawable.symbol_red_bg));
            }
    }

    @Override
    public int getItemCount() {
        if (mWatchListWithSymbols == null) return 0;
        return mWatchListWithSymbols.symbols.size();
    }

    public void setSymbols(WatchListWithSymbols watchListWithSymbols) {
        this.mWatchListWithSymbols = watchListWithSymbols;
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView symbol;
        TextView lastPrice;
        TextView bidPrice;
        TextView askPrice;
        OnSymbolListener mOnSymbolListener;

        public ItemViewHolder(View itemView, OnSymbolListener onSymbolListener) {
            super(itemView);
            symbol = itemView.findViewById(R.id.item_watchlistlv_symbol);
            lastPrice = itemView.findViewById(R.id.item_watchlistlv_last);
            bidPrice = itemView.findViewById(R.id.item_watchlistlv_bid);
            askPrice = itemView.findViewById(R.id.item_watchlistlv_ask);
            mOnSymbolListener = onSymbolListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnSymbolListener.onSymbolClick(getAdapterPosition());
        }
    }

    public interface OnSymbolListener{
        void onSymbolClick(int position);
    }
}
