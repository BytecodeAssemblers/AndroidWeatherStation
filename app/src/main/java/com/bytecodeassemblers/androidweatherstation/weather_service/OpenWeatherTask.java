package com.bytecodeassemblers.androidweatherstation.weather_service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.MimageLoader;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherMap;

public class OpenWeatherTask extends AsyncTask<String,Void, OpenWeatherMap> {

    private MimageLoader imageLoader;
    private OpenWeatherMap openWeatherObject;

    private TextView openWeathertempOnView;
    private TextView openWeathermaxTempOnView;
    private TextView openWeatherminTempOnView;
    private TextView openWeatherhumidityOnView;
    private TextView openWeatherdescriptionOnView;
    private TextView openWeatherwindSpeedOnView;
    private TextView openWeathercityNameOnView;
    private NetworkImageView openWeathermyImage;

    //mainView
    private TextView generalTemp;

   private Activity activity;

    public OpenWeatherTask(Activity activity,MimageLoader image){
        this.activity=activity;
        this.imageLoader = image;
        openWeatherObject = new OpenWeatherMap();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //OpenWeather TextView Initialization
        openWeathercityNameOnView = activity.findViewById(R.id.address);
        openWeathertempOnView = activity.findViewById(R.id.openWeatherTemp);
        openWeathermaxTempOnView = activity.findViewById(R.id.openWeatherMaxTemp);
        openWeatherminTempOnView = activity.findViewById(R.id.openWeatherMinTemp);
        openWeatherhumidityOnView = activity.findViewById(R.id.openWeatherHumidity);
        openWeatherwindSpeedOnView = activity.findViewById(R.id.openWeatherWindSpeed);
        openWeatherdescriptionOnView = activity.findViewById(R.id.openWeatherDescription);
        openWeathermyImage = activity.findViewById(R.id.openWeatherImage);
        generalTemp=activity.findViewById(R.id.temp);
    }


    @Override
    protected OpenWeatherMap doInBackground(String... strings) {
        String responseData = ((new WeatherHttpClient()).getWeatherData(strings[0]));
        openWeatherObject = JSONWeatherParser.getOpenWeatherData(responseData);
        imageLoader.setImageLoader();
        return openWeatherObject;
    }

    @Override
    protected void onPostExecute(OpenWeatherMap openWeatherMap) {
        super.onPostExecute(openWeatherMap);
        openWeathercityNameOnView.setText(openWeatherMap.simple.getCityName());
        openWeathertempOnView.setText("Temp: "+openWeatherMap.main.getTemp());
        openWeathermaxTempOnView.setText("Temp max: "+openWeatherMap.main.getTempMax());
        openWeatherminTempOnView.setText("Temp min: "+openWeatherMap.main.getTempMin());
        openWeatherhumidityOnView.setText("Humidity: "+openWeatherMap.main.getHumidity());
        openWeatherwindSpeedOnView.setText("Wind speed: "+openWeatherMap.wind.getSpeed());
        openWeatherdescriptionOnView.setText("Description: "+openWeatherMap.weather.getDescription());
        openWeathermyImage.setImageUrl(openWeatherMap.weather.getImage(openWeatherMap.weather.getIcon()),imageLoader.getmImageLoader());
        generalTemp.setText(openWeatherMap.main.getTemp() + "°C");
    }
}
