package com.bytecodeassemblers.androidweatherstation;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;




public class MainActivity extends AppCompatActivity {

    private MainActivityController MainActivityController;
    //private GetClientLocation getClientLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getClientLocation = new GetClientLocation(this);
        MainActivityController = new MainActivityController(this);

    }


}
