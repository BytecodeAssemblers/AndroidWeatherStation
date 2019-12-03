package com.bytecodeassemblers.androidweatherstation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Common commonObject;
    private double lat;
    private double lon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        commonObject = new Common();
        lat = Double.parseDouble(commonObject.getLatitude()); //takes latitude from user input
        lon = Double.parseDouble(commonObject.getLongitude()); //takes longitude from user input
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

        Geocoder geocoder= new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1); //get specific address for latitude and longtitude given
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Add a marker in current location
        String exactPosition =  addresses.get(0).getAddressLine(0);
        LatLng selectedPosition = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(selectedPosition).title(exactPosition));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(selectedPosition));

        float zoomLevel = 5.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedPosition, zoomLevel));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Toast.makeText(
                        GoogleMapActivity.this,
                        "Lat : " + latLng.latitude + " , "
                                + "Long : " + latLng.longitude,
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.putExtra("lat", latLng.latitude);
                intent.putExtra("lon", latLng.longitude);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
