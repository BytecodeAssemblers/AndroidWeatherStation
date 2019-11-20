package com.bytecodeassemblers.androidweatherstation.open_weather;

public class Common {

    public static String API_LINK =  "http://api.openweathermap.org/data/2.5/weather";
    public static String API_KEY= "ee6892eaa4ce0be1a8eac7817898d322";

    private static String lat;
    private static String lon;
    private static String location;

    public static String apiRequestLink(String latitude,String longtitude){
        StringBuilder builder = new StringBuilder(API_LINK);
        builder.append(String.format("?lat=%s&lon=%s&APPID=%s&units=metric",latitude ,longtitude,API_KEY));
        return builder.toString();
    }

//    public static String apiRequestLink(){
//        StringBuilder builder = new StringBuilder(API_LINK);
//        builder.append(String.format("?lat="+lat+"&lon="+lon+"&APPID=%s&units=metric",API_KEY));
//        return builder.toString();
//    }

    public static String getImage(String icon){
        return String.format("http://api.openweathermap.org/img/w/%s.png",icon);
    }

    public static void setLat(String lat) {
        Common.lat = lat;
    }

    public static void setLon(String lon) {
        Common.lon = lon;
    }

    public static String getLat() {
        return lat;
    }

    public static String getLon() {
        return lon;
    }

    public static String getLocation() {
        return location;
    }
    public static void setLocation(String location) {
        Common.location = location;
    }
}
