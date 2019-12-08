package com.bytecodeassemblers.androidweatherstation.weatherBitModel;


public class WeatherBitModel {
    private String temp;
    private String description;
    private String speed;
    private String dir;
    private String cityName;
    private String icon;

    public WeatherBitModel()
    {
        setDescription("");
        setTemp("");
        setDir("");
        setSpeed("");
        setCityName("");
        setIcon("");
    }

    //Getters
    public String getSpeed() {
        return speed;
    }
    public String getDir() {
        return dir;
    }
    public String getTemp() {
        return temp;
    }
    public String getDescription() {
        return description;
    }
    public String getCityName() {
        return cityName;
    }
    public String getIcon() {
        return icon;
    }

    //Setters
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public void setDir(String dir) {
        this.dir = dir;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public static String getImage(String icon){
        return String.format("https://www.weatherbit.io/static/img/icons/%s.png",icon);
    }
}
