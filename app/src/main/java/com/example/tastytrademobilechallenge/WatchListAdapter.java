package com.example.tastytrademobilechallenge;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tastytrademobilechallenge.DataBase.DBManger;

import java.util.HashMap;
import java.util.List;

class WatchListAdapter extends BaseExpandableListAdapter {


    Context mContext;
    List<String> watchList;
    // it should be list of StockModel
    HashMap<String, List<String>> listItems;

    public WatchListAdapter(Context context, List<String> watchList, HashMap<String, List<String>> listItems) {
        mContext = context;
        this.watchList = watchList;
        this.listItems = listItems;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public int getGroupCount() {
        return watchList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listItems.get(watchList.get(groupPosition)).size() == 0) {
            return 0;
        }
        return listItems.get(watchList.get(groupPosition)).size() + 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return watchList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listItems.get(watchList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_main_lv, null);
            groupViewHolder = new GroupViewHolder(view);
            view.setTag(groupViewHolder);

        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.watchListName.setText(watchList.get(groupPosition));
        //item count to be added from database
        groupViewHolder.itemCount.setText(DBManger.getItemsCount(watchList.get(groupPosition)) + " items");

        int imageResourceId = isExpanded ? android.R.drawable.arrow_down_float : android.R.drawable.arrow_up_float;
        if (this.getChildrenCount(groupPosition) <= 1) {
            groupViewHolder.expandBtn.setVisibility(View.GONE);
        }
        groupViewHolder.expandBtn.setImageResource(imageResourceId);
        groupViewHolder.expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded) {
                    ((ExpandableListView) viewGroup).collapseGroup(groupPosition);
                } else {
                    ((ExpandableListView) viewGroup).expandGroup(groupPosition, true);
                }
            }
        });
        groupViewHolder.expandBtn.setFocusable(false);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder = null;
        if (childPosition == 0) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandablelv_header, null);
            return view;
        }
        String childItem = (String) getChild(groupPosition, childPosition - 1);

        if (view != null) {
            childViewHolder = (ChildViewHolder) view.getTag();
        }

        if (childViewHolder == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_watchlist_lv, null);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        }

        childViewHolder.symbol.setText(childItem);

        return view;
    }

//    public View getChildView(int groupPosition, int childPosition,
//                             boolean isLastChild, View convertView, ViewGroup parent) {
//        if(childPosition == 0)return convertView = mInflater.inflate(R.layout.child_header, null);
//
//        // A ViewHolder keeps references to children views to avoid unneccessary calls
//        // to findViewById() on each row.
//        ChildViewHolder holder = null;
//
//        ChildItem childItem = (ChildItem) getChild(groupPosition, childPosition-1);
//
//        //Get ViewHolder first
//        if(convertView!=null){
//            holder = (ChildViewHolder) convertView.getTag();
//        }
//
//        //If no ViewHolder, then create a new child row as convertView is probably a header
//        if(holder == null){
//            convertView = mInflater.inflate(R.layout.child_row, null);
//            holder = new ChildViewHolder(convertView);
//            convertView.setTag(holder);
//        }
//        holder.getTextLabel().setText(childItem.name);
//        holder.getPriceLabel().setText(String.valueOf(childItem.price));
//        return convertView;
//    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView watchListName;
        TextView itemCount;
        ImageButton expandBtn;

        public GroupViewHolder(View itemView) {
            watchListName = itemView.findViewById(R.id.item_mainlv_title);
            itemCount = itemView.findViewById(R.id.item_mainlv_item_count);
            expandBtn = itemView.findViewById(R.id.item_mainlv_expand_btn);
        }
    }

    class ChildViewHolder {
        TextView symbol;
        TextView lastPrice;
        TextView bidPrice;
        TextView askPrice;

        public ChildViewHolder(View itemView) {
            symbol = itemView.findViewById(R.id.item_watchlistlv_symbol);
            lastPrice = itemView.findViewById(R.id.item_watchlistlv_last);
            bidPrice = itemView.findViewById(R.id.item_watchlistlv_bid);
            askPrice = itemView.findViewById(R.id.item_watchlistlv_ask);
        }
    }
}
