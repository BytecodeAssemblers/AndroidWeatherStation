package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LocationRepo {


    private Activity activity;
    private static JSONArray locationInventory = new JSONArray();


    public LocationRepo(Activity activity){
        this.activity = activity;
    }


    public void addLocationReg(JSONObject latLng) {
        locationInventory.put(latLng);
        saveMap(locationInventory);
    }


    public static void setLocationRepo(JSONArray locationRepo) {
        LocationRepo.locationInventory = locationRepo;
    }

     public JSONArray loadMap() {  //set LocationRepo's hashmap from Saved Shared Preferences
        SharedPreferences prefs = activity.getSharedPreferences("MyVariables", activity.MODE_PRIVATE);
        JSONArray hashmap = null;
        String storedHashMapString = prefs.getString("My_map", "oopsDintWork");
         try {
             hashmap = new JSONArray(storedHashMapString);
             setLocationRepo(hashmap);
         } catch (JSONException e) {
             e.printStackTrace();
         }
        return hashmap;
    }


    public void saveMap(JSONArray inputMap){  //save hashmap in shared preferences
        SharedPreferences pSharedPref = activity.getApplicationContext().getSharedPreferences("MyVariables", Context.MODE_PRIVATE);
        if (pSharedPref != null){
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("My_map").commit();
            editor.putString("My_map", inputMap.toString());
            editor.commit();
        }
    }
}
