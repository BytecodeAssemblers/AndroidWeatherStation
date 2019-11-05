package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bytecodeassemblers.androidweatherstation.open_weather.OpenWeatherController;

public class MainActivity extends AppCompatActivity {

    private OpenWeatherController openWeatherController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openWeatherController = new OpenWeatherController(this);
    }
}
