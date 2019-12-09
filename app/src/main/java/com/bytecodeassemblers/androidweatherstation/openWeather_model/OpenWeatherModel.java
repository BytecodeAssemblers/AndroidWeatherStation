package com.bytecodeassemblers.androidweatherstation.openWeather_model;

public class OpenWeatherModel {

    //API LINK -> http://api.openweathermap.org/data/2.5/weather?q=Serres%2Cgr&APPID=ee6892eaa4ce0be1a8eac7817898d322&units=metric

    private String temp;
    private String pressure;
    private String humidity;
    private String tempMin;
    private String tempMax;
    private String speed;
    private String deg;
    private String description;
    private String icon;

    public OpenWeatherModel() {
        setHumidity("");
        setPressure("");
        setTemp("");
        setTempMax("");
        setTempMin("");
        setSpeed("");
        setDeg("");
        setDescription("");
        setIcon("");
    }

    //Getters
    public String getTemp() {
        return temp;
    }
    public String getPressure() {
        return pressure;
    }
    public String getHumidity() {
        return humidity;
    }
    public String getTempMin() {
        return tempMin;
    }
    public String getTempMax() {
        return tempMax;
    }
    public String getSpeed() {
        return speed;
    }
    public String getDeg() {
        return deg;
    }
    public String getDescription() {
        return description;
    }
    public String getIcon() {
        return icon;
    }   public static String getImage(String icon){
        return String.format("http://api.openweathermap.org/img/w/%s.png",icon);
    }

    //Setters
    public void setTemp(String temp) {
        this.temp = temp;
    }
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }
    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public void setDeg(String deg) {
        this.deg = deg;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }



}
