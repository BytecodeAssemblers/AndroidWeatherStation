package com.bytecodeassemblers.androidweatherstation.weather_service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.MainActivityController;
import com.bytecodeassemblers.androidweatherstation.MimageLoader;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.MainActivityStateManager;

public class OpenWeatherTask extends AsyncTask<String,Void, OpenWeatherModel> {

    private MainActivityController mainActivityController;

    private MimageLoader imageLoader;
    private OpenWeatherModel openWeatherObject;

    private TextView cityOnMainActivityView;
    private TextView openWeathertempOnView;
    private TextView openWeathermaxTempOnView;
    private TextView openWeatherminTempOnView;
    private TextView openWeatherhumidityOnView;
    private TextView openWeatherdescriptionOnView;
    private TextView openWeatherwindSpeedOnView;
    private TextView openWeathercityNameOnView;
    private TextView openWeatherMainActivityDescription;
    private NetworkImageView openWeathermyImage;

    //mainView
    private TextView generalTemp;

   private Activity activity;

    public OpenWeatherTask(Activity activity,MainActivityController mainActivityController,MimageLoader image){
        this.mainActivityController = mainActivityController;
        this.activity=activity;
        this.imageLoader = image;
        openWeatherObject = new OpenWeatherModel();

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //OpenWeather TextView Initialization
       // openWeathercityNameOnView = activity.findViewById(R.id.address);
        openWeatherMainActivityDescription= activity.findViewById(R.id.mainActivityWeatherDescription);
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
    protected OpenWeatherModel doInBackground(String... strings) {
        String responseData = ((new WeatherHttpClient()).getWeatherData(strings[0]));
        openWeatherObject = JSONWeatherParser.getOpenWeatherData(responseData);
        imageLoader.setImageLoader();
        return openWeatherObject;
    }

    @Override
    protected void onPostExecute(OpenWeatherModel openWeatherModel) {
        super.onPostExecute(openWeatherModel);
        //openWeathercityNameOnView.setText(openWeatherModel.simple.getCityName());
//        openWeathertempOnView.setText("Temp: "+ openWeatherModel.getTemp());
//        openWeathermaxTempOnView.setText("Temp max: "+ openWeatherModel.getTempMax());
//        openWeatherminTempOnView.setText("Temp min: "+ openWeatherModel.getTempMin());
//        openWeatherhumidityOnView.setText("Humidity: "+ openWeatherModel.getHumidity());
//        openWeatherwindSpeedOnView.setText("Wind speed: "+ openWeatherModel.getSpeed());
//        openWeatherdescriptionOnView.setText("Description: "+ openWeatherModel.getDescription());
//        openWeathermyImage.setImageUrl(openWeatherModel.getImage(openWeatherModel.getIcon()),imageLoader.getmImageLoader());
        openWeatherMainActivityDescription.setText(""+ openWeatherModel.getDescription());
        generalTemp.setText(openWeatherModel.getTemp() + "°C");

try {
    cityOnMainActivityView.setText(mainActivityController.GetExactLocationAddress());
    openWeatherMainActivityDescription.setText(openWeatherModel.getDescription());
    this.mainActivityController.setOpenWeatherModel(openWeatherModel);

}catch (NullPointerException openWeatherDescription){
    openWeatherDescription.printStackTrace();
}
    }
}
