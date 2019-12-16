package com.bytecodeassemblers.androidweatherstation;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
    private static final String MARKER_TITLE = "markerTitle";


    private static final String PREFS_NAME ="GoogleMapState";

    private SharedPreferences mapStatePrefs;


    private double cameraLatitude;
    private double cameraLongitude;

    private boolean markerStatus;
    private double markerLatitude;
    private double markerLongitude;


    public GoogleMapStateManager(Context context) {
        mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveCameraState(GoogleMap mapMie) {
        if(mapStatePrefs != null){
            SharedPreferences.Editor editor = mapStatePrefs.edit();
            CameraPosition position = mapMie.getCameraPosition();
            editor.putFloat(LATITUDE, (float) position.target.latitude);
            editor.putFloat(LONGITUDE, (float) position.target.longitude);
            editor.putFloat(ZOOM, position.zoom);
            editor.putFloat(TILT, position.tilt);
            editor.putFloat(BEARING, position.bearing);
            editor.commit();
        }
    }

    public CameraPosition getSavedCameraState() {
        cameraLatitude = mapStatePrefs.getFloat(LATITUDE, 0);
        if (cameraLatitude == 0) {
            return null;
        }
        cameraLongitude = mapStatePrefs.getFloat(LONGITUDE, 0);

        float zoom = mapStatePrefs.getFloat(ZOOM, 0);
        float bearing = mapStatePrefs.getFloat(BEARING, 0);
        float tilt = mapStatePrefs.getFloat(TILT, 0);

        CameraPosition position = new CameraPosition(new LatLng(cameraLatitude,cameraLongitude), zoom, tilt, bearing);
        return position;
    }


    public void saveMarkerState(Marker marker){
        if(mapStatePrefs != null) {
            SharedPreferences.Editor editor = mapStatePrefs.edit();
            editor.putFloat(MARKER_LATITUDE, (float) marker.getPosition().latitude);
            editor.putFloat(MARKER_LONGITUDE, (float) marker.getPosition().longitude);
            editor.putString(MARKER_TITLE, marker.getTitle());
            editor.putBoolean(MARKER_STATUS, true);
            editor.commit();
        }
    }

    public MarkerOptions getSavedMarkerState(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerLatitude = mapStatePrefs.getFloat(MARKER_LATITUDE, 1);
        markerLongitude= mapStatePrefs.getFloat(MARKER_LONGITUDE,1);

        markerOptions.position(new LatLng(markerLatitude,markerLongitude));
        markerOptions.title(mapStatePrefs.getString(MARKER_TITLE,"your location"));
        return markerOptions;
    }

    public boolean getSavedMarkerStatus(){
        markerStatus = mapStatePrefs.getBoolean(MARKER_STATUS,false);
        return  this.markerStatus;
    }
}
