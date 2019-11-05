package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bytecodeassemblers.androidweatherstation.weather_bit.WeatherBitController;

public class MainActivity extends AppCompatActivity {
    private WeatherBitController weatherBitController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherBitController = new WeatherBitController(this);
    }
}
