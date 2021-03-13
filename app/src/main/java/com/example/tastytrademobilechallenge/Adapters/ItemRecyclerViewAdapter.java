package com.example.tastytrademobilechallenge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.R;

import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    List<String> itemsList;
    Context mContext;

    public ItemRecyclerViewAdapter(List<String> itemsList, Context mContext) {
        this.itemsList = itemsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_watchlist_lv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemsList.get(position);
        holder.symbol.setText(item);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView symbol;
        TextView lastPrice;
        TextView bidPrice;
        TextView askPrice;

        public ViewHolder(View itemView){
            super(itemView);
            symbol = itemView.findViewById(R.id.item_watchlistlv_symbol);
            lastPrice = itemView.findViewById(R.id.item_watchlistlv_last);
            bidPrice = itemView.findViewById(R.id.item_watchlistlv_bid);
            askPrice = itemView.findViewById(R.id.item_watchlistlv_ask);
        }


    }
}
