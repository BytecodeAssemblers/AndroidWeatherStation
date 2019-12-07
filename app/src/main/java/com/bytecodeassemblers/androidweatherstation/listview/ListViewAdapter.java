package com.bytecodeassemblers.androidweatherstation.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bytecodeassemblers.androidweatherstation.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter {

    private final ArrayList mData;

    public ListViewAdapter(Map<String, LatLng> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, LatLng> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        if (convertView == null) {
             result = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, LatLng> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        TextView titles= result.findViewById(R.id.title);
        TextView desriptions = result.findViewById(R.id.description);

        titles.setText(item.getKey());
        desriptions.setText(String.valueOf(item.getValue()));

        return result;
    }
}
