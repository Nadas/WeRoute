package edu.cpp.nada.weroute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.maps.model.LatLng;
import com.johnhiott.darkskyandroidlib.ForecastApi;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WeatherRouteDetailsActivity extends Activity implements RoutingListener {

    private static final String TAG = "WeatherRouteActivity";
    private TrackGPS gps;
    private double currentLat;
    private double currentLng;
    private double distLat;
    private double distLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Bundle extras = getIntent().getExtras();
        String LatLangString = extras.getString("LatLang");
        String[] latlong = LatLangString.split(",");

        distLat = Double.parseDouble(latlong[0]);
        distLng = Double.parseDouble(latlong[1]);

        currentLat = 0;
        currentLng = 0;

        gps = new TrackGPS(WeatherRouteDetailsActivity.this);

        if(gps.canGetLocation()){
            currentLat = gps.getLongitude();
            currentLng = gps .getLatitude();
        }

        LatLng start = new LatLng(currentLat,currentLng);
        LatLng end = new LatLng(distLat, distLng);

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(extras.getString("Name"));

        TextView currentTemp = (TextView) findViewById(R.id.currentTemp);
        WeatherIconView weatherIconView = (WeatherIconView) findViewById(R.id.weatherIcon);
        TextView currentSummary = (TextView) findViewById(R.id.currentSummary);
        TextView nowSummary = (TextView) findViewById(R.id.nowSummary);
        TextView tempMax = (TextView) findViewById(R.id.tempMax);
        TextView tempMin = (TextView) findViewById(R.id.tempMin);

        TextView cloud = (TextView) findViewById(R.id.cloud);
        TextView humidity = (TextView) findViewById(R.id.humid);
        TextView rain = (TextView) findViewById(R.id.rain);

        getCurrentWeather(String.valueOf(distLat),String.valueOf(distLng), "SI", currentTemp, weatherIconView, currentSummary,
                                                    nowSummary, tempMax, tempMin, cloud, humidity, rain) ;
    }

    public void getCurrentWeather(String lat,  String lon, String units,
                                  final TextView currentTempView,
                                  final WeatherIconView weatherIconView,
                                  final TextView currentSummary,
                                  final TextView nowSummary,
                                  final TextView tempMax,
                                  final TextView tempMin,
                                  final TextView cloud,
                                  final TextView humidity,
                                  final TextView rain) {

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
                String displayedCurrentWeather = currentTemp + "° " ; //+ weatherResponse.getCurrently().getSummary()

                currentTempView.setText(displayedCurrentWeather);

                //String icon_string = weatherResponse.getCurrently().getIcon().replaceAll("-", "_");
                //weatherIconView.setIconResource("wi-forecast-io-rain");

                currentSummary.setText(weatherResponse.getCurrently().getSummary());
                nowSummary.setText(weatherResponse.getMinutely().getSummary());

                String maxTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMax()));
                String minTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMin()));

                tempMax.setText(maxTemp+"°");
                tempMin.setText(minTemp+"°");

                //String rain = String.valueOf((int)(float)Math.round(weatherResponse.getCurrently().getPrecipProbability()));

                rain.setText(weatherResponse.getCurrently().getPrecipProbability());
                humidity.setText(weatherResponse.getCurrently().getHumidity());
                cloud.setText(weatherResponse.getCurrently().getCloudClover());
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(TAG, "Error while calling: " + retrofitError.getUrl());
                Log.d(TAG, retrofitError.toString());
            }
        });
    }

    @Override
    public void onRoutingFailure(RouteException e) { }

    @Override
    public void onRoutingStart() { }

    @Override
    public void onRoutingCancelled() { }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        ((TextView) findViewById(R.id.dirInfo))
                .setText(arrayList.get(i).getDurationText());
    }

    public void goToGoogle(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+currentLat+","+currentLng+"&daddr="+distLat+","+distLng));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }
}