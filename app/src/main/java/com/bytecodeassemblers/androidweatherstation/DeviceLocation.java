package com.bytecodeassemblers.androidweatherstation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
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
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;

    private Activity activity;
    private LocationManager locationManager;
    private Common commonObject;

    private TextView myLocationResult;

    private static double deviceLatitude;
    private static double deviceLongitude;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for Network status
    boolean isNetowrkEnabled = false;

    boolean testing = false;

    public DeviceLocation(Activity activity) {
        this.activity = activity;
        commonObject = new Common();

        locationManager = (LocationManager) activity.getSystemService(activity.getApplicationContext().LOCATION_SERVICE);

//        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            //Permission Request
//            ActivityCompat.requestPermissions(activity, new String[]
//                    {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
//        } else{
//            // get Network status
//            isNetowrkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            // get GPS status
//            isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            startService();
//            commonObject.setLat(String.valueOf(this.deviceLatitude));
//            commonObject.setLon(String.valueOf(this.deviceLongitude));
//        }


        //Permission Request
        ActivityCompat.requestPermissions(activity, new String[]
                {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

        //get Network status
        isNetowrkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        //get GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        startService();


        commonObject.setLat(String.valueOf(this.deviceLatitude));
        commonObject.setLon(String.valueOf(this.deviceLongitude));

        //initialize text view for coordinates result
        myLocationResult = activity.findViewById(R.id.locationResult);


        MyLocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
        //
    }



    public void startService(){
        if(isNetowrkEnabled){
            getCoordinatesFromNetwork();
        }
        else if(isGPSEnabled){
            getCoordinatesFromGPS();
        }
    }




    public boolean getCoordinatesFromNetwork(){

        if(isNetowrkEnabled) {
            //takes location using network provider
            @SuppressLint("MissingPermission")
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (LocationNetwork != null ) {

                this.deviceLatitude = LocationNetwork.getLatitude();
                this.deviceLongitude = LocationNetwork.getLongitude();

                Toast.makeText(
                        activity.getBaseContext(),
                        "Success Network Response", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }


    public  boolean getCoordinatesFromGPS(){

        if(isGPSEnabled) {
            //takes location using GPS provider
            @SuppressLint("MissingPermission")
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (locationGPS != null) {
                this.deviceLatitude = locationGPS.getLatitude();
                this.deviceLongitude = locationGPS.getLongitude();

                Log.v(TAG,"Success GPS Response");

                Toast.makeText(
                        activity.getBaseContext(),
                        "Success GPS Response", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }



    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {



            commonObject.setLon(String.valueOf(loc.getLongitude()));
            commonObject.setLat(String.valueOf(loc.getLatitude()));


            Toast.makeText(
                    activity.getBaseContext(),
                    "Location changed: Lat from Common: " + commonObject.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();

            deviceLatitude = loc.getLatitude();
            deviceLongitude = loc.getLongitude();

            myLocationResult.setText("Your Location:" + "\n" + "Latitude= " + commonObject.getLatitude() + "\n" + "Longitude= " + deviceLongitude);
//            Log.v(TAG, );
//            Log.v(TAG, deviceLongitude);

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
