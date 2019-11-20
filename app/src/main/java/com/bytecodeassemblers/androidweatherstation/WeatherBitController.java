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

    private WeatherBitMap weatherBitMap;
    private TextView result;

    private NetworkImageView imageView;

    private double lat = 41.090923;
    private double lon = 23.54132;

    private String url = "https://api.weatherbit.io/v2.0/current?lat="+lat+"&lon="+lon+"&key="+API_KEY;
    private Activity activity;
    private MimageLoader imageLoader;



    public WeatherBitController(Activity activity){
        this.activity=activity;
        weatherBitMap = new WeatherBitMap();
        InitializeComponent();
        new WeatherTask().execute(url);
    }

    private void InitializeComponent() {
       //result = this.activity.findViewById(R.id.Result);
        imageView = this.activity.findViewById(R.id.image);
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
            imageView.setImageUrl(weatherBitMap.simple.getImage(weatherBitMap.simple.getIcon()),imageLoader.getmImageLoader());
        }






    }
}
