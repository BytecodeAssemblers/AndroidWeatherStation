package com.bytecodeassemblers.androidweatherstation.weatherBitModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

public class MainActivityStateManager {


    private static final String PREFS_NAME ="MainActivityState";
    private static SharedPreferences weatherPreferences;

    public MainActivityStateManager(Context context){
        weatherPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void saveActivityState(TextView cityName, TextView description, TextView temp) {
        String savedActivityValues = cityName.getText()+"-,"+description.getText()+"-,"+temp.getText()+"-,";
        if(weatherPreferences !=null){
            SharedPreferences.Editor editor = weatherPreferences.edit();
            editor.remove("MainActivity_Values");
            editor.putString("MainActivity_Values",savedActivityValues);
            editor.apply();
        }
    }

    public String loadActivityState(){
        AtomicReference<String> loadWeatherPreferences = new AtomicReference<>(weatherPreferences.getString("MainActivity_Values", "currentlocation"));
        return loadWeatherPreferences.get();
    }
    
}
