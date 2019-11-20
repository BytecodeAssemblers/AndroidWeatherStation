package com.bytecodeassemblers.androidweatherstation.data;


import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherClouds;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherCoord;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMain;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMap;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherSimpleData;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherSys;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherWeather;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherWind;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitCoord;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMain;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMap;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitSimpleData;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitSys;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitWeather;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitWind;


import org.json.JSONException;
import org.json.JSONObject;


public class JSONWeatherParser {

    public static OpenWeatherMap getOpenWeatherData(String data) {

        OpenWeatherMap myOpenWeatherMap = new OpenWeatherMap(); // object composition
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

    public static WeatherBitMap getWeatherBitData(String data) {

        WeatherBitMap weatherBitMap = new WeatherBitMap();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject dataObject = jsonObject.getJSONArray("data").getJSONObject(0);
            JSONObject weatherData = dataObject.getJSONObject("weather");

           // Read WeatherBitSimpleData Data
            String cityName = dataObject.getString("city_name");
            String icon = weatherData.getString("icon");
            String timezone = dataObject.getString("timezone");



           // Set WeatherBitSimpleData Data
            WeatherBitSimpleData weatherBitSimple = new WeatherBitSimpleData();
            weatherBitSimple.setCityName(cityName);
            weatherBitSimple.setIcon(icon);
            weatherBitSimple.setTimezone(timezone);

            //Read WeatherBitCoord Data
            String lat=dataObject.getString("lat");
            String lon = dataObject.getString("lon");

            //Set WeatherBitCoord Data
            WeatherBitCoord coord = new WeatherBitCoord();
            coord.setLat(lat);
            coord.setLon(lon);

            //Read WeatherBitMain Data

            String temp = dataObject.getString("temp");

            //Set WeatherBitMain Data
            WeatherBitMain main = new WeatherBitMain();
            main.setTemp(temp);

            //Read WeatherBitSys Data
            String country = dataObject.getString("country_code");
            String sunrise = dataObject.getString("sunrise");
            String sunset = dataObject.getString("sunset");

            //Set WeatherBitSys Data
            WeatherBitSys sys = new WeatherBitSys();
            sys.setCountry(country);
            sys.setSunrise(sunrise);
            sys.setSunset(sunset);

            //Read WeatherBitWeather Data
            String last_observation_time = dataObject.getString("last_ob_time");
            String weatherCode = weatherData.getString("code");
            String weatherDescription = weatherData.getString("description");

            //Set WeatherBitWeather Data
            WeatherBitWeather bitWeather = new WeatherBitWeather();
            bitWeather.setCode(weatherCode);
            bitWeather.setDescription(weatherDescription);
            bitWeather.setLast_observation_time(last_observation_time);

            //Read WeatherBitWind Data
            String Windspeed= dataObject.getString("wind_spd");
            String Wind_direction = dataObject.getString("wind_cdir_full");

            //Set WeatherBitWind Data
            WeatherBitWind WBWind = new WeatherBitWind();
            WBWind.setDir(Wind_direction);
            WBWind.setSpeed(Windspeed);


            weatherBitMap.simple=weatherBitSimple;
            weatherBitMap.coord=coord;
            weatherBitMap.main = main;
            weatherBitMap.sys = sys;
            weatherBitMap.wind=WBWind;
            weatherBitMap.weather =bitWeather;
            return  weatherBitMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }


}
