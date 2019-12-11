package com.bytecodeassemblers.androidweatherstation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;
import com.bytecodeassemblers.androidweatherstation.listview.ListViewActivity;
import com.bytecodeassemblers.androidweatherstation.weather_service.OpenWeatherTask;
import com.bytecodeassemblers.androidweatherstation.weather_service.WeatherBitTask;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.HashMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    private MainActivity mainView = this;
    private MainActivityController mainActivityController;

    private GetClientLocation getClientLocation;
    Menu optionsMenu;
    private WeatherHistoryActivity weatherHistoryActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*--This is a message, that informs a user how to use the app the first time--*/
        View parentLayout = findViewById(android.R.id.content);

       final Snackbar snackbar = Snackbar.make(parentLayout,"Please, Choose a location from the map or check Enable Gps from the Menu",Snackbar.LENGTH_LONG);

              snackbar.setAction("Close", new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        snackbar.dismiss();
                    }
                        })
                .setActionTextColor(getResources().getColor(android.R.color.holo_blue_dark))
        .show();

              /*--End of Message--*/


        TextView textView = findViewById(R.id.updated_at);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String fetchDate = dateFormat.format(date);
        textView.setText(fetchDate);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        mainActivityController = new MainActivityController(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
           getMenuInflater().inflate(R.menu.main_menu, menu);
           optionsMenu = menu;
           return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent ;
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.settings:
                return true;
            case R.id.weatherdiagram:
                TextView cityTextView =  findViewById(R.id.weatherbitMainActivityCityName);
                String cityName = cityTextView.getText().toString();
                intent = new Intent(mainView, WeatherHistoryActivity.class);
                intent.putExtra("cityName", cityName);
                startActivity(intent);
                return true;
            case R.id.advanceddetails:
                intent = new Intent(mainView, AdvancedDetailsActivity.class);
                /*OpenWeather Data Send To Advanced Details*/
                intent.putExtra("Main_Temp",this.mainActivityController.getOpenWeatherModel().getTemp()+ "°C");
                intent.putExtra("Minimum_Temp",this.mainActivityController.getOpenWeatherModel().getTempMin()+ "°C");
                intent.putExtra("Maximum_Temp",this.mainActivityController.getOpenWeatherModel().getTempMax()+ "°C");
                intent.putExtra("Description",this.mainActivityController.getOpenWeatherModel().getDescription());
                intent.putExtra("WindSpeed",this.mainActivityController.getOpenWeatherModel().getSpeed());
                intent.putExtra("Humidity",this.mainActivityController.getOpenWeatherModel().getHumidity());
                /*WeatherBit Data Send To Advanced Details*/
                intent.putExtra("weatherbit_city",this.mainActivityController.getOpenWeatherModel().getTemp()+ "°C"); /*--MISTAKE!!!! getOpenweather in Weatherbit, i will rectify!!!--*/
                intent.putExtra("weatherbit_temperature",this.mainActivityController.getOpenWeatherModel().getTempMin()+ "°C");
                intent.putExtra("weatherbit_windSpeed",this.mainActivityController.getOpenWeatherModel().getTempMax()+ "°C");
                intent.putExtra("weatherbit_description",this.mainActivityController.getOpenWeatherModel().getDescription());

                startActivity(intent);
                return true;
            case R.id.about:
                mainActivityController.openListViewActivity();

                return true;
            case R.id.enableGps:
                boolean permissions = this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED;

                 if(!item.isChecked()){
                     if(!permissions){
                         requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                     } else {
                         item.setChecked(true);
                         getClientLocation = new GetClientLocation(mainActivityController, mainView);
                     }
                 }else
                 {
                     item.setChecked(false);
                     getClientLocation = null;
                 }
                return true;

            case R.id.location:
                mainActivityController.openMapActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.mainActivityController.setLatitude(data.getDoubleExtra("lat", 48.08));
                this.mainActivityController.setLongitude(data.getDoubleExtra("lon", 23.78));

                this.mainActivityController.ExecuteOpenWeatherTask();
                this.mainActivityController.ExecuteWeatherBitTask();

                this.mainActivityController.savedLocation();
            }
        }else if(requestCode == 2){
            if (resultCode == RESULT_OK) {

                this.mainActivityController.setLatitude(data.getDoubleExtra("latitude", 48.08));
                this.mainActivityController.setLongitude(data.getDoubleExtra("longitude", 23.78));

                this.mainActivityController.ExecuteOpenWeatherTask();
                this.mainActivityController.ExecuteWeatherBitTask();

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case LOCATION_REQUEST:
                if (PackageManager.PERMISSION_GRANTED==checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    optionsMenu.getItem(4).setChecked(true);
                    getClientLocation = new GetClientLocation(mainActivityController, mainView);
                }
                else
                {
                    optionsMenu.getItem(4).setChecked(false);
                    getClientLocation = null;
                }
                break;
        }
    }
}
