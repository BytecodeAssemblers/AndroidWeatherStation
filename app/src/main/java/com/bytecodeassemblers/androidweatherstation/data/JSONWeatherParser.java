package com.bytecodeassemblers.androidweatherstation.data;


import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherSimpleData;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherWind;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitModel;


import org.json.JSONException;
import org.json.JSONObject;


public class JSONWeatherParser {

    public static OpenWeatherModel getOpenWeatherData(String data) {

        OpenWeatherModel myOpenWeatherModel = new OpenWeatherModel(); // object composition
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject windObject =   jsonObject.getJSONObject("wind");
            String deg = windObject.getString("deg");
            String speed = windObject.getString("speed");
            String temp = mainObject.getString("temp");
            String pressure = mainObject.getString("pressure");
            String humidity = mainObject.getString("humidity");
            String tempMin = mainObject.getString("temp_min");
            String tempMax = mainObject.getString("temp_max");
            String description = weatherObject.getString("description");
            String icon = weatherObject.getString("icon");

            myOpenWeatherModel.set

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //IF YOU RETURN NULL OBJECTS WHILE YOU HAVE ALREADY INITIALIZED THEM IN THE BEGINNING OF THE FUNCTION
        //I WILL FIND WHERE YOU LIVE AND EAT ALL THE FOOD IN YOUR FUCKING FRIDGE
        return myOpenWeatherModel;
    }

    public static WeatherBitModel getWeatherBitData(String data) {

        WeatherBitModel weatherBitModel = new WeatherBitModel();
        try {

            //Read data from response JSON
            JSONObject jsonObject = new JSONObject(data);
            JSONObject dataObject = jsonObject.getJSONArray("data").getJSONObject(0);
            JSONObject weatherData = dataObject.getJSONObject("weather");
            String cityName = dataObject.getString("city_name");
            String icon = weatherData.getString("icon");
            String weatherDescription = weatherData.getString("description");
            String temp = dataObject.getString("temp");
            String windspeed= dataObject.getString("wind_spd");
            String windDirection = dataObject.getString("wind_cdir_full");

            //Populate the model object with the data
            weatherBitModel.setCityName(cityName);
            weatherBitModel.setDescription(weatherDescription);
            weatherBitModel.setDir(windDirection);
            weatherBitModel.setSpeed(windspeed);
            weatherBitModel.setTemp(temp);
            weatherBitModel.setIcon(icon);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //AGAIN?!?!?!
        return weatherBitModel;
    }


}
