package com.bytecodeassemblers.androidweatherstation.listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bytecodeassemblers.androidweatherstation.Common;
import com.bytecodeassemblers.androidweatherstation.LocationRepo;
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
    private HashMap<String, LatLng> locationInventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent intent = getIntent();
        locationInventory = (HashMap<String, LatLng>) intent.getSerializableExtra("map");  //get hashmap LocationRepo from MainActivityController

        myAdapter = new ListViewAdapter(locationInventory);  // pass hashmap to Adapter

          list = findViewById(R.id.listView);
          list.setAdapter(myAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {    // execute the apis requests with selected items lat and lot.
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


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {  //Delete item from listview and update the changes (runs on longclick of an item)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(ListViewActivity.this).setTitle("Are you sure?")
                        .setMessage("Do you want to delete this item?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locationInventory.remove(myAdapter.getItem(position).getKey());
                        myAdapter = new ListViewAdapter(locationInventory);
                        list.setAdapter(myAdapter);
                        LocationRepo.setLocationRepo(locationInventory);
                    }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });

    }
}
