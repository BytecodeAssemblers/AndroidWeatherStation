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
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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

        Geocoder gcd = new Geocoder(this, Locale.getDefault());

        Common commonObject = new Common();
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(Double.parseDouble(commonObject.getLatitude()), Double.parseDouble(commonObject.getLongitude()), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add a marker in selected location and move the camera
        LatLng selectedPosition = new LatLng(Double.parseDouble(commonObject.getLatitude()), Double.parseDouble(commonObject.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(selectedPosition).title("Marker in "+addresses.get(0).getAddressLine(0)));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(selectedPosition));
    }
}
