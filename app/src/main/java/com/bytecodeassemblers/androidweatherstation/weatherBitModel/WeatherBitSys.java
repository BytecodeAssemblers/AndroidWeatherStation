package com.bytecodeassemblers.androidweatherstation.weatherBitModel;

public class WeatherBitSys {
    private String Country;
    private String sunrise;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    private String sunset;
}
