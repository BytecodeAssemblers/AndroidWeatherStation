package com.bytecodeassemblers.androidweatherstation;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class MainActivityStateManager {


    private static final String PREFS_NAME ="MainActivityState";

    private static SharedPreferences mainActivityStatePrefs;

    private String loadActivityValues;

    public MainActivityStateManager(Context context){
        mainActivityStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveActivityState(TextView cityName,TextView description, TextView temp,String imageUrl) {
        String savedActivityValues = cityName.getText()+"-,"+description.getText()+"-,"+temp.getText()+"-,"+imageUrl;
        SharedPreferences.Editor editor = mainActivityStatePrefs.edit();
        editor.putString("MainActivity_Values",savedActivityValues);
        editor.commit();
    }

    public String loadActivityState(){
        loadActivityValues = mainActivityStatePrefs.getString("MainActivity_Values","oopsDintWork");
        return  loadActivityValues;
    }
    
}
