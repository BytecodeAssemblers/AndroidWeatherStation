package com.bytecodeassemblers.androidweatherstation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WeatherHistoryActivity extends AppCompatActivity {

    private MainActivity mainActivity;
    private XYPlot plot;
    private DatabaseApiSelect locationHistory = new DatabaseApiSelect();
    public WeatherHistoryActivity(){}
    public WeatherHistoryActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        this.locationHistory.setContext(this.mainActivity);
        this.locationHistory.setDatabaseSelectEndpoint("http://weatherassemble.hopto.org:8080/");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_history);

        plot = findViewById(R.id.plot);

        // create a couple arrays of y-values to plot:
        final Date[] years = {
                new GregorianCalendar(2001, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2001, Calendar.JULY, 1).getTime(),
                new GregorianCalendar(2002, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2002, Calendar.JULY, 1).getTime(),
                new GregorianCalendar(2003, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2003, Calendar.JULY, 1).getTime(),
                new GregorianCalendar(2004, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2004, Calendar.JULY, 1).getTime(),
                new GregorianCalendar(2005, Calendar.JANUARY, 1).getTime(),
                new GregorianCalendar(2005, Calendar.JULY, 1).getTime()
        };
        Number[] series1Numbers = {1, 4, 2, 8, 4, 16, 8, 32, 16, 64};

        // turn the above arrays into XYSeries':
        // (Y_VALS_ONLY means use the element index as the x value)
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sales of 2010");

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
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
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
    }
}
