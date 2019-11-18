package com.bytecodeassemblers.androidweatherstation.data;


import com.example.myapplication.openWeather_model.OpenWeatherClouds;
import com.example.myapplication.openWeather_model.OpenWeatherCoord;
import com.example.myapplication.openWeather_model.OpenWeatherMain;
import com.example.myapplication.openWeather_model.OpenWeatherMap;
import com.example.myapplication.openWeather_model.OpenWeatherSimpleData;
import com.example.myapplication.openWeather_model.OpenWeatherSys;
import com.example.myapplication.openWeather_model.OpenWeatherWeather;
import com.example.myapplication.openWeather_model.OpenWeatherWind;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {


    public static OpenWeatherMap getOpenWeatherData(String data) {

        OpenWeatherMap myOpenWeatherMap = new OpenWeatherMap();
        try {
            JSONObject jsonObject = new JSONObject(data);

            //read wind JsonObject from OpenWeather Api json
            JSONObject windObject =   jsonObject.getJSONObject("wind");
            String deg = windObject.getString("deg");
            String speed = windObject.getString("speed");
            //set data on OpenWeatherWind class
            OpenWeatherWind weatherWind = new OpenWeatherWind();
            weatherWind.setDeg(deg);
            weatherWind.setSpeed(speed);

            //read coord JsonObject from OpenWeather Api json
            JSONObject coordObject = jsonObject.getJSONObject("coord");
            String latitude = coordObject.getString("lat");
            String longitude = coordObject.getString("lon");
            //set data on OpenWeatherCoord class
            OpenWeatherCoord weatherCoord = new OpenWeatherCoord();
            weatherCoord.setLat(latitude);
            weatherCoord.setLon(longitude);

            //read weather JsonArray from OpenWeather Api json
            JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
            String id = weatherObject.getString("id");
            String main = weatherObject.getString("main");
            String description = weatherObject.getString("description");
            String icon = weatherObject.getString("icon");
            //set data on OpenWeatherWeather class
            OpenWeatherWeather weatherWeather = new OpenWeatherWeather();
            weatherWeather.setId(id);
            weatherWeather.setMain(main);
            weatherWeather.setDescription(description);
            weatherWeather.setIcon(icon);


            //read main JsonObject from OpenWeather Api json
            JSONObject mainObject = jsonObject.getJSONObject("main");
            String temp = mainObject.getString("temp");
            String pressure = mainObject.getString("pressure");
            String humidity = mainObject.getString("humidity");
            String tempMin = mainObject.getString("temp_min");
            String tempMax = mainObject.getString("temp_max");
            //set data on OpenWeatherMain class
            OpenWeatherMain weatherMain = new OpenWeatherMain();
            weatherMain.setTemp(temp);
            weatherMain.setPressure(pressure);
            weatherMain.setHumidity(humidity);
            weatherMain.setTempMin(tempMin);
            weatherMain.setTempMax(tempMax);


            //read clouds JsonObject from OpenWeather Api json
            JSONObject cloudsObject = jsonObject.getJSONObject("clouds");
            String all = cloudsObject.getString("all");
            //set data on OpenWeatherClouds class
            OpenWeatherClouds weatherClouds = new OpenWeatherClouds();
            weatherClouds.setAll(all);

            //read sys JsonObject from OpenWeather Api json
            JSONObject sysObject = jsonObject.getJSONObject("sys");
            String type = sysObject.getString("type");
            String sysId = sysObject.getString("id");
            String country = sysObject.getString("country");
            String sunrise = sysObject.getString("sunrise");
            String sunset = sysObject.getString("sunset");
            //set data on OpenWeatherSys class
            OpenWeatherSys weatherSys = new OpenWeatherSys();
            weatherSys.setType(type);
            weatherSys.setId(sysId);
            weatherSys.setCountry(country);
            weatherSys.setSunrise(sunrise);
            weatherSys.setSunset(sunset);


            //read simple data from OpenWeather Api json
            String base = jsonObject.getString("base");
            String dt = jsonObject.getString("dt");
            String timezone = jsonObject.getString("timezone");
            String name = jsonObject.getString("name");
            String cod = jsonObject.getString("cod");
            //set data on OpenWeatherSimple class
            OpenWeatherSimpleData weatherSimple = new OpenWeatherSimpleData();
            weatherSimple.setBase(base);
            weatherSimple.setDt(dt);
            weatherSimple.setTimezone(timezone);
            weatherSimple.setCityName(name);
            weatherSimple.setCod(cod);


            myOpenWeatherMap.wind = weatherWind;
            myOpenWeatherMap.coord= weatherCoord;
            myOpenWeatherMap.weather = weatherWeather;
            myOpenWeatherMap.main = weatherMain;
            myOpenWeatherMap.clouds = weatherClouds;
            myOpenWeatherMap.sys = weatherSys;
            myOpenWeatherMap.simple = weatherSimple;

            return myOpenWeatherMap;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
