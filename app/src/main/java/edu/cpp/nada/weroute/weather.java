package edu.cpp.nada.weroute;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.model.LatLng;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class weather extends Activity implements RoutingListener {

    private static final String TAG = "weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        LatLng start = new LatLng(33.688953,-117.8303227);
        LatLng end = new LatLng(34.0565284,-117.8215295);

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();

        TextView weatherInfo = (TextView) findViewById(R.id.weatherInfo);

        getCurrentWeather("34.0565284","-117.8215295", "SI", weatherInfo) ;
    }

    public void getCurrentWeather(String lat,  String lon, String units, final TextView weatherInfo) {
        ForecastApi.create("e89a8b0f4b20f766a8a8a1a26b4f2fd9");

        RequestBuilder weather = new RequestBuilder();
        Request request = new Request();
        request.setLat(lat);
        request.setLng(lon);
        if (units.equals("SI")) {
            request.setUnits(Request.Units.SI);
        } else {
            request.setUnits(Request.Units.US);
        }
        request.setLanguage(Request.Language.ENGLISH);

        weather.getWeather(request, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {
                String currentTemp = String.valueOf((int)(float)Math.round(weatherResponse.getCurrently().getTemperature()));
                String displayedCurrentWeather = currentTemp + "° " + weatherResponse.getCurrently().getSummary();
                weatherInfo.setText(displayedCurrentWeather);

            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.d(TAG, retrofitError.toString());
            }
        });
    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        ((TextView) findViewById(R.id.dirInfo))
                .setText(arrayList.get(i).getDurationText());
    }

    @Override
    public void onRoutingCancelled() {

    }
}