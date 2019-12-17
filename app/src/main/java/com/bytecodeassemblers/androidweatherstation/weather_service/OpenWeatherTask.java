package com.bytecodeassemblers.androidweatherstation.weather_service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.MainActivityController;
import com.bytecodeassemblers.androidweatherstation.MainActivityStateManager;
import com.bytecodeassemblers.androidweatherstation.MimageLoader;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;

public class OpenWeatherTask extends AsyncTask<String,Void, OpenWeatherModel> {

    private Activity activity;
    private  MainActivityController mainActivityController;

    private TextView openWeatherMainActivityDescription;
    private TextView generalTemp;
    private TextView cityOnMainActivityView;
    private NetworkImageView openWeatherImageView;

    private OpenWeatherModel openWeatherModel;
    private MimageLoader imageLoader;

    public OpenWeatherTask(Activity activity, MainActivityController mainActivityController, MimageLoader image){
        this.imageLoader = image;
        this.activity=activity;
        this.mainActivityController=mainActivityController;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        cityOnMainActivityView = activity.findViewById(R.id.weatherbitMainActivityCityName);
        openWeatherMainActivityDescription = activity.findViewById(R.id.mainActivityWeatherDescription);
        generalTemp = activity.findViewById(R.id.temp);
        openWeatherImageView = activity.findViewById(R.id.openWeatherImage);
    }

    @Override
    protected OpenWeatherModel doInBackground(String... strings) {
         String responseData = ((new WeatherHttpClient()).getWeatherData(strings[0])); // get data from openWeather using http request
         openWeatherModel = JSONWeatherParser.getOpenWeatherData(responseData); //sends the response values into parse
         return openWeatherModel;
        }

    @Override
    protected void onPostExecute(OpenWeatherModel openWeather) {
         super.onPostExecute(openWeather);
         cityOnMainActivityView.setText(mainActivityController.GetExactLocationAddress());
         openWeatherMainActivityDescription.setText(openWeather.getDescription());
         generalTemp.setText(openWeather.getTemp()+ "°C");
         //openWeatherImageView.setImageUrl(openWeather.getImage(openWeather.getIcon()),imageLoader.getmImageLoader());

         this.mainActivityController.setOpenWeatherModel(openWeather);

         String imageUrl = openWeatherModel.getImage(openWeatherModel.getIcon());
         MainActivityStateManager.saveActivityState(cityOnMainActivityView,openWeatherMainActivityDescription,generalTemp,imageUrl);
        }
}
