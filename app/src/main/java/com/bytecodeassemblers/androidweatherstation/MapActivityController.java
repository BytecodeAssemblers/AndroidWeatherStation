package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class MapActivityController {


    private Common commonObject;
    Button openMapActivityButton;
    private SearchView searchView;
    private Activity activity;
    private String selectedCoord;


    private String[] coords;

    public MapActivityController(Activity activity){
        this.activity = activity;
        commonObject = new Common();
        init();
        buttonClick();
    }


    public void init(){
        openMapActivityButton = activity.findViewById(R.id.viewMap);
        searchView = activity.findViewById(R.id.searchView);
    }

    public void parseSearchView(){
        selectedCoord = String.valueOf(searchView.getQuery()); //get text from SearchView
        String[] coords = selectedCoord.split(",");  //separate coordinates
        commonObject.setLat(coords[0]);  //set latitude
        commonObject.setLon(coords[1]);  //set longitude
    }


    public void openMapActivity(){
        Intent intent = new Intent(activity, GoogleMapActivity.class);
        activity.startActivity(intent);
    }


    public  void  buttonClick(){
        openMapActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseSearchView();
                MainActivityController mainActivityController = new MainActivityController(activity);
                openMapActivity();
            }
        });
    }
}
