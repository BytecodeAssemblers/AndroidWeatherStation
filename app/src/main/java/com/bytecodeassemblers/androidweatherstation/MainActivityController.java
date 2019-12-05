package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.widget.SearchView;
import android.widget.Toast;

import com.bytecodeassemblers.androidweatherstation.weather_service.OpenWeatherTask;
import com.bytecodeassemblers.androidweatherstation.weather_service.WeatherBitTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


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


    private LocationRepo locationRepo ;

    public Activity getActivity() {
        return activity;
    }

    boolean inputCheck;


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


    public String GetExactLocationAddress(Double latitude, Double longitude){
        Geocoder geocoder= new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = null;
        try {
                addresses = geocoder.getFromLocation(latitude,longitude , 1);  //get specific address for latitude and longtitude given
                 lat = addresses.get(0).getLatitude();
                 lon = addresses.get(0).getLongitude();

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
