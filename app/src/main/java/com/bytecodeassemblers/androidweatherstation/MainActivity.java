package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.bytecodeassemblers.androidweatherstation.weather_bit.WeatherBitController;
import com.bytecodeassemblers.androidweatherstation.open_weather.OpenWeatherController;


public class MainActivity extends AppCompatActivity {

    private OpenWeatherController openWeatherController;
    private WeatherBitController weatherBitController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherBitController = new WeatherBitController(this);
        openWeatherController = new OpenWeatherController(this);
    }
}
