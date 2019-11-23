package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMap;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMap;


public class Controller {

    private MimageLoader imageLoader;
    private double lat = 41.090923;
    private double lon = 23.54132;
    private Activity activity;
    private Common commonObject;

    //openWeather
    private OpenWeatherMap openWeatherObject;
    private TextView openWeathertempOnView;
    private TextView openWeathermaxTempOnView;
    private TextView openWeatherminTempOnView;
    private TextView openWeatherhumidityOnView;
    private TextView openWeatherdescriptionOnView;
    private TextView openWeatherwindSpeedOnView;
    private TextView openWeathercityNameOnView;
    private NetworkImageView openWeathermyImage;
    private String OpenWeatherUrl;


    //WeatherBit
    private WeatherBitMap weatherBitMap;
    private TextView weatherBitTempOnView;
    private TextView weatherBitCityOnView;
    private TextView weatherBitDescriptionOnView;
    private TextView weatherBitWindSpeedOnView;
    private NetworkImageView weatherBitimageView;
//    private String WeatherBitUrl = "https://api.weatherbit.io/v2.0/current?lat="+lat+"&lon="+lon+"&key=85166dfd6eae40128861ff9efb80ec65";
    private String WeatherBitUrl;



    public Controller(Activity activity){
        this.activity=activity;
        openWeatherObject = new OpenWeatherMap();
        weatherBitMap = new WeatherBitMap();
        commonObject = new Common();
        OpenWeatherUrl = commonObject.openWeatherRequestLink();
        WeatherBitUrl = commonObject.weatherBitRequestLink();
        InitializeComponent();
        new OpenWeatherTask().execute(OpenWeatherUrl);
        new WeatherBitTask().execute(WeatherBitUrl);
    }

    public void InitializeComponent() {

        imageLoader = new MimageLoader(activity);

        //OpenWeather TextView Initialization
        openWeathercityNameOnView = activity.findViewById(R.id.address);
        openWeathertempOnView = activity.findViewById(R.id.openWeatherTemp);
        openWeathermaxTempOnView = activity.findViewById(R.id.openWeatherMaxTemp);
        openWeatherminTempOnView = activity.findViewById(R.id.openWeatherMinTemp);
        openWeatherhumidityOnView = activity.findViewById(R.id.openWeatherHumidity);
        openWeatherwindSpeedOnView = activity.findViewById(R.id.openWeatherWindSpeed);
        openWeatherdescriptionOnView = activity.findViewById(R.id.openWeatherDescription);
        openWeathermyImage = activity.findViewById(R.id.openWeatherImage);


        //WeatherBit Textview Initialization
        weatherBitTempOnView = activity.findViewById(R.id.weatherbit_temp);
        weatherBitCityOnView = activity.findViewById(R.id.weatherbit_city);
        weatherBitDescriptionOnView = activity.findViewById(R.id.weatherbit_description);
        weatherBitWindSpeedOnView = activity.findViewById(R.id.weatherbit_windspeed);
        weatherBitimageView = activity.findViewById(R.id.weatherbitImage);

    }


    private class OpenWeatherTask extends AsyncTask<String,Void, OpenWeatherMap> {
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
            openWeathercityNameOnView.setText(weather.simple.getCityName());
            openWeathertempOnView.setText("Temp: "+weather.main.getTemp());
            openWeathermaxTempOnView.setText("Temp max: "+weather.main.getTempMax());
            openWeatherminTempOnView.setText("Temp min: "+weather.main.getTempMin());
            openWeatherhumidityOnView.setText("Humidity: "+weather.main.getHumidity());
            openWeatherwindSpeedOnView.setText("Wind speed: "+weather.wind.getSpeed());
            openWeatherdescriptionOnView.setText("Description: "+weather.weather.getDescription());
            openWeathermyImage.setImageUrl(weather.weather.getImage(weather.weather.getIcon()),imageLoader.getmImageLoader());
        }
    }

    private class WeatherBitTask extends AsyncTask<String,Void, WeatherBitMap> {

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
            weatherBitTempOnView.setText("Temp: "+weatherBitMap.main.getTemp());
            weatherBitCityOnView.setText("City: "+weatherBitMap.simple.getCityName());
            weatherBitDescriptionOnView.setText("Description: "+weatherBitMap.weather.getDescription());
            weatherBitWindSpeedOnView.setText("Wind speed: "+weatherBitMap.wind.getSpeed());
            weatherBitimageView.setImageUrl(weatherBitMap.simple.getImage(weatherBitMap.simple.getIcon()),imageLoader.getmImageLoader());
        }

    }
}
