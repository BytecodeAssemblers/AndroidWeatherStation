package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMap;


public class OpenWeatherController {

    private final String API_KEY = "ee6892eaa4ce0be1a8eac7817898d322";

    private OpenWeatherMap openWeatherObject;

    private MimageLoader imageLoader;
    private TextView tempOnView;
    private TextView maxTempOnView;
    private TextView minTempOnView;
    private TextView humidityOnView;
    private TextView descriptionOnView;
    private TextView windSpeedOnView;
    private TextView cityNameOnView;
    private NetworkImageView myImage;

    private double lat = 41.090923;
    private double lon = 23.54132;



//        private double lat = ClientLocation.getLatitude();
//        private double lon = ClientLocation.getLongitude();

    private String  url;
    private Activity activity;

    public OpenWeatherController(Activity activity){
        this.activity=activity;
        openWeatherObject = new OpenWeatherMap();
        InitializeComponent();
        url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID="+API_KEY+"&units=metric";
        new WeatherTask().execute(url);
    }

    public void InitializeComponent() {
        cityNameOnView = activity.findViewById(R.id.address);
        tempOnView = activity.findViewById(R.id.openWeatherTemp);
        maxTempOnView = activity.findViewById(R.id.openWeatherMaxTemp);
        minTempOnView = activity.findViewById(R.id.openWeatherMinTemp);
        humidityOnView = activity.findViewById(R.id.openWeatherHumidity);
        windSpeedOnView = activity.findViewById(R.id.openWeatherWindSpeed);
        descriptionOnView = activity.findViewById(R.id.openWeatherDescription);
        myImage = activity.findViewById(R.id.openWeatherImage);
        imageLoader = new MimageLoader(activity);
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
            tempOnView.setText("Temp: "+weather.main.getTemp());
            maxTempOnView.setText("Temp max: "+weather.main.getTempMax());
            minTempOnView.setText("Temp min: "+weather.main.getTempMin());
            humidityOnView.setText("Humidity: "+weather.main.getHumidity());
            windSpeedOnView.setText("Wind speed: "+weather.wind.getSpeed());
            descriptionOnView.setText("Description: "+weather.weather.getDescription());
           myImage.setImageUrl(weather.weather.getImage(weather.weather.getIcon()),imageLoader.getmImageLoader());
        }
    }
}
