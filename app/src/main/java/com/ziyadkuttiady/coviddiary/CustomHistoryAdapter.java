package com.ziyadkuttiady.coviddiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ItemHistory> item;
    private ArrayList<ItemHistory> originalItem;

    public CustomHistoryAdapter() {
        super();
    }

    public CustomHistoryAdapter(Context context, ArrayList<ItemHistory> item) {
        this.context = context;
        this.item = item;
        //this.originalItem = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (item.get(position).isSection()) {
            // if section header
            convertView = inflater.inflate(R.layout.layout_section_header, parent, false);
            TextView tvSectionTitle = (TextView) convertView.findViewById(R.id.tvSectionTitle);
            tvSectionTitle.setText(((CustomHistorySectionItem) item.get(position)).getTitle());
        } else {
            // if item
            convertView = inflater.inflate(R.layout.listview_single_item, parent, false);
            TextView tvItemTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);
            tvItemTitle.setText(((CustomHistoryEntryItem) item.get(position)).getTitle());
            TextView tvItemSubTitle = (TextView) convertView.findViewById(R.id.tvItemDesc);
            tvItemSubTitle.setText(((CustomHistoryEntryItem) item.get(position)).getTime());
        }

        return convertView;
    }


}