package com.bytecodeassemblers.androidweatherstation.client_location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.*;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;
import static java.lang.String.valueOf;


public class GetClientLocation extends Activity  {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    private TextView myLocationResult;
    private LocationManager locationManager;
    private Location location;
    private Activity activity;


    private double latitude;
    private double longitude;

    //flag for GPS status
    private boolean isGPSEnabled = false;

    //flag for Network status
    private boolean isNetworkEnabled = false;


    public GetClientLocation(){


    }

    MainActivityController  mainActivityController;

    @SuppressLint("MissingPermission")
    public  GetClientLocation(MainActivityController mainController, MainActivity activity) {

        this.activity = activity;

         locationManager = (LocationManager)
               this.activity.getSystemService(Context.LOCATION_SERVICE);

         //getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);


         //getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissionRequest();

        this.mainActivityController = mainController;

        //Check gps is enable or not
        if (getLocationFromGpsProvider()) {


        } else if (getLocationFromNetworkProvider()) {


            mainActivityController.setLatitude(latitude);
            mainActivityController.setLongitude(longitude);

            mainActivityController.ExecuteWeatherBitTask();
            mainActivityController.ExecuteOpenWeatherTask();

        }

        LocationListener locationListener = new MyLocationListener();
        //GPS is already On then
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 300000, 100, locationListener);


    }



    public void permissionRequest(){
            while (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }
    }

    @SuppressLint("MissingPermission")
    public boolean getLocationFromNetworkProvider(){
        if(isNetworkEnabled){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location!=null){
                this.latitude = location.getLatitude();
                this.longitude = location.getLongitude();
                return true;
            }
        }
        return false;
    }


    @SuppressLint("MissingPermission")
    public boolean getLocationFromGpsProvider(){
        if(isGPSEnabled) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                this.latitude = location.getLatitude();
                this.longitude = location.getLongitude();
                return true;
            }
        } else{
            showSettingsAlert();
        }
        return false;
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }



    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {
            if(loc!=null){
                mainActivityController.setLatitude(loc.getLatitude());
                mainActivityController.setLongitude(loc.getLongitude());

                mainActivityController.ExecuteWeatherBitTask();
                mainActivityController.ExecuteOpenWeatherTask();

                Toast.makeText(activity,"Yor location changed: "+location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }

        }


        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

    }


}


