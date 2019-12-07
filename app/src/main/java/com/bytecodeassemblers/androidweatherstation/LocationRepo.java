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



    public void addLocationReg(String string, LatLng latLng) {
        locationRepo.put(string, latLng);
    }


    public static void setLocationRepo(HashMap<String, LatLng> locationRepo) {
        LocationRepo.locationRepo = locationRepo;
    }


    static HashMap<String, LatLng> locationRepo = new HashMap<String, LatLng>();


    public  HashMap<String, LatLng> getLocationRepo() {

        return new HashMap<String, LatLng>(locationRepo);
    }




    public LatLng searchLocationReg(String string){
        LatLng latLng = null ;
        if (locationRepo.containsKey(string)){
            latLng = locationRepo.get(string);
        }
        return latLng;
    }



}
