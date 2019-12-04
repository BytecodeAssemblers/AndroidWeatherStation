package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AdvancedDetailsActivity extends AppCompatActivity {



    MainActivityController mainActivityController;

    public AdvancedDetailsActivity(){

    }


    public AdvancedDetailsActivity(MainActivityController mainActivityController){

        this.mainActivityController = mainActivityController;
          this.mainActivityController.ExecuteOpenWeatherTask();
            this.mainActivityController.ExecuteWeatherBitTask();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_details);

    }
}
