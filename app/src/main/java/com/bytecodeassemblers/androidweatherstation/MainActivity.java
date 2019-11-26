package com.bytecodeassemblers.androidweatherstation;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


//import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;




public class MainActivity extends Activity {

    private MainActivity mainView = this;
    private MainActivityController MainActivityController;
    //private GetClientLocation getClientLocation;
    private WeatherHistoryActivity weatherHistoryActivity;
    Button weatherHistoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getClientLocation = new GetClientLocation(this);
        MainActivityController = new MainActivityController(this);

        weatherHistoryButton = findViewById(R.id.buttonHistoryActivity);
        weatherHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherHistoryActivity = new WeatherHistoryActivity(mainView);
                Intent intent = new Intent(mainView, WeatherHistoryActivity.class);
                startActivity(intent);
            }
        });

    }


}
