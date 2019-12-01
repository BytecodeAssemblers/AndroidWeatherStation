package com.bytecodeassemblers.androidweatherstation;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
/**
   This class takes device coordinates from GetClientLocation class at first run. It also takes coordinates from user input.
   It has two methods to create link for request in OpenWeather API and WeatherBit API
   this methods returns the API'S links for request.
 */
public class Common {


    //OpenWeather call info
    public final String openWeather_API_LINK =  "http://api.openweathermap.org/data/2.5/weather";
    public final String openWeather_API_KEY = "ee6892eaa4ce0be1a8eac7817898d322";


    //WeatherBit call info
    public final String weatherBitAPI_LINK =  "https://api.weatherbit.io/v2.0/current";
    public final String weatherBitAPI_KEY= "85166dfd6eae40128861ff9efb80ec65";


    private static String latitude =null;
    private static String longitude = null;

    //returns updated openweather request link
    public  String openWeatherRequestLink(){
        StringBuilder builder = new StringBuilder(openWeather_API_LINK);
        builder.append(String.format("?lat="+latitude+"&lon="+longitude+"&APPID=%s&units=metric",openWeather_API_KEY));
        return builder.toString();
    }

    //returns updated weatherBit request link
    public  String weatherBitRequestLink(){
        StringBuilder builder = new StringBuilder(weatherBitAPI_LINK);
        builder.append(String.format("?lat="+latitude+"&lon="+longitude+"&key=%s",weatherBitAPI_KEY));
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
