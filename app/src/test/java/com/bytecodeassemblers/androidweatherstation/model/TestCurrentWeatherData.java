package com.bytecodeassemblers.androidweatherstation.model;


import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;
import com.bytecodeassemblers.androidweatherstation.weather_service.OpenWeatherTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;


public class TestCurrentWeatherData {
    private Collection collection;
    private String inputJson;

    @Before
    public void setUp(){
        // a result from the call to a url of
        // http://api.openweathermap.org/data/2.5/weather?q={Seattle}&APPID=appKey
        inputJson = "{\"coord\":{\"lon\":-122.33,\"lat\":47.61}," +
                "\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}]," +
                "\"base\":\"cmc stations\",\"main\":{\"temp\":289.437,\"pressure\":1016.59,\"humidity\":83," +
                "\"temp_min\":289.437,\"temp_max\":289.437,\"sea_level\":1027.05,\"grnd_level\":1016.59}," +
                "\"wind\":{\"speed\":1.91,\"deg\":231},\"rain\":{\"3h\":1.355},\"clouds\":{\"all\":92}," +
                "\"dt\":1463265986," +
                "\"sys\":{\"message\":0.0039,\"country\":\"US\",\"sunrise\":1463229072,\"sunset\":1463283657}," +
                "\"id\":5809844,\"name\":\"Seattle\",\"cod\":200}";
    }

    /**
     * Can CurrentWeatherData class instances be instantiated?
     */

    @Test
    public void testInstantiateCurrentWeatherDataClass(){
        System.out.println("@Test - testInstantiateCurrentWeatherDataClass");
        assertTrue("Unable to instantiate CurrentWeatherData",(new OpenWeatherModel() != null));
    }

    /**
     * Can get instance from json string.
     */

    @Test
    public void testGetInstanceFromJSonString(){
        Gson gson = new Gson();
        OpenWeatherModel result = gson.fromJson(inputJson, OpenWeatherModel.class);
        assertTrue("Was unable to get CurrentWeatherData instance from Json String.", result instanceof CurrentWeatherData);
    }

    /**
     * Has Expected fields
     */

    @Test
    public void testHasExpectedFields(){
        GsonBuilder builder = new GsonBuilder();
        // need to register rain and snow classes
        builder.registerTypeAdapter(OpenWeatherModel.Rain.class, new OpenWeatherModel());
        builder.registerTypeAdapter(OpenWeatherModel.Snow.class, new OpenWeatherModel());
        Gson gson = builder.create(); // new Gson();
        OpenWeatherModel result = gson.fromJson(inputJson, OpenWeatherModel.class);
        // check coord
        assertTrue("Expecting lon value", result.coord.lon.equals(-122.33));
        assertTrue("Expecting lat value", result.coord.lat.equals(47.61));
        // check weather values
        OpenWeatherModel.Weather rWeather = result.weather[0];
        assertTrue("Expecting weather id value", rWeather.id.equals(500));
        assertTrue("Expecting weather main value", rWeather.main.equals("Rain"));
        assertTrue("Expecting weather description value", rWeather.description.equals("light rain"));
        assertTrue("Expecting weather icon value", rWeather.icon.equals("10d"));
        // check main values
        OpenWeatherModel.Main rMain = result.main;
        assertTrue("Expecting temp value", rMain.temp.equals(289.437));
        assertTrue("Expecting pressure value", rMain.pressure.equals(1016.59));
        assertTrue("Expecting humidity value of 83 but was " +
                rMain.humidity.toString(), rMain.humidity.equals(new Double(83)));
        assertTrue("Expecting temp min value", rMain.temp_min.equals(289.437));
        assertTrue("Expecting temp max value", rMain.temp_max.equals(289.437));
        assertTrue("Expecting sea level value", rMain.sea_level.equals(1027.05));
        assertTrue("Expecting ground level value", rMain.grnd_level.equals(1016.59));

        //rain, snow, wind and clouds
        assertTrue("Expecting rain value", result.rain.threeHour.equals(1.355));
        assertTrue("Expecting null snow value" , result.snow == null);
        assertTrue("Expecting wind speed value" , result.wind.speed.equals(1.91));
        assertTrue("Expecting wind deg wind value" , result.wind.deg.equals(new Double(231)));
        assertTrue("Expecting clouds all value" , result.clouds.all.equals(new Double(92)));

        // dt, sys, (city) name, cod
        assertTrue("Expecting dt value", result.dt.equals(1463265986));
        assertTrue("Expecting sys country value" , result.sys.country.equals("US"));
        assertTrue("Expecting sys sun rise value" , result.sys.sunrise.equals(new Long(1463229072)));
        assertTrue("Expecting sys sun set value" , result.sys.sunset.equals(new Long(1463283657)));
        assertTrue("Expecting (city) name value" , result.name.equals("Seattle"));
        assertTrue("Expecting cod value" , result.cod.equals(200));
        assertTrue("Expecting (city) id value" , result.id.equals(5809844));
    }

    /**
     * Can instantiate from error code.
     */

    @Test
    public void canInstantiateFromErrorJson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create(); // new Gson();
        String inputJson = "{\"cod\":401, \"message\": \"Invalid API key. " +
                "Please see http://openweathermap.org/faq#error401 for more info.\"}";
        String expecting = "Invalid API key. Please see http://openweathermap.org/faq#error401 " +
                "for more info.";
        OpenWeatherModel result = gson.fromJson(inputJson, OpenWeatherModel.class);
        assertTrue("Expecting message value", result.equals(expecting));
    }

}