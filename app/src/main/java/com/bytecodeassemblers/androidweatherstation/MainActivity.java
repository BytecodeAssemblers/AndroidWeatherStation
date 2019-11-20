package com.bytecodeassemblers.androidweatherstation;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.ImageLoader;

import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;




public class MainActivity extends AppCompatActivity {

    private WeatherBitController weatherBitController;
    private OpenWeatherController openWeatherController;
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
