package com.ziyadkuttiady.coviddiary;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomHistoryAdapter extends BaseAdapter {
    DataBaseHelper dataBaseHelper;
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

            ImageView categoryIcon = convertView.findViewById(R.id.category_icon);
            dataBaseHelper = new DataBaseHelper(context);
//
            Cursor cursor = dataBaseHelper.getData(item.get(position).getId());
            if (cursor.moveToNext()) {
                switch (cursor.getString(10)) {
                    case "Bike":
                        categoryIcon.setImageResource(R.drawable.ic_motorcycle);
                        break;
                    case "Bus":
                        categoryIcon.setImageResource(R.drawable.ic_bus);
                        break;
                    case "Car":
                        categoryIcon.setImageResource(R.drawable.ic_car);
                        break;
                    case "Jeep":
                        categoryIcon.setImageResource(R.drawable.ic_jeep);
                        break;
                    case "Cycle":
                        categoryIcon.setImageResource(R.drawable.ic_cycle);
                        break;
                    case "Walk":
                        categoryIcon.setImageResource(R.drawable.ic_walk);
                        break;
                    case "Train":
                        categoryIcon.setImageResource(R.drawable.ic_train);
                        break;
                    case "Aeroplane":
                        categoryIcon.setImageResource(R.drawable.ic_airplane);
                        break;
                    case "Truck":
                        categoryIcon.setImageResource(R.drawable.ic_truck);
                        break;
                    case "Auto Rickshaw":
                        categoryIcon.setImageResource(R.drawable.ic_autorickshaw);
                        break;
                }

            }

        }

        return convertView;
    }


}