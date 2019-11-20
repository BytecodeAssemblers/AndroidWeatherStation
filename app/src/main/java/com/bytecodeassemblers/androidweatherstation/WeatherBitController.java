package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMap;

public class WeatherBitController {

    private final String API_KEY = "85166dfd6eae40128861ff9efb80ec65";
    private MimageLoader imageLoader;

    private WeatherBitMap weatherBitMap;
    private TextView tempOnView;
    private TextView cityOnView;
    private TextView descriptionOnView;
    private TextView windSpeedOnView;

    private NetworkImageView imageView;

//    private double lat = ClientLocation.getLatitude();
//    private double lon = ClientLocation.getLongitude();


    private double lat = 41.090923;
    private double lon = 23.54132;

    private String url = "https://api.weatherbit.io/v2.0/current?lat="+lat+"&lon="+lon+"&key="+API_KEY;
    private Activity activity;


    public WeatherBitController(Activity activity){
        this.activity=activity;
        weatherBitMap = new WeatherBitMap();
        InitializeComponent();
        new WeatherTask().execute(url);
    }

    private void InitializeComponent() {
        tempOnView = activity.findViewById(R.id.weatherbit_temp);
        cityOnView = activity.findViewById(R.id.weatherbit_city);
        descriptionOnView = activity.findViewById(R.id.weatherbit_description);
        windSpeedOnView = activity.findViewById(R.id.weatherbit_windspeed);
        imageView = activity.findViewById(R.id.weatherbitImage);
        imageLoader = new MimageLoader(activity);
    }
    private class WeatherTask extends AsyncTask<String,Void, WeatherBitMap> {

        @Override
        protected WeatherBitMap doInBackground(String... strings) {
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));
            weatherBitMap = JSONWeatherParser.getWeatherBitData(data);
            imageLoader.setImageLoader();
            return weatherBitMap;
        }

        @Override
        protected void onPostExecute(WeatherBitMap weatherBitMap) {
            super.onPostExecute(weatherBitMap);
            tempOnView.setText("Temp: "+weatherBitMap.main.getTemp());
            cityOnView.setText("City: "+weatherBitMap.simple.getCityName());
            descriptionOnView.setText("Description: "+weatherBitMap.weather.getDescription());
            windSpeedOnView.setText("Wind speed: "+weatherBitMap.wind.getSpeed());
           imageView.setImageUrl(weatherBitMap.simple.getImage(weatherBitMap.simple.getIcon()),imageLoader.getmImageLoader());
        }

    }
}
