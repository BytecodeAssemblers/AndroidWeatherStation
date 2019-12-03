package com.bytecodeassemblers.androidweatherstation;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;

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


        ConstraintLayout constraintLayout = findViewById(R.id.layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        GetClientLocation clientLocation = new GetClientLocation(this);
        //mainActivityController = new MainActivityController(this);


        weatherHistoryButton = findViewById(R.id.buttonHistoryActivity);
        weatherHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherHistoryActivity = new WeatherHistoryActivity(mainView);
                Intent intent = new Intent(mainView, WeatherHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

}
