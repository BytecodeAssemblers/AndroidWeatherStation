package com.bytecodeassemblers.androidweatherstation.weather_bit;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bytecodeassemblers.androidweatherstation.MainActivity;
import com.bytecodeassemblers.androidweatherstation.R;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherBitController {
    private TextView WeatherBitTemp;
    private TextView WeatherBitCity;
    private TextView WeatherBitDescription;
    private TextView WeatherBitWindSpeed;

   private String CITY="Serres,gr";
    private String url = Common.apiRequestLink(CITY);//"https://api.weatherbit.io/v2.0/current?city=Serres,Gr&key=85166dfd6eae40128861ff9efb80ec65";
    private RequestQueue requestQueue;

    public WeatherBitController(MainActivity activity){
        initializeComponents(activity);
        requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        volleyService(activity.getApplicationContext());
    }


    public void initializeComponents(MainActivity activity){
        WeatherBitTemp = activity.findViewById(R.id.weatherbit_temp);
        WeatherBitCity = activity.findViewById(R.id.weatherbit_city);
        WeatherBitDescription = activity.findViewById(R.id.weatherbit_description);
        WeatherBitWindSpeed = activity.findViewById(R.id.weatherbit_windspeed);
    }


    public void volleyService(Context contextParam) {
        StringRequest getRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject myObject = new JSONObject(response);
                    JSONObject DataObject = myObject.getJSONArray("data").getJSONObject(0);
                    JSONObject weather = DataObject.getJSONObject("weather");
                    String temp = DataObject.getString("temp") + "°C";
                    String cityName = DataObject.getString("city_name");
                    String description = weather.getString("description");
                    String windspeed = DataObject.getString("wind_spd") + "m/h";
                    WeatherBitTemp.setText("Temperature: \n"+temp);
                    WeatherBitCity.setText("City: \n"+cityName);
                    WeatherBitDescription.setText("Weather: \n"+description);
                    WeatherBitWindSpeed.setText("Wind speed: \n"+windspeed);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(getRequest);
    }
}
