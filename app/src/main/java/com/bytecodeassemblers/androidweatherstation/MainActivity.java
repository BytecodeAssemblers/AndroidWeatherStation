package com.bytecodeassemblers.androidweatherstation;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;




public class MainActivity extends AppCompatActivity {

    private MainActivity mainView = this;
    private MainActivityController mainActivityController;

    //private GetClientLocation getClientLocation;

    private WeatherHistoryActivity weatherHistoryActivity;
    Button weatherHistoryButton;

    private boolean isGPSEnabled = false;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getClientLocation = new GetClientLocation(this);
        TextView textView = findViewById(R.id.updated_at);
        Date date = Calendar.getInstance().getTime();   DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy");   String strDate = dateFormat.format(date);
        textView.setText(strDate);


        ConstraintLayout constraintLayout = findViewById(R.id.layout);



        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mainActivityController = new MainActivityController(this);

        GetClientLocation clientLocation = new GetClientLocation(mainActivityController, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
           getMenuInflater().inflate(R.menu.main_menu, menu);
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
                TextView cityTextView =  findViewById(R.id.weatherbit_city2);
                String cityName = cityTextView.getText().toString();
                intent = new Intent(mainView, WeatherHistoryActivity.class);
                intent.putExtra("cityName", cityName);
                startActivity(intent);
                return true;
            case R.id.advanceddetails:
                intent = new Intent(mainView, AdvancedDetailsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:

                return true;
            case R.id.location:
                mainActivityController.parseSearchView();
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
            }
        }
    }

}
