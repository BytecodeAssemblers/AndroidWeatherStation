package com.bytecodeassemblers.androidweatherstation.listview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.bytecodeassemblers.androidweatherstation.LocationRepo;
import com.bytecodeassemblers.androidweatherstation.MainActivity;
import com.bytecodeassemblers.androidweatherstation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListViewActivity extends AppCompatActivity {

    private static final String TAG="ListViewActivity";


    private ListView list;
    private ListViewAdapter myAdapter;
    private JSONArray locationInventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent intent = getIntent();
        try {
            locationInventory = new JSONArray(intent.getStringExtra("map"));  //get hashmap LocationRepo from MainActivityController
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myAdapter = new ListViewAdapter(locationInventory);// pass hashmap to Adapter

          list = findViewById(R.id.listView);
          list.setAdapter(myAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {    // execute the apis requests with selected items lat and lot.
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                double lat = 0;
                double lon = 0;
                try {
                    lat = myAdapter.getItem(position).getDouble("lat");
                    lon = myAdapter.getItem(position).getDouble("lon");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                        locationInventory.remove(position);
                        myAdapter = new ListViewAdapter(locationInventory);
                        list.setAdapter(myAdapter);

                        saveMap(locationInventory);
                        LocationRepo.setLocationRepo(locationInventory);

                    }
                }).setNegativeButton("No",null).show();
                return true;
            }
        });

    }

    public void saveMap(JSONArray inputMap){  //save hashmap in shared preferences
        SharedPreferences pSharedPref = getApplicationContext().getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map", inputMap.toString());
            editor.commit();
        }
    }
}
