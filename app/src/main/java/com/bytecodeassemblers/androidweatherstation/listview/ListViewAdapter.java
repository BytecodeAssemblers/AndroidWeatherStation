package com.bytecodeassemblers.androidweatherstation.listview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bytecodeassemblers.androidweatherstation.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter {

    private final ArrayList<JSONObject> mData;

     public ListViewAdapter(JSONArray map) {
        mData = new ArrayList<>();
        for(int i=0; i<map.length(); i++) {
            try {
                mData.add(map.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public JSONObject getItem(int position) {
        return mData.get(position);
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

        JSONObject item = getItem(position);

        // TODO replace findViewById by ViewHolder
        TextView titles= result.findViewById(R.id.title);
        TextView desriptions = result.findViewById(R.id.description);

        try {
            titles.setText(item.getString("cityName"));
            desriptions.setText("Latitude: ");
            desriptions.append(String.valueOf(item.getDouble("lat")));
            desriptions.append("\nLongitude: ");
            desriptions.append(String.valueOf(item.getDouble("lon")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
