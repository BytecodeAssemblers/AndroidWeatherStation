package com.bytecodeassemblers.androidweatherstation.client_location;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bytecodeassemblers.androidweatherstation.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GetClientLocation {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private FusedLocationProviderClient client;
    private TextView myLocationResult;
    double latitude;
    double longitude;

    public GetClientLocation(Activity activity) {
        client = LocationServices.getFusedLocationProviderClient(activity);
        myLocationResult = activity.findViewById(R.id.locationResult);
        getLocation(activity);
    }

    private void getLocation(final Activity activity) {
        // Here, thisActivity is the current activity
        if(ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Permission is not granted
            // Should we show an explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_COARSE_LOCATION)){
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(activity.getApplicationContext())
                        .setTitle("Required Loaction Permission")
                        .setMessage("You have to give this premission to acess the feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION );
                            }
                        })
                        .setNegativeButton("Cansel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

            }else {
                //   No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                //result of the request.
            }

        }else {
            // Permission has already been granted
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Got last know location. In some rare situations this can be null.
                    if(location!=null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Toast.makeText(activity, "asdasdas!!", Toast.LENGTH_SHORT).show();
                        myLocationResult.setText("latitude: "+latitude+" longitude: "+longitude);
                    }
                }
            });
        }
    }


    

}
