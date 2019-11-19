package com.bytecodeassemblers.androidweatherstation;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;

import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;
import com.bytecodeassemblers.androidweatherstation.weather_bit.WeatherBitController;
import com.bytecodeassemblers.androidweatherstation.open_weather.OpenWeatherController;
//import com.bytecodeassemblers.androidweatherstation.weather_bit.WB_VolleySingleton;
import com.bytecodeassemblers.androidweatherstation.weather_bit.WB_VolleySingleton;
import com.bytecodeassemblers.androidweatherstation.weather_bit.WeatherBitController;



public class MainActivity extends AppCompatActivity {

    private OpenWeatherController openWeatherController;
    private WeatherBitController weatherBitController;

    private ImageLoader mImageLoader;
    private GetClientLocation getClientLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getClientLocation = new GetClientLocation(this);
        weatherBitController = new WeatherBitController(this);
        //openWeatherController = new OpenWeatherController(this);
        //mImageLoader = WB_VolleySingleton.getInstance(this).getImageLoader();


    }


}
