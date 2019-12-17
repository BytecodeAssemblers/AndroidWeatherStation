package com.bytecodeassemblers.androidweatherstation.weather_service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import com.bytecodeassemblers.androidweatherstation.AdvancedDetailsActivityStateManager;
import com.bytecodeassemblers.androidweatherstation.DatabaseApiInsert;
import com.bytecodeassemblers.androidweatherstation.MainActivityController;
import com.bytecodeassemblers.androidweatherstation.MimageLoader;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeatherBitTask extends AsyncTask<String,Void, WeatherBitModel> {


    private Activity activity;
    private MimageLoader imageLoader;


    //WeatherBit
    private WeatherBitModel weatherBitModel;
    private TextView weatherBitCityOnMainActivityView;


    private MainActivityController mainActivitycontroller;


    public WeatherBitTask(Activity activity,MainActivityController mainActivitycontroller, MimageLoader image){
        this.imageLoader = image;
        this.activity = activity;
        weatherBitModel = new WeatherBitModel();
	    this.mainActivitycontroller = mainActivitycontroller ;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // weatherBitCityOnMainActivityView = activity.findViewById(R.id.weatherbitMainActivityCityName);
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

      //  weatherBitCityOnMainActivityView.setText(""+ weatherBitModel.getCityName());

        Date date = Calendar.getInstance().getTime();   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String strDate = dateFormat.format(date);


        DatabaseApiInsert insertWeather = new DatabaseApiInsert();
        JSONObject weatherPayload = new JSONObject();
        try {
            weatherPayload.put("region",weatherBitModel.getCityName());
            weatherPayload.put("temperature",weatherBitModel.getTemp());
            weatherPayload.put("date",strDate);
            weatherPayload.put("description",weatherBitModel.getDescription());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        insertWeather.setPayload(weatherPayload);
        insertWeather.setContext(activity);
        insertWeather.setDatabaseInsertEndpoint("http://weatherassemble.hopto.org:8080/updateweatherhistory.php");
        insertWeather.executeInsert();

	   mainActivitycontroller.setWeatherBitModel(weatherBitModel);

       AdvancedDetailsActivityStateManager.saveAdvancedActivityState(AppendData());
    }

        //save AdvancedDetailsActivity dATA
    public String AppendData() {   //Append AdvancedDetails Texviews to save them later
        String resultStrings =this.mainActivitycontroller.getOpenWeatherModel().getTemp()+ "°C"+","+this.mainActivitycontroller.getOpenWeatherModel().getTempMin()+ "°C"+
                ","+ this.mainActivitycontroller.getOpenWeatherModel().getTempMax()+ "°C"+","+this.mainActivitycontroller.getOpenWeatherModel().getDescription()+
                ","+ this.mainActivitycontroller.getOpenWeatherModel().getSpeed()+","+this.mainActivitycontroller.getOpenWeatherModel().getHumidity()+
                ","+ weatherBitModel.getTemp()+ "°C"+","+weatherBitModel.getDescription();
        return resultStrings;
    }

}
