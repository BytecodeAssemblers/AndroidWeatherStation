package com.bytecodeassemblers.androidweatherstation;





import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;
import com.bytecodeassemblers.androidweatherstation.weather_bit.WeatherBitController;
import com.bytecodeassemblers.androidweatherstation.open_weather.OpenWeatherController;


public class MainActivity extends AppCompatActivity {

    private OpenWeatherController openWeatherController;
    private WeatherBitController weatherBitController;
    private GetClientLocation getClientLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getClientLocation = new GetClientLocation(this);
        weatherBitController = new WeatherBitController(this);
        openWeatherController = new OpenWeatherController(this);
    }


}
