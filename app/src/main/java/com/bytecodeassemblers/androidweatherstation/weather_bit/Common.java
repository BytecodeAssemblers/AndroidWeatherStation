package com.bytecodeassemblers.androidweatherstation.weather_bit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static String API_LINK =  "https://api.weatherbit.io/v2.0/current";
    public static String API_KEY= "85166dfd6eae40128861ff9efb80ec65";

    public static String apiRequestLink(String city){
        StringBuilder builder = new StringBuilder(API_LINK);
        builder.append(String.format("?city=%s&key=%s",city,API_KEY));
        return builder.toString();
    }

    public static String unixTimeStampToDateTime(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getImage(String icon){
        return String.format("https://www.weatherbit.io/static/img/icons/r01d.png",icon);
    }
    
}