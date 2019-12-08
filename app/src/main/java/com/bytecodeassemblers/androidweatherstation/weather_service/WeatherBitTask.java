package com.bytecodeassemblers.androidweatherstation.weather_service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.MimageLoader;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitModel;

public class WeatherBitTask extends AsyncTask<String,Void, WeatherBitModel> {


    private Activity activity;
    private MimageLoader imageLoader;


    //WeatherBit
    private WeatherBitModel weatherBitModel;
    private TextView weatherBitTempOnView;
    private TextView weatherBitCityOnView2;
    private TextView weatherBitCityOnView;
    private TextView weatherBitDescriptionOnView;
    private TextView weatherBitWindSpeedOnView;
    private NetworkImageView weatherBitimageView;




    public WeatherBitTask(Activity activity,MimageLoader image){
        this.imageLoader = image;
        this.activity = activity;
        weatherBitModel = new WeatherBitModel();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //WeatherBit Textview Initialization
        weatherBitCityOnView2 = activity.findViewById(R.id.weatherbit_city2);
        weatherBitTempOnView = activity.findViewById(R.id.weatherbit_temp);
        weatherBitCityOnView = activity.findViewById(R.id.weatherbit_city);
        weatherBitDescriptionOnView = activity.findViewById(R.id.weatherbit_description);
        weatherBitWindSpeedOnView = activity.findViewById(R.id.weatherbit_windspeed);
        weatherBitimageView = activity.findViewById(R.id.weatherbitImage);
    }

    @Override
    protected WeatherBitModel doInBackground(String... strings) {
        String data = ((new WeatherHttpClient()).getWeatherData(strings[0])); // get data from weatherBit using http request
        weatherBitModel = JSONWeatherParser.getWeatherBitData(data);  //sends the response values into parse
        imageLoader.setImageLoader();
        return weatherBitModel;
    }

    @Override
    protected void onPostExecute(WeatherBitModel weatherBitModel) {
        super.onPostExecute(weatherBitModel);
        weatherBitCityOnView2.setText(""+ weatherBitModel.getCityName());
//
//        weatherBitTempOnView.setText("Temp: "+ weatherBitModel.getTemp());
//        weatherBitCityOnView.setText("City: "+ weatherBitModel.getCityName());
//        weatherBitDescriptionOnView.setText("Description: "+ weatherBitModel.getDescription());
//        weatherBitWindSpeedOnView.setText("Wind speed: "+ weatherBitModel.getSpeed());
//        weatherBitimageView.setImageUrl(weatherBitModel.getImage(weatherBitModel.getIcon()),imageLoader.getmImageLoader());
    }

}
