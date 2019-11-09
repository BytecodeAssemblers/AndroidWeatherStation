package com.bytecodeassemblers.androidweatherstation.open_weather;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bytecodeassemblers.androidweatherstation.DatabaseApiInsert;
import com.bytecodeassemblers.androidweatherstation.MainActivity;
import com.bytecodeassemblers.androidweatherstation.R;
import com.bytecodeassemblers.androidweatherstation.client_location.GetClientLocation;
import com.google.android.gms.location.LocationRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenWeatherController {
    private RequestQueue myQueue;
    private TextView tempOnView;
    private TextView maxTempOnView;
    private TextView minTempOnView;
    private TextView humidityOnView;
    private TextView descriptionOnView;
    private TextView windSpeedOnView;
    private TextView cityNameOnView;
    private String lat = "41.090923"; //coordinates and stuff
    private String lon = "23.54132";
    private String url = Common.apiRequestLink(lat,lon);

    private GetClientLocation getClientLocation;

    public OpenWeatherController(MainActivity activity){
        getClientLocation = new GetClientLocation(activity);
        initializeComponents(activity);
        myQueue = Volley.newRequestQueue(activity.getApplicationContext());
        executeGet();
    }


    public void initializeComponents(MainActivity activity){
        cityNameOnView = activity.findViewById(R.id.address);
        tempOnView = activity.findViewById(R.id.openWeatherTemp);
        maxTempOnView = activity.findViewById(R.id.openWeatherMaxTemp);
        minTempOnView = activity.findViewById(R.id.openWeatherMinTemp);
        humidityOnView = activity.findViewById(R.id.openWeatherHumidity);
        windSpeedOnView = activity.findViewById(R.id.openWeatherWindSpeed);
        descriptionOnView = activity.findViewById(R.id.openWeatherDescription);
    }


    public void executeGet(){

        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                      JSONObject mainObject = jsonObj.getJSONObject("main");
                        String temp = mainObject.getString("temp") + "°C";
                        String tempMin = mainObject.getString("temp_min") + "°C";
                        String tempMax = mainObject.getString("temp_max") + "°C";
                        String humidity = mainObject.getString("humidity") + "%";

                    String cityName = jsonObj.getString("name");
                    JSONObject weatherObject = jsonObj.getJSONArray("weather").getJSONObject(0);
                    String description = weatherObject.getString("description");

                    JSONObject windObject = jsonObj.getJSONObject("wind");
                    String windSpeed = windObject.getString("speed");
                    cityNameOnView.setText(cityName);
                    tempOnView.setText("Temp: \n"+temp);
                    maxTempOnView.setText("Max temp: \n"+tempMax);
                    minTempOnView.setText("Min temp: \n"+tempMin);
                    humidityOnView.setText("Humidity: \n"+humidity);
                    windSpeedOnView.setText("Wind speed: \n"+windSpeed);
                    descriptionOnView.setText("Description: \n"+description);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        myQueue.add(getRequest);
    }
}
