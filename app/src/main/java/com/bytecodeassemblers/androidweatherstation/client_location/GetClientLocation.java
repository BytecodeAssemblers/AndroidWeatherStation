package com.bytecodeassemblers.androidweatherstation.client_location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import static java.lang.String.valueOf;


public class GetClientLocation extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private FusedLocationProviderClient client;
    private TextView myLocationResult;
    private  String latitude;
    private String longitude;
    private LocationManager locationManager;
    private Activity activity;


    public GetClientLocation(){


    }



    public  GetClientLocation(MainActivity activity) {
        client = LocationServices.getFusedLocationProviderClient(activity);
        this.activity = activity;

        locationManager=(LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);
        ActivityCompat.requestPermissions(activity,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

        //Check gps is enable or not
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps
            Toast.makeText(activity, "Please enable GPS", Toast.LENGTH_SHORT).show();
            OnGPS();
        }
        else
        {
            //GPS is already On then
            getLocation(activity);

        }
    }


    public void getLocation(final Activity activity) {
        /*
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (LocationGps != null) {
                double lat = LocationGps.getLatitude();
                double longi = LocationGps.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                myLocationResult.setText("Your Location:" + "\n" + "Latitude= " + latitude + "\n" + "Longitude= " + longitude);
                Common.setLon(longitude);
                Common.setLat(latitude);
            } else if (LocationNetwork != null) {
                double lat = LocationNetwork.getLatitude();
                double longi = LocationNetwork.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                myLocationResult.setText("Your Location:" + "\n" + "Latitude= " + latitude + "\n" + "Longitude= " + longitude);
                Common.setLon(longitude);
                Common.setLat(latitude);
            } else if (LocationPassive != null) {
                double lat = LocationPassive.getLatitude();
                double longi = LocationPassive.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                myLocationResult.setText("Your Location:" + "\n" + "Latitude= " + latitude + "\n" + "Longitude= " + longitude);
                Common.setLon(longitude);
                Common.setLat(latitude);
            } else {
                Toast.makeText(activity, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
        }
*/
        LocationManager locationManager = (LocationManager)
                this.activity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }

    }


    public void OnGPS (){
        final  AlertDialog.Builder builder = new AlertDialog.Builder(activity.getApplicationContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {

            MainActivityController controller = new MainActivityController(activity);
            controller.setLat(loc.getLatitude());
            controller.setLon(loc.getLongitude());
            controller.OpenWeatherTask(); // it gives the ability to the controller to set the lon and lat
            controller.WeatherBitTask();

        }


        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}


    }


}


