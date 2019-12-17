package com.bytecodeassemblers.androidweatherstation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AdvancedDetailsActivityStateManager {

    private static final String PREFS_NAME ="AdvancedDetailsActivityState";
    private static SharedPreferences AdvancedDetailsharedPrefs;

    public AdvancedDetailsActivityStateManager(Context context){
        AdvancedDetailsharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    }

    public static void saveAdvancedActivityState(String UIData) {
        if(AdvancedDetailsharedPrefs!=null){
            Editor editor = AdvancedDetailsharedPrefs.edit();
            editor.remove("Advanced_Values");
            editor.putString("Advanced_Values",UIData);
            editor.commit();
        } else
        {

        }
    }

    private String loadAdvancedDetailsActivityState(){
        String loadAdvancedDetailsActivityState = AdvancedDetailsharedPrefs.getString("Advanced_Values"," ");
        return  loadAdvancedDetailsActivityState;
    }

    public static boolean checkStatus(){
        if(AdvancedDetailsharedPrefs.getString("Advanced_Values"," ").isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public void getSavedAdvancedData(Intent intent){    //send saved values to AdvancedDetailsActivity
        String[] result =  loadAdvancedDetailsActivityState().split(",");
        /*OpenWeather Data Send To Advanced Details*/
        try {
            intent.putExtra("Main_Temp", result[0]);
            intent.putExtra("Minimum_Temp", result[1]);
            intent.putExtra("Maximum_Temp", result[2]);
            intent.putExtra("Description", result[3]);
            intent.putExtra("WindSpeed", result[4]);
            intent.putExtra("Humidity", result[5]);
            /*WeatherBit Data Send To Advanced Details*/
            intent.putExtra("weatherbit_city", result[6]); /*--MISTAKE!!!! getOpenweather in Weatherbit, i will rectify!!!--*/
            intent.putExtra("weatherbit_temperature", result[2]); //max temp
            intent.putExtra("weatherbit_windSpeed", result[1]);  //min temp
            intent.putExtra("weatherbit_description", result[7]);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

}
