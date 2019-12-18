package com.bytecodeassemblers.androidweatherstation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    private MainActivity mainView = this;
    private MainActivityController mainActivityController;

    private GetClientLocation getClientLocation;
    private Menu optionsMenu;
    private WeatherHistoryActivity weatherHistoryActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*--This is a message, that informs a user how to use the app the first time--*/
            View parentLayout = findViewById(android.R.id.content);
            final Snackbar snackbar = Snackbar.make(parentLayout, "Please, Choose a location from the map or check Enable Gps from the Menu", Snackbar.LENGTH_LONG);
            snackbar.setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            }).setActionTextColor(getResources().getColor(android.R.color.holo_orange_dark)).show();
            /*--End of Message--*/


        TextView textView = findViewById(R.id.updated_at);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        String fetchDate = dateFormat.format(date);
        textView.setText(fetchDate);
        Toolbar toolbar = findViewById(R.id.advancedDetailsToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        mainActivityController = new MainActivityController(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
           getMenuInflater().inflate(R.menu.main_menu, menu);
           optionsMenu = menu;
           return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent ;
        //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;


            case R.id.weatherdiagram:
                TextView cityTextView =  findViewById(R.id.weatherbitMainActivityCityName);
                String cityName = cityTextView.getText().toString();
                intent = new Intent(mainView, WeatherHistoryActivity.class);
                intent.putExtra("cityName", cityName);
                startActivity(intent);
                return true;
            case R.id.advanceddetails:
                AdvancedDetailsActivityStateManager advancedDetailsActivityStateManager = new AdvancedDetailsActivityStateManager(this);
                intent = new Intent(mainView, AdvancedDetailsActivity.class);
                if(this.mainActivityController.getWeatherBitModel() == null && this.mainActivityController.getOpenWeatherModel() == null)
                {
                    if (advancedDetailsActivityStateManager.checkStatus()) { //if there is some data saved
                        advancedDetailsActivityStateManager.getSavedAdvancedData(intent); //set data to Advanced Activity
                    } else {                     //if there is not saved data...

                    }
                }else {
                    /*OpenWeather Data Send To Advanced Details*/
                    intent.putExtra("Main_Temp","Temperature : " + this.mainActivityController.getOpenWeatherModel().getTemp() + "°C");
                    intent.putExtra("Minimum_Temp","Maximum Temperature : " + this.mainActivityController.getOpenWeatherModel().getTempMin() + "°C");
                    intent.putExtra("Maximum_Temp", "Minimum Temperature : " + this.mainActivityController.getOpenWeatherModel().getTempMax() + "°C");
                    intent.putExtra("Description","Weather Description : " + this.mainActivityController.getOpenWeatherModel().getDescription());
                    intent.putExtra("WindSpeed","WindSpeed : " + this.mainActivityController.getOpenWeatherModel().getSpeed()+"m/s");
                    intent.putExtra("Humidity","Humidity : " + this.mainActivityController.getOpenWeatherModel().getHumidity()+"%");
                    /*WeatherBit Data Send To Advanced Details*/
                    intent.putExtra("weatherbit_city","City Name :" +this.mainActivityController.getWeatherBitModel().getCityName());
                    intent.putExtra("weatherbit_temperature", "Temperature : "+this.mainActivityController.getWeatherBitModel().getTemp() + "°C");
                    intent.putExtra("weatherbit_windSpeed", "WindSpeed : "+this.mainActivityController.getWeatherBitModel().getSpeed() + "m/s");
                    intent.putExtra("weatherbit_description", "Weather Description : "+this.mainActivityController.getOpenWeatherModel().getDescription());
                    //advancedDetailsActivityStateManager.saveAdvancedActivityState(AppendData());
                }
                startActivity(intent);
                return true;
            case R.id.myPlaces:
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

    //save AdvancedDetailsActivity dATA



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
