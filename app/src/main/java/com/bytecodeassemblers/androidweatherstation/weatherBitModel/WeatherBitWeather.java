package com.bytecodeassemblers.androidweatherstation.weatherBitModel;

public class WeatherBitWeather {
    private String last_observation_time;
    private String code;
    private String description;

    public String getLast_observation_time() {
        return last_observation_time;
    }

    public void setLast_observation_time(String last_observation_time) {
        this.last_observation_time = last_observation_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
