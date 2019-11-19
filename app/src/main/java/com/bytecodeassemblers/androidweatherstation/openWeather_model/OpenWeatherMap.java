package com.bytecodeassemblers.androidweatherstation.openWeather_model;

public class OpenWeatherMap {

    //API LINK -> http://api.openweathermap.org/data/2.5/weather?q=Serres%2Cgr&APPID=ee6892eaa4ce0be1a8eac7817898d322&units=metric

    public OpenWeatherWind wind = new OpenWeatherWind();
    public OpenWeatherCoord coord = new OpenWeatherCoord();
    public OpenWeatherWeather weather = new OpenWeatherWeather();
    public OpenWeatherMain main = new OpenWeatherMain();
    public OpenWeatherClouds clouds = new OpenWeatherClouds();
    public OpenWeatherSys sys = new OpenWeatherSys();
    public OpenWeatherSimpleData simple = new OpenWeatherSimpleData();

}
