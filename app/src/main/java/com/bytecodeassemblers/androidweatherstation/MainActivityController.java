package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.bytecodeassemblers.androidweatherstation.data.JSONWeatherParser;
import com.bytecodeassemblers.androidweatherstation.data.WeatherHttpClient;
import com.bytecodeassemblers.androidweatherstation.listview.ListViewActivity;
import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitModel;
import com.bytecodeassemblers.androidweatherstation.weather_service.WeatherBitTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivityController {

    private OpenWeatherModel openWeatherModel;
    private WeatherBitModel weatherBitModel;

    private LocationRepo locationRepo;
    private MainActivityStateManager mainActivityStateManager;
    private MimageLoader imageLoader;
    private Activity activity;

    private  double lat;
    private  double lon;

    private TextView openWeatherMainActivityDescription;
    private TextView generalTemp;
    private TextView cityOnMainActivityView;
    private NetworkImageView openWeatherImageView;


    public MainActivityController(MainActivity activity){
        this.activity=activity;
        locationRepo  = new LocationRepo(activity);
        InitializeComponent();
        mainActivityStateManager = new MainActivityStateManager(activity);
        imageLoader.setImageLoader();
        setUiFromSharedPref();
    }

    public void InitializeComponent() {
        imageLoader = new MimageLoader(activity);
        cityOnMainActivityView = activity.findViewById(R.id.weatherbitMainActivityCityName);
        openWeatherMainActivityDescription = activity.findViewById(R.id.mainActivityWeatherDescription);
        generalTemp = activity.findViewById(R.id.temp);
        openWeatherImageView = activity.findViewById(R.id.openWeatherImage);
    }

    public void setUiFromSharedPref(){
        try{
            if(mainActivityStateManager.checkStatus()){
                String[] result =  mainActivityStateManager.loadActivityState().split("-,");
                cityOnMainActivityView.setText(result[0]);
                openWeatherMainActivityDescription.setText(""+ result[1]);
                generalTemp.setText(result[2]);
                openWeatherImageView.setImageUrl(result[3],imageLoader.getmImageLoader());
            }
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    public void ExecuteOpenWeatherTask(){
        String url = Common.openWeatherRequestLink(lat,lon);
        new OpenWeatherTask().execute(url);
    }

    public void ExecuteWeatherBitTask(){
        String url = Common.weatherBitRequestLink(lat,lon);
        new WeatherBitTask(activity,this,imageLoader).execute(url);
    }


    public void savedLocation(){
        try {
            JSONObject latLong = new JSONObject();
            latLong.put("cityName", GetExactLocationAddress());
            latLong.put("lat", lat);
            latLong.put("lon", lon);
            locationRepo.addLocationReg(latLong);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String GetExactLocationAddress(){
        Geocoder geocoder= new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = null;
        try {
                addresses = geocoder.getFromLocation(lat,lon , 1);  //get specific address for latitude and longtitude given
                 lat = addresses.get(0).getLatitude();
                 lon = addresses.get(0).getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0);
    }


    public void openMapActivity(){
        Intent intent = new Intent(activity, GoogleMapActivity.class);
        activity.startActivityForResult(intent,1 );
    }


    public void openListViewActivity(){
        Intent intent = new Intent(this.activity, ListViewActivity.class);
        intent.putExtra("map",locationRepo.loadMap().toString());
        activity.startActivityForResult(intent,2);
    }

    private class OpenWeatherTask extends AsyncTask<String,Void, OpenWeatherModel> {
        @Override
        protected OpenWeatherModel doInBackground(String... strings) {
            String responseData = ((new WeatherHttpClient()).getWeatherData(strings[0])); // get data from openWeather using http request
            openWeatherModel = JSONWeatherParser.getOpenWeatherData(responseData); //sends the response values into parse
            return openWeatherModel;
        }
        @Override
        protected void onPostExecute(OpenWeatherModel openWeather) {
            super.onPostExecute(openWeather);
            cityOnMainActivityView.setText(""+ GetExactLocationAddress());
            openWeatherMainActivityDescription.setText(openWeather.getDescription());
            generalTemp.setText(openWeather.getTemp()+ "°C");
            openWeatherImageView.setImageUrl(openWeather.getImage(openWeather.getIcon()),imageLoader.getmImageLoader());

            String imageUrl = openWeather.getImage(openWeather.getIcon());
            mainActivityStateManager.saveActivityState(cityOnMainActivityView,openWeatherMainActivityDescription,generalTemp,imageUrl);
        }
    }

    public OpenWeatherModel getOpenWeatherModel() {
        return openWeatherModel;
    }

    public WeatherBitModel getWeatherBitModel() {
        return weatherBitModel;
    }

    public Activity getActivity() {
        return activity;
    }

    public  void setLatitude(double latitude){
        lat = latitude;
    }

    public  void setLongitude(double longitude){
        lon = longitude;
    }

    public void setWeatherBitModel(WeatherBitModel weatherBitModel) {
        this.weatherBitModel = weatherBitModel;
    }

}
