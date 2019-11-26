package com.bytecodeassemblers.androidweatherstation;

import android.app.Activity;
import android.app.admin.SystemUpdatePolicy;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapActivityController {


    private Activity activity;
    private Button mapButton;
    private EditText latitudeText;
    private EditText longitudeText;
    private Common commonObject;

    private MainActivityController mainActivityController;


    public MapActivityController(final Activity activity){
        this.activity=activity;
        initializeComponent();
        commonObject = new Common();
        buttonClick();
    }

public  void  buttonClick(){
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInput(latitudeText.getText().toString(),longitudeText.getText().toString());
            }
        });
    }

    public void setInput(String latitudeT, String longtitudeT){
        double value;

         if(!latitudeT.isEmpty() && !longtitudeT.isEmpty()) {
                   try {
                       value = Double.parseDouble(latitudeT);
                       value = Double.parseDouble(longtitudeT);
                       commonObject.setLat(latitudeT);
                       commonObject.setLon(longtitudeT);
                       mainActivityController = new MainActivityController(activity);
                       openMapActivity();
                      // it means it is double
                   } catch (Exception e1) {
                       Toast.makeText(activity, "Wrong coordinates", Toast.LENGTH_SHORT).show();
                       // this means it is not double
                    }
                }else{
                     Toast.makeText(activity, "Please give coordinates", Toast.LENGTH_SHORT).show();
                  // e1.printStackTrace();
                }
    }

    public void openMapActivity(){
        Intent intent = new Intent(activity, MapActivity.class);
        activity.startActivity(intent);
    }


    public void initializeComponent(){
        mapButton = activity.findViewById(R.id.mapButton);
        latitudeText  = activity.findViewById(R.id.latitudeText);
        longitudeText  = activity.findViewById(R.id.longitudeText);
    }
}
