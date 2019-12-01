package com.bytecodeassemblers.androidweatherstation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMap;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMap;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;


public class MainActivityController {

    private MimageLoader imageLoader;
    private double lat = 0;
    private double lon = 0;
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

//mainView
    private TextView generalTemp ;

    private String WeatherBitUrl;

    private SearchView searchView;

    private String inputCoordinates;

    public MainActivityController(Activity activity){
        this.activity=activity;
        InitializeComponent();
        commonObject = new Common();
        openWeatherObject = new OpenWeatherMap();
        weatherBitMap = new WeatherBitMap();
        OpenWeatherUrl = commonObject.openWeatherRequestLink();
        WeatherBitUrl = commonObject.weatherBitRequestLink();
        new OpenWeatherTask().execute(OpenWeatherUrl);
        new WeatherBitTask().execute(WeatherBitUrl);
    }


//    public void OpenWeatherTask(){
//        String  OpenWeatherUrl="http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&APPID=ee6892eaa4ce0be1a8eac7817898d322&units=metric";
//        new OpenWeatherTask().execute(OpenWeatherUrl);
//    }




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
        generalTemp=activity.findViewById(R.id.temp);

        
        searchView = activity.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(onSubmitQueryTextListener);
    }


    //run query from search button in keyboard
    private SearchView.OnQueryTextListener onSubmitQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            parseSearchView();
            openMapActivity();
            OpenWeatherUrl = commonObject.openWeatherRequestLink();
            WeatherBitUrl = commonObject.weatherBitRequestLink();
            new OpenWeatherTask().execute(OpenWeatherUrl);
            new WeatherBitTask().execute(WeatherBitUrl);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    public void parseSearchView(){
        inputCoordinates = String.valueOf(searchView.getQuery()); //get text from SearchView
        String[] coords = inputCoordinates.split(",");  //separate coordinates
        commonObject.setLat(coords[0]);  //set latitude in common class to take values in GoogleMapActivity
        commonObject.setLon(coords[1]);  //set longitude in common class
    }

    public void openMapActivity(){
        Intent intent = new Intent(activity, GoogleMapActivity.class);
        activity.startActivity(intent);
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
            generalTemp.setText(weather.main.getTemp() + "°C");
        }
    }



    private class WeatherBitTask extends AsyncTask<String,Void, WeatherBitMap> {

        @Override
        protected WeatherBitMap doInBackground(String... strings) {
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0])); // get data from weatherBit using http request
            weatherBitMap = JSONWeatherParser.getWeatherBitData(data);  //sends the response values into parse
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
