package com.bytecodeassemblers.androidweatherstation.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.bytecodeassemblers.androidweatherstation.Common;
import com.bytecodeassemblers.androidweatherstation.MainActivity;
import com.bytecodeassemblers.androidweatherstation.MainActivityController;
import com.bytecodeassemblers.androidweatherstation.MimageLoader;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.weather_service.WeatherBitTask;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class ListViewActivity extends AppCompatActivity {

    private static final String TAG="ListViewActivity";


    private ListView list;
    private ListViewAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent intent = getIntent();
        HashMap<String, LatLng> locationInventory = (HashMap<String, LatLng>) intent.getSerializableExtra("map");

        myAdapter = new ListViewAdapter(locationInventory);

          list = findViewById(R.id.listView);
          list.setAdapter(myAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                double lat = myAdapter.getItem(position).getValue().latitude;
                double lon = myAdapter.getItem(position).getValue().longitude;

                Intent intent = new Intent(ListViewActivity.this, MainActivity.class);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lon);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


    }
}
