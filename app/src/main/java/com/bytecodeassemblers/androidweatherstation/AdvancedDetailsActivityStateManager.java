package com.bytecodeassemblers.androidweatherstation;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.bytecodeassemblers.androidweatherstation.openWeather_model.OpenWeatherModel;
import com.bytecodeassemblers.androidweatherstation.weatherBitModel.WeatherBitModel;

public class AdvancedDetailsActivityStateManager {


    private static final String PREFS_NAME ="AdvancedDetailsActivityState";

    private static SharedPreferences AdvancedDetailsharedPrefs;

    private String loadAdvancedDetailsActivityState;


    public AdvancedDetailsActivityStateManager(Context context){
        AdvancedDetailsharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveAdvancedActivityState(String UIData) {
        SharedPreferences.Editor editor = AdvancedDetailsharedPrefs.edit();
        if(AdvancedDetailsharedPrefs!=null){
            editor.remove("Advanced_Values");
            editor.putString("Advanced_Values",UIData);
            editor.commit();
        }
    }

    public String loadAdvancedDetailsActivityState(){
        loadAdvancedDetailsActivityState = AdvancedDetailsharedPrefs.getString("Advanced_Values","Oops!Something went wrong!");
        return  loadAdvancedDetailsActivityState;
    }

    public boolean checkStatus(){
        if(loadAdvancedDetailsActivityState()!=null){
            return  true;
        }else{
            return false;
        }
    }



}
