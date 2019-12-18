package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitModel;

import java.util.Objects;

public class AdvancedDetailsActivity extends AppCompatActivity {
    private Button upButton;

    TextView openWeatherTemperature;
      TextView openWeatherMaxTemperature;
        TextView openWeatherMinTemperature;
          TextView openWeatherWindSpeed;
             TextView openWeatherDescription;
               TextView openWeatherHumidity;
               NetworkImageView openWeatherImage;

    TextView weatherbit_city;
      TextView weatherbit_description;
        TextView weatherbit_windspeed;
          TextView weatherbit_temperature;
            NetworkImageView weatherbitImage;

    MainActivityController mainActivityController;
    public AdvancedDetailsActivity(){
    }
    public AdvancedDetailsActivity(MainActivityController mainActivityController){
        this.mainActivityController = mainActivityController;
          //this.mainActivityController.ExecuteOpenWeatherTask();
          // this.mainActivityController.ExecuteWeatherBitTask();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_details);
        Toolbar advancedDetailsToolbar = findViewById(R.id.advancedDetailsToolbar);
        setSupportActionBar(advancedDetailsToolbar);
      //  Objects.requireNonNull(getSupportActionBar()).setTitle("Advanced Details");
       Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
         /*--OpenWeather Initializer--*/
          openWeatherTemperature = findViewById(R.id.openWeatherTemp);
          openWeatherMaxTemperature = findViewById(R.id.openWeatherMaxTemp);
          openWeatherMinTemperature = findViewById(R.id.openWeatherMinTemp);
          openWeatherWindSpeed = findViewById(R.id.openWeatherWindSpeed);
          openWeatherHumidity = findViewById(R.id.openWeatherHumidity);
          openWeatherDescription = findViewById(R.id.openWeatherDescription);
          String openWeatherMainTemperature = intent.getStringExtra("Main_Temp");
          String openWeatherMinimumTemperature = intent.getStringExtra("Minimum_Temp");
          String openWeatherMaximumTemperature = intent.getStringExtra("Maximum_Temp");
          String openWeatherSetDescription = intent.getStringExtra("Description");
          String openWeatherSetWindSpeed =  intent.getStringExtra("WindSpeed");
          String openWeatherSetHumidity = intent.getStringExtra("Humidity");

          openWeatherTemperature.setText(openWeatherMainTemperature);
          openWeatherMinTemperature.setText(openWeatherMinimumTemperature);
          openWeatherMaxTemperature.setText(openWeatherMaximumTemperature);
          openWeatherDescription.setText(openWeatherSetDescription);
          openWeatherWindSpeed.setText(openWeatherSetWindSpeed);
          openWeatherHumidity.setText(openWeatherSetHumidity);

          /*--WeatherBit Initializer--*/
         weatherbit_city = findViewById(R.id.weatherBit_city);
         weatherbit_description = findViewById(R.id.weatherBit_description);
         weatherbit_windspeed=findViewById(R.id.weatherBit_WindSpeed);
         weatherbit_temperature=findViewById(R.id.weatherBit_temp);

        String weatherbitCityName  = intent.getStringExtra("weatherbit_city");
        String weatherbitTemperature  = intent.getStringExtra("weatherbit_temperature");
        String weatherbitDescription = intent.getStringExtra("weatherbit_description");
        String weatherbitWindspeed =  intent.getStringExtra("weatherbit_windSpeed");

        weatherbit_city.setText(weatherbitCityName);
        weatherbit_description.setText(weatherbitDescription);
        weatherbit_windspeed.setText(weatherbitWindspeed);
        weatherbit_temperature.setText(weatherbitTemperature);

    }


}
