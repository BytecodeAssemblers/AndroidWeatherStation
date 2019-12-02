package com.bytecodeassemblers.androidweatherstation;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
/**
   This class takes device coordinates from MainActivityController class , takes coordinates from user input.
   GoogleMapActivity class receive this coordinates to show location from the user input in map.
   It has two methods to create link for request in OpenWeather API and WeatherBit API
   this methods returns the API'S links for request.
 */
public class Common {


    //OpenWeather call info
    public static final String openWeather_API_LINK =  "http://api.openweathermap.org/data/2.5/weather";
    public static final String openWeather_API_KEY = "ee6892eaa4ce0be1a8eac7817898d322";


    //WeatherBit call info
    public static final String weatherBitAPI_LINK =  "https://api.weatherbit.io/v2.0/current";
    public static final String weatherBitAPI_KEY= "85166dfd6eae40128861ff9efb80ec65";


    private static String latitude = null;
    private static String longitude = null;


    //returns updated openWeather request link
    public static String openWeatherRequestLink(double lat,double lon){
        StringBuilder builder = new StringBuilder(openWeather_API_LINK);
        builder.append(String.format("?lat="+lat+"&lon="+lon+"&APPID=%s&units=metric",openWeather_API_KEY));
        return builder.toString();
    }


    //returns updated weatherBit request link
    public static String weatherBitRequestLink(double lat,double lon){
        StringBuilder builder = new StringBuilder(weatherBitAPI_LINK);
        builder.append(String.format("?lat="+lat+"&lon="+lon+"&key=%s",weatherBitAPI_KEY));
        return builder.toString();
    }


    public void setLat(String lat) {
        this.latitude = lat;
    }

    public void setLon(String lon) {
        this.longitude = lon;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public String getLongitude(){
        return this.longitude;
    }

}
