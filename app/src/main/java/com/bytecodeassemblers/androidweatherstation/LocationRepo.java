package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;

public class LocationRepo {


    private Activity activity;
    private static HashMap<String, LatLng> locationInventory = new HashMap<>();


    public LocationRepo(Activity activity){
        this.activity = activity;
    }


    public void addLocationReg(String string, LatLng latLng) {
        locationInventory.put(string, latLng);
        saveMap(locationInventory);
    }


    public static void setLocationRepo(HashMap<String, LatLng> locationRepo) {
        LocationRepo.locationInventory = locationRepo;
    }


    public  HashMap<String, LatLng> getLocationRepo() {
        return new HashMap<String, LatLng>(locationInventory);
    }


    public LatLng searchLocationReg(String string){
        LatLng latLng = null ;
        if (locationInventory.containsKey(string)){
            latLng = locationInventory.get(string);
        }
        return latLng;
    }


     public HashMap<String, LatLng> loadMap() {  //set LocationRepo's hashmap from Saved Shared Preferences
        SharedPreferences prefs = activity.getSharedPreferences("MyVariables", activity.MODE_PRIVATE);
        Gson gson = new Gson();
        HashMap<String, LatLng> hashmap = new HashMap<String, LatLng>();
        String storedHashMapString = prefs.getString("My_map", "oopsDintWork");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, LatLng>>() {
        }.getType();
        hashmap = gson.fromJson(storedHashMapString, type);
        setLocationRepo(hashmap);
        return  hashmap;
    }


    public void saveMap(HashMap<String,LatLng> inputMap){  //save hashmap in shared preferences
        Gson gson = new Gson();
        SharedPreferences pSharedPref = activity.getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            String jsonString = gson.toJson(inputMap);
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map", jsonString);
            editor.commit();
        }
    }
}
