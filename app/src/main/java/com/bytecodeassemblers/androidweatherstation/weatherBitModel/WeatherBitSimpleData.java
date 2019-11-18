package com.bytecodeassemblers.androidweatherstation.weatherBitModel;

public class WeatherBitSimpleData {
    private String timezone;
    private String cityName;
    private String icon;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static String getImage(String icon){
        return String.format("https://www.weatherbit.io/static/img/icons/%s.png",icon);
    }


}
