package com.example.tastytrademobilechallenge.Screens.watchlistDetail.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.R;
import com.example.tastytrademobilechallenge.Models.Symbol;
import com.example.tastytrademobilechallenge.Models.WatchList;
import com.example.tastytrademobilechallenge.Models.WatchListWithSymbols;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    WatchListWithSymbols mWatchListWithSymbols = new WatchListWithSymbols(new WatchList(""), new ArrayList<>());
    Context mContext;

    public ItemRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_watchlist_lv, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_expandablelv_header, parent, false);
            return new HeaderViewHolder(view);
        }
        else return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Symbol symbol = mWatchListWithSymbols.symbols.get(position - 1);
            itemViewHolder.symbol.setText(symbol.getSymbol());
            itemViewHolder.bidPrice.setText(String.valueOf(symbol.getBidPrice()));
            itemViewHolder.askPrice.setText(String.valueOf(symbol.getAskPrice()));
            itemViewHolder.lastPrice.setText(String.valueOf(symbol.getLastPrice()));
        }

    }

    @Override
    public int getItemCount() {
        if (mWatchListWithSymbols.symbols == null || mWatchListWithSymbols.symbols.size() == 0) return 0;
        return mWatchListWithSymbols.symbols.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setSymbols(WatchListWithSymbols watchListWithSymbols) {
        this.mWatchListWithSymbols = watchListWithSymbols;
        notifyDataSetChanged();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView symbol;
        TextView lastPrice;
        TextView bidPrice;
        TextView askPrice;

        public ItemViewHolder(View itemView) {
            super(itemView);
            symbol = itemView.findViewById(R.id.item_watchlistlv_symbol);
            lastPrice = itemView.findViewById(R.id.item_watchlistlv_last);
            bidPrice = itemView.findViewById(R.id.item_watchlistlv_bid);
            askPrice = itemView.findViewById(R.id.item_watchlistlv_ask);
        }


    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
