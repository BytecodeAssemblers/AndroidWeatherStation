package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.data.JSONWeatherParser;
import com.example.myapplication.data.WeatherHttpClient;
import com.example.myapplication.openWeather_model.OpenWeatherMap;


public class OpenWeatherController {

    private final String API_KEY = "ee6892eaa4ce0be1a8eac7817898d322";

    private OpenWeatherMap openWeatherObject;

    private TextView tempOnView;
    private TextView maxTempOnView;
    private TextView minTempOnView;
    private TextView humidityOnView;
    private TextView descriptionOnView;
    private TextView windSpeedOnView;
    private TextView cityNameOnView;

    private double lat = 41.090923;
    private double lon = 23.54132;

    
    private String  url;
    private Activity activity;
    private MimageLoader imageLoader;
    private NetworkImageView imageView;

    public OpenWeatherController(Activity activity){
        this.activity=activity;
        openWeatherObject = new OpenWeatherMap();
        InitializeComponent();
        url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID="+API_KEY+"&units=metric";
        new WeatherTask().execute(url);
    }

    public void InitializeComponent(){
        cityNameOnView = activity.findViewById(R.id.address);
        tempOnView = activity.findViewById(R.id.openWeatherTemp);
        maxTempOnView = activity.findViewById(R.id.openWeatherMaxTemp);
        minTempOnView = activity.findViewById(R.id.openWeatherMinTemp);
        humidityOnView = activity.findViewById(R.id.openWeatherHumidity);
        windSpeedOnView = activity.findViewById(R.id.openWeatherWindSpeed);
        descriptionOnView = activity.findViewById(R.id.openWeatherDescription);
    }


    private class WeatherTask extends AsyncTask<String,Void, OpenWeatherMap> {
        @Override
        protected OpenWeatherMap doInBackground(String... strings) {
            String responseData = ((new WeatherHttpClient()).getWeatherData(strings[0])); // get data from openWeather using http request
            openWeatherObject = JSONWeatherParser.getOpenWeatherData(responseData); //sends the response values into parse
            imageLoader.setImageLoader();
            return openWeatherObject;
        }

        @Override
        protected void onPostExecute(OpenWeatherMap weather) {
            super.onPostExecute(weather);
            cityNameOnView.setText(weather.simple.getCityName());
            tempOnView.setText(weather.main.getTemp());
            maxTempOnView.setText(weather.main.getTempMax());
            minTempOnView.setText(weather.main.getTempMin());
            humidityOnView.setText(weather.main.getHumidity());
            windSpeedOnView.setText(weather.wind.getSpeed());
            descriptionOnView.setText(weather.weather.getDescription());
            imageView.setImageUrl(weather.simple.getImage(weather.weather.getIcon()),imageLoader.getmImageLoader());
        }
    }
}
