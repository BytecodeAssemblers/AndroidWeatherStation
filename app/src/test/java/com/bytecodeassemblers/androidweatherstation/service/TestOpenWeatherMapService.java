package com.bytecodeassemblers.androidweatherstation.service;

import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;
import com.bytecodeassemblers.androidweatherstation.weather_service.OpenWeatherTask;


import org.junit.Test;

import static org.junit.Assert.*;


public class TestOpenWeatherMapService {

    @Test
    public void testRequestCurrentWeather() throws Exception {
        OpenWeatherModel result = OpenWeatherTask.instance()
                .requestCurrentWeather(47.6067,-122.3321);
        assertTrue("result is null!", result != null);
        assertTrue("result code is not 200",  result.cod == 200);
        // check erroneous values
        result = OpenWeatherTask.instance().requestCurrentWeather(547.6067,-122.3321);
        assertTrue("result is null!", result != null);
        result = OpenWeatherTask.instance().requestCurrentWeather(null,null);
        assertTrue("result is null!", result == null);
    }

}

}