package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

public class AdvancedDetailsActivity extends AppCompatActivity {


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












    }
}
