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
    private static final String MARKER_LATITUDE = "markerLatitude";
    private static final String MARKER_LONGITUDE = "markerLongitude";


    private static final String PREFS_NAME ="GoogleMapState";

    private SharedPreferences mapStatePrefs;
    private Context context;

    private double cameraLatitude;
    private double cameraLongitude;
    private LatLng cameraTarget;

    private boolean markerStatus;
    private double markerLatitude;
    private double markerLongitude;
    private LatLng markerTarget;


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
        cameraLatitude = mapStatePrefs.getFloat(LATITUDE, 0);
        if (cameraLatitude == 0) {
            return null;
        }

        cameraLongitude = mapStatePrefs.getFloat(LONGITUDE, 0);
        cameraTarget = new LatLng(cameraLatitude, cameraLongitude);

        float zoom = mapStatePrefs.getFloat(ZOOM, 0);
        float bearing = mapStatePrefs.getFloat(BEARING, 0);
        float tilt = mapStatePrefs.getFloat(TILT, 0);

        CameraPosition position = new CameraPosition(cameraTarget, zoom, tilt, bearing);
        return position;
    }


    public String getSavedExactLocationAddress(){
        Geocoder geocoder= new Geocoder(context,Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(markerLatitude,markerLongitude , 1);  //get specific address for latitude and longtitude given
            markerLatitude = addresses.get(0).getLatitude();
            markerLongitude = addresses.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(0);
    }

    public void saveMarkerStatus(double markerLatitude,double markerLongitude){
        SharedPreferences.Editor editor = mapStatePrefs.edit();
        editor.putFloat(MARKER_LATITUDE,(float) markerLatitude);
        editor.putFloat(MARKER_LONGITUDE, (float) markerLongitude);
        editor.putBoolean(MARKER_STATUS,true);
        editor.commit();
    }

    public boolean getSavedMarkerStatus(){
        markerStatus = mapStatePrefs.getBoolean(MARKER_STATUS,false);
        return  this.markerStatus;
    }

    public LatLng getSavedMarkerPosition(){
        markerLatitude = mapStatePrefs.getFloat(MARKER_LATITUDE, 1);
        markerLongitude= mapStatePrefs.getFloat(MARKER_LONGITUDE,1);
        markerTarget = new LatLng(markerLatitude,markerLongitude);
        return markerTarget;
    }

}
