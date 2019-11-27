package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidplot.Plot;
import com.androidplot.PlotListener;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeatherHistoryActivity extends AppCompatActivity {

    private MainActivity mainActivity;
    private ArrayList<Date> dates = new ArrayList<>();
    private ArrayList<Double> temperatures = new ArrayList<>();
    private XYPlot plot;
    private DatabaseApiSelect locationHistory = new DatabaseApiSelect();
    public WeatherHistoryActivity(){}
    public WeatherHistoryActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        this.locationHistory.setContext(this.mainActivity);
        this.locationHistory.setDatabaseSelectEndpoint("http://weatherassemble.hopto.org:8080/");
    }

    private void initializeData(JSONArray array) {
        for(int i=0; i<array.length(); i++)
        {
            try {
                dates.add(new SimpleDateFormat("yyyy-MM-dd").parse(array.getJSONObject(i).getString("Date")));
                temperatures.add(Double.valueOf(array.getJSONObject(i).getString("Temperature")));
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_history);

        plot = findViewById(R.id.plot);
        plot.setRenderMode(Plot.RenderMode.USE_MAIN_THREAD);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest databaseRequest = new JsonArrayRequest(Request.Method.GET, "http://weatherassemble.hopto.org:8080/getweatherhistory.php?region=Serres,gr",null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("SUCCESS");
                    initializeData(response);
                    drawPlot();
                    plot.redraw();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERROR On Select");
                    System.out.println(error.getMessage());
                    try {
                        initializeData(new JSONArray("[{\"Temperature\":\"13.4\",\"Date\":\"2019-11-19\"},{\"Temperature\":\"13.2\",\"Date\":\"2019-11-20\"},{\"Temperature\":\"12.8\",\"Date\":\"2019-11-21\"},{\"Temperature\":\"13.3\",\"Date\":\"2019-11-22\"},{\"Temperature\":\"17.8\",\"Date\":\"2019-11-23\"},{\"Temperature\":\"17.4\",\"Date\":\"2019-11-24\"},{\"Temperature\":\"17.6\",\"Date\":\"2019-11-25\"}]"));
                        drawPlot();
                        plot.redraw();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        queue.add(databaseRequest);
    }

    private void drawPlot() {
        final Date[] years = dates.toArray(new Date[dates.size()]);
        Number[] series1Numbers = temperatures.toArray(new Double[temperatures.size()]);

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Serres");

        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, null, null);

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int yearIndex = (int) Math.round(((Number) obj).doubleValue());
                return dateFormat.format(years[yearIndex], toAppendTo, pos);
            }
            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });
        plot.redraw();
    }
}
