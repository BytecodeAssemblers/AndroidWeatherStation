package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitMap;

public class WeatherBitTask extends AsyncTask<String,Void, WeatherBitMap> {


    private Activity activity;
    private MimageLoader imageLoader;


    //WeatherBit
    private WeatherBitMap weatherBitMap;
    private TextView weatherBitTempOnView;
    private TextView weatherBitCityOnView;
    private TextView weatherBitDescriptionOnView;
    private TextView weatherBitWindSpeedOnView;
    private NetworkImageView weatherBitimageView;




    public WeatherBitTask(Activity activity,MimageLoader image){
        this.imageLoader = image;
        this.activity = activity;
        weatherBitMap = new WeatherBitMap();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //WeatherBit Textview Initialization
        weatherBitTempOnView = activity.findViewById(R.id.weatherbit_temp);
        weatherBitCityOnView = activity.findViewById(R.id.weatherbit_city);
        weatherBitDescriptionOnView = activity.findViewById(R.id.weatherbit_description);
        weatherBitWindSpeedOnView = activity.findViewById(R.id.weatherbit_windspeed);
        weatherBitimageView = activity.findViewById(R.id.weatherbitImage);
    }

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
