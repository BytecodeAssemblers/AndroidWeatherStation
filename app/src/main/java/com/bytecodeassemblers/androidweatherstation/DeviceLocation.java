package com.bytecodeassemblers.androidweatherstation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;


public class DeviceLocation {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    private Activity activity;
    private LocationManager locationManager;
    private Common commonObject;

    private TextView myLocationResult;


    public DeviceLocation(Activity activity) {
        this.activity = activity;
        //get client permission
        locationManager = (LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);
        ActivityCompat.requestPermissions(activity, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

        //initialize text view for coordinates result
        myLocationResult = activity.findViewById(R.id.locationResult);

        //get coordinates using network provider
        @SuppressLint("MissingPermission")
        Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


        if (LocationNetwork != null) {
            double lat = LocationNetwork.getLatitude();
            double lon = LocationNetwork.getLongitude();

            //set coordinates in common class
            commonObject = new Common();
            commonObject.setLat(String.valueOf(lat));
            commonObject.setLon(String.valueOf(lon));

            myLocationResult.setText("Your Location:" + "\n" + "Latitude= " + lat + "\n" + "Longitude= " + lon);
        }else{
            LocationListener locationListener = new MyLocationListener();
        }

    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            String latitude;
            String longitude;

            Toast.makeText(
                    activity.getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            commonObject.setLon(String.valueOf(loc.getLongitude()));
            commonObject.setLat(String.valueOf(loc.getLatitude()));


           latitude = String.valueOf(loc.getLatitude());
            longitude = String.valueOf(loc.getLongitude());
            Log.v(TAG, longitude);
            Log.v(TAG, latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(activity.getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
