package com.example.tastytrademobilechallenge;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastytrademobilechallenge.Screens.AddItemActivity;
import com.example.tastytrademobilechallenge.Screens.ItemDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder> {
    List<WatchList> mWatchLists;
    Context mContext;

    List<WatchListWithSymbols> mWatchListWithSymbols;


    public WatchListAdapter( Context mContext) {

        this.mContext = mContext;
        this.mWatchListWithSymbols = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_lv, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, ItemDetailsActivity.class);
                        intent.putExtra("watchListName", holder.listName.getText());
                        holder.itemView.getContext().startActivity(intent);

                    }
                }
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WatchListWithSymbols watchList = mWatchListWithSymbols.get(position);
        holder.listName.setText(watchList.mWatchList.getName());
        holder.itemCount.setText(String.valueOf(this.mWatchListWithSymbols.get(position).symbols.size() + " items"));

    }

    @Override
    public int getItemCount() {
        return this.mWatchListWithSymbols.size();
        // return mWatchLists.size();
    }

    public void setNodes(List<WatchListWithSymbols> watchLists){
        mWatchListWithSymbols = watchLists;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView listName, itemCount;

        public ViewHolder(View itemView){
            super(itemView);
            listName = itemView.findViewById(R.id.item_mainlv_title);
            itemCount = itemView.findViewById(R.id.item_mainlv_item_count);
        }


    }
}
