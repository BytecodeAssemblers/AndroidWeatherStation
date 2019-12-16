package com.bytecodeassemblers.androidweatherstation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMapStateManager googleMapStateManager;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMapStateManager = new GoogleMapStateManager(this);

        CameraPosition position = googleMapStateManager.getSavedCameraState();

        if (position != null) { //entering resume state
            CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(position);
            googleMapStateManager.getSavedCameraState();
            mMap.moveCamera(cameraUpdate);

            if(googleMapStateManager.getSavedMarkerStatus()){
                mMap.addMarker(googleMapStateManager.getSavedMarkerState());
            }
        }

        // Add a marker in current location
        /*String exactPosition =  addresses.get(0).getAddressLine(0);
        LatLng selectedPosition = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(selectedPosition).title(exactPosition));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(selectedPosition));

        float zoomLevel = 5.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPosition, zoomLevel));*/

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();

                Geocoder geocoder= new Geocoder(GoogleMapActivity.this,Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude , 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                marker =  mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getAddressLine(0)));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                googleMapStateManager.saveMarkerState(marker); //marker state has been save

                Toast.makeText(
                        GoogleMapActivity.this,
                        "Lat : " + latLng.latitude + " , "
                                + "Long : " + latLng.longitude,
                        Toast.LENGTH_LONG).show();


                Intent intent = new Intent();
                intent.putExtra("lat", latLng.latitude);
                intent.putExtra("lon", latLng.longitude);
                setResult(RESULT_OK, intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleMapStateManager.saveCameraState(mMap); //camera state has been save
        finish();
    }

}
