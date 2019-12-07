package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
import android.widget.SearchView;
import android.widget.Toast;

import com.bytecodeassemblers.androidweatherstation.listview.ListViewActivity;
import com.bytecodeassemblers.androidweatherstation.weather_service.OpenWeatherTask;
import com.bytecodeassemblers.androidweatherstation.weather_service.WeatherBitTask;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivityController {

    HashMap<String,LatLng> locationInventory = new HashMap<>();

    private MimageLoader imageLoader;
    private Activity activity;



    private  double lat;
    private  double lon;

    public  void setLatitude(double latitude){
        lat = latitude;
    }

    public  void setLongitude(double longitude){
        lon = longitude;
    }


    public Activity getActivity() {
        return activity;
    }



    public MainActivityController(MainActivity activity){
        this.activity=activity;
        InitializeComponent();
    }


    public void InitializeComponent() {
        imageLoader = new MimageLoader(activity);
    }

    public void ExecuteOpenWeatherTask(){
        String url = Common.openWeatherRequestLink(lat,lon);
        new OpenWeatherTask(activity,imageLoader).execute(url);
    }


    public void ExecuteWeatherBitTask(){
        String url = Common.weatherBitRequestLink(lat,lon);
        new WeatherBitTask(activity,imageLoader).execute(url);

    }


    public void savedLocation(){
        LatLng latLng = new LatLng(lat,lon);
        locationInventory.put(GetExactLocationAddress(),latLng);
    }


    public String GetExactLocationAddress(){
        Geocoder geocoder= new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = null;
        try {
                addresses = geocoder.getFromLocation(lat,lon , 1);  //get specific address for latitude and longtitude given
                 lat = addresses.get(0).getLatitude();
                 lon = addresses.get(0).getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0);
    }


    public void openMapActivity(){
        Intent intent = new Intent(activity, GoogleMapActivity.class);
        activity.startActivityForResult(intent,1 );
    }


    public void openListViewActivity(){
        Intent intent = new Intent(this.activity, ListViewActivity.class);
        intent.putExtra("map",locationInventory);
        activity.startActivityForResult(intent,2);
    }

}
