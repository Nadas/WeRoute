package edu.cpp.nada.weroute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class WeatherRouteDetailsActivity extends Activity implements RoutingListener {
    @BindView(R.id.wind)
    TextView wind;
    @BindView(R.id.pressure)
    TextView pressure;
    @BindView(R.id.visibility)
    TextView visibility;
    @BindView(R.id.rain)
    TextView rain;
    @BindView(R.id.humid)
    TextView humidity;
    @BindView(R.id.cloud)
    TextView cloud;
    @BindView(R.id.tempMax)
    TextView tempMax;
    @BindView(R.id.tempMin)
    TextView tempMin;
    @BindView(R.id.nowSummary)
    TextView nowSummary;
    @BindView(R.id.currentSummary)
    TextView currentSummary;
    @BindView(R.id.currentTemp)
    TextView currentTempView;
    @BindView(R.id.weatherIcon)
    WeatherIconView weatherIconView;
    @BindView(R.id.title)
    TextView title;


    private static final String TAG = "WeatherRouteActivity";
    private double currentLat;
    private double currentLng;
    private double distLat;
    private double distLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        String LatLangString = extras.getString("LatLang");
        String[] latLang = LatLangString.split(",");

        title.setText(extras.getString("Name"));
        distLat = Double.parseDouble(latLang[0]);
        distLng = Double.parseDouble(latLang[1]);

        Location location = GPS.sharedInstance(this).getLastKnownLocation();
        currentLat = location.getLatitude();
        currentLng = location.getLongitude();

        LatLng start = new LatLng(currentLat,currentLng);
        LatLng end = new LatLng(distLat, distLng);

        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(start, end)
                .build();
        routing.execute();

        getCurrentWeather(String.valueOf(distLat),String.valueOf(distLng), "SI");
    }

    public void getCurrentWeather(String lat,  String lon, String units) {

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
                String maxTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMax()));
                String minTemp = String.valueOf((int)(float)Math.round(weatherResponse.getDaily().getData().get(0).getTemperatureMin()));
                String rainPer = weatherResponse.getCurrently().getPrecipProbability();
                String humidPer = weatherResponse.getCurrently().getHumidity();
                String cloudPer = weatherResponse.getCurrently().getCloudClover();

                String icon2 = "wi_forecast_io_" + weatherResponse.getCurrently().getIcon().replaceAll("-", "_");
                int icon3 = getResources().getIdentifier(icon2, "string", getPackageName());

                weatherIconView.setIconResource(getString(icon3));

                currentTempView.setText(currentTemp + "° ");
                currentSummary.setText(weatherResponse.getCurrently().getSummary());
                nowSummary.setText(weatherResponse.getMinutely().getSummary());

                tempMax.setText(maxTemp+"°");
                tempMin.setText(minTemp+"°");

                rain.setText(rainPer.substring(rainPer.lastIndexOf(".") + 1)+"%");
                humidity.setText(humidPer.substring(humidPer.lastIndexOf(".") + 1)+"%");
                cloud.setText(cloudPer.substring(cloudPer.lastIndexOf(".") + 1)+"%");

                wind.setText( weatherResponse.getCurrently().getWindSpeed()+"MPH");
                pressure.setText( weatherResponse.getCurrently().getPressure()+"ni");
                visibility.setText(weatherResponse.getCurrently().getVisibility()+"mi");
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
}