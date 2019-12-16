package com.bytecodeassemblers.androidweatherstation;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapStateManager {

    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String ZOOM = "zoom";
    private static final String BEARING = "bearing";
    private static final String TILT = "tilt";
    private static final String MARKER_STATUS = "marker";


    private static final String PREFS_NAME ="GoogleMapState";

    private SharedPreferences mapStatePrefs;
    private Context context;

    private double latitude;
    private double longitude;
    private LatLng target;
    private boolean markerStatus;

    public GoogleMapStateManager(Context context) {
        mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void saveMapState(GoogleMap mapMie) {
        SharedPreferences.Editor editor = mapStatePrefs.edit();
        CameraPosition position = mapMie.getCameraPosition();
        editor.putFloat(LATITUDE, (float) position.target.latitude);
        editor.putFloat(LONGITUDE, (float) position.target.longitude);
        editor.putFloat(ZOOM, position.zoom);
        editor.putFloat(TILT, position.tilt);
        editor.putFloat(BEARING, position.bearing);
        editor.commit();
    }

    public CameraPosition getSavedCameraPosition() {
        latitude = mapStatePrefs.getFloat(LATITUDE, 0);
        if (latitude == 0) {
            return null;
        }

        longitude = mapStatePrefs.getFloat(LONGITUDE, 0);
        target = new LatLng(latitude, longitude);

        float zoom = mapStatePrefs.getFloat(ZOOM, 0);
        float bearing = mapStatePrefs.getFloat(BEARING, 0);
        float tilt = mapStatePrefs.getFloat(TILT, 0);

        CameraPosition position = new CameraPosition(target, zoom, tilt, bearing);
        return position;
    }


    public String getSavedExactLocationAddress(){
        Geocoder geocoder= new Geocoder(context,Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude,longitude , 1);  //get specific address for latitude and longtitude given
            latitude = addresses.get(0).getLatitude();
            longitude = addresses.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0);
    }

    public void saveMarkerStatus(){
        SharedPreferences.Editor editor = mapStatePrefs.edit();
        editor.putBoolean(MARKER_STATUS,true);
        editor.commit();
    }

    public boolean getSavedMarkerStatus(){
        markerStatus = mapStatePrefs.getBoolean(MARKER_STATUS,false);
        return  this.markerStatus;
    }

    public LatLng getSavedMarkerPosition(){
        return target;
    }

}
