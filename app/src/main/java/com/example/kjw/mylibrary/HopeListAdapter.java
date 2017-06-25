package com.example.kjw.mylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2017-06-25.
 */

public class HopeListAdapter extends BaseAdapter {
    private ArrayList<HopeListItem> listItemList = new ArrayList<HopeListItem>();
    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hopebook_listitem, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.hopelistItem_name);
        TextView date = (TextView)convertView.findViewById(R.id.hopelistItem_date);
        TextView num = (TextView)convertView.findViewById(R.id.hopelistItem_num);
        TextView status = (TextView)convertView.findViewById(R.id.hopelistItem_status);

        HopeListItem item = listItemList.get(position);
        name.setText(item.getName());
        date.setText(item.getDate());
        num.setText(item.getNum());
        status.setText(item.getStatus());
        return convertView;
    }

    public void addItem(String date, String name, String status){
        HopeListItem item = new HopeListItem(date, name, status);
        listItemList.add(item);
    }
}
