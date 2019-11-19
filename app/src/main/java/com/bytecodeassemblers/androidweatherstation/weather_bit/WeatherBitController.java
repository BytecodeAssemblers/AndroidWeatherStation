package com.bytecodeassemblers.androidweatherstation.weather_bit;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherBitController {
    private TextView WeatherBitTemp;
    private TextView WeatherBitCity;
    private TextView WeatherBitDescription;
    private TextView WeatherBitWindSpeed;
    private TextView MainTemp;
    private String CITY="Serres,gr"; //-> we need to implement the location of the current user // sometime
    private String url = Common.apiRequestLink(CITY);//"https://api.weatherbit.io/v2.0/current?city=Serres,Gr&key=85166dfd6eae40128861ff9efb80ec65";
    private RequestQueue requestQueue;
    private Context activityContext;



    public WeatherBitController(MainActivity activity){
        initializeComponents(activity);
        requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        activityContext = activity;
        volleyService(activity.getApplicationContext());
    }


    public void initializeComponents(MainActivity activity){
        MainTemp = activity.findViewById(R.id.temp);
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


                    JSONObject payloadForDatabase = new JSONObject();
                      payloadForDatabase.put("region",CITY);

                    SimpleDateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date currentDate = new Date();

                    payloadForDatabase.put("date",databaseDateFormat.format(currentDate));





                          payloadForDatabase.put("temperature",Double.valueOf(DataObject.getString("temp")));
                            payloadForDatabase.put("description",weather.getString("description"));

                    DatabaseApiInsert historicalDatabaseUpdate = new DatabaseApiInsert();
                      historicalDatabaseUpdate.setDatabaseInsertEndpoint("http://weatherassemble.hopto.org:8080/updateweatherhistory.php");
                        historicalDatabaseUpdate.setContext(activityContext);
                         historicalDatabaseUpdate.setPayload(payloadForDatabase);
                          historicalDatabaseUpdate.executeInsert();


                    String cityName = DataObject.getString("city_name");
                    String description = weather.getString("description");
                    String windspeed = DataObject.getString("wind_spd") + "m/h";
                    WeatherBitTemp.setText("Temperature: \n"+temp);
                    WeatherBitCity.setText("City: \n"+cityName);
                    WeatherBitDescription.setText("Weather: \n"+description);
                    WeatherBitWindSpeed.setText("Wind speed: \n"+windspeed);
                    MainTemp.setText(temp);
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
