package com.bytecodeassemblers.androidweatherstation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMap;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;


public class MainActivityController {



    private MimageLoader imageLoader;
    private Activity activity;
    private Common commonObject;


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

    //mainView
    private SearchView searchView;
    private String inputCoordinates;



    public MainActivityController(Activity activity){
        this.activity=activity;
        commonObject = new Common();
        InitializeComponent();
    }


    public void InitializeComponent() {
        imageLoader = new MimageLoader(activity);
        searchView = activity.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(onSubmitQueryTextListener);
    }

    public void ExecuteOpenWeatherTask(){
        String url = Common.openWeatherRequestLink(lat,lon);
        new OpenWeatherTask(activity,imageLoader).execute(url);
    }

    public void ExecuteWeatherBitTask(){
        String url = Common.weatherBitRequestLink(lat,lon);
        new WeatherBitTask(activity,imageLoader).execute(url);
    }


    //run query from search button in keyboard
    private SearchView.OnQueryTextListener onSubmitQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            parseSearchView();
            openMapActivity();
            //ExecuteWeatherBitTask();
            //ExecuteOpenWeatherTask();
            return true;
        }
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };


        public String GetExactLocationAddress(){
            Geocoder geocoder= new Geocoder(activity, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(commonObject.getLatitude()),Double.parseDouble(commonObject.getLongitude()) , 1); //get specific address for latitude and longtitude given
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses.get(0).getAddressLine(0);
        }


    public void parseSearchView(){
        //inputCoordinates = String.valueOf(searchView.getQuery()); //get text from SearchView
        //String[] coords = inputCoordinates.split(",");  //separate coordinates
        //lat = Double.parseDouble(coords[0]);
        //lon = Double.parseDouble(coords[1]);

         commonObject.setLat(String.valueOf(this.lat));  //set latitude in common class
         commonObject.setLon(String.valueOf(this.lon));  //set longitude in common class
    }

    public void openMapActivity(){
        Intent intent = new Intent(activity, GoogleMapActivity.class);
        activity.startActivityForResult(intent,1 );

    }


}
