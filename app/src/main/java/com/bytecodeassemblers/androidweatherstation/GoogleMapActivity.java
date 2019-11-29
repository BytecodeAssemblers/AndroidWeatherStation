package com.bytecodeassemblers.androidweatherstation;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

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
    ArrayList<Marker> markersArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markersArray = new ArrayList<Marker>();
        commonObject = new Common();
        lat = Double.parseDouble(commonObject.getLatitude());
        lon = Double.parseDouble(commonObject.getLongitude());
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
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Add a marker in current location
        String exactPosition =  addresses.get(0).getAddressLine(0);

        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in "+exactPosition));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
