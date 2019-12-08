package com.bytecodeassemblers.androidweatherstation.client_location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.bytecodeassemblers.androidweatherstation.*;

public class GetClientLocation extends Activity  {

    private TextView myLocationResult;
    private LocationManager locationManager;
    private Location location;
    private Activity activity;

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
        this.mainActivityController = mainController;
        locationManager = (LocationManager)this.activity.getSystemService(Context.LOCATION_SERVICE);

         //getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

         //getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        LocationListener locationListener = new MyLocationListener();
        //GPS is already On then
        if(isGPSEnabled)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 100, locationListener);
        else if(isNetworkEnabled)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 100, locationListener);
        else
            Toast.makeText(activity,"Yor location won't be accounted for",Toast.LENGTH_SHORT).show();

    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            if(loc!=null){
                mainActivityController.setLatitude(loc.getLatitude());
                mainActivityController.setLongitude(loc.getLongitude());

                mainActivityController.ExecuteWeatherBitTask();
                mainActivityController.ExecuteOpenWeatherTask();
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


