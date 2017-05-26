package edu.cpp.nada.weroute;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PlacesActivity extends AppCompatActivity {

    private List<PlacesClass> placeList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        Paper.init(this);

        queryPlaces();
    }

    private void queryPlaces() {
        placeList = Paper.book().read("places");

        if (placeList == null) {
            placeList = new ArrayList<>();
        }

        listView = (ListView) findViewById(R.id.listView1);
        PlaceArrayAdapter placeArrayAdapter = new PlaceArrayAdapter(
                this, R.layout.listview_place_item, placeList);
        listView.setAdapter(placeArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlacesActivity.this, WeatherRouteDetailsActivity.class);

                String LatLang = placeList.get(position).getLatLang().toString();
                String placeName = placeList.get(position).getName();
                String distention = LatLang.substring(LatLang.indexOf("(") + 1, LatLang.indexOf(")"));

                intent.putExtra("Name", placeName);
                intent.putExtra("LatLang", distention);

                startActivity(intent);
            }
        });
    }

    private void savePlace(PlacesClass place) {

        List<PlacesClass> placeList = Paper.book().read("places");
        if (placeList == null) {
            placeList = new ArrayList<>();
        }
        placeList.add(place);
        Paper.book().write("places", placeList);

        queryPlaces();
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place foundPlace = PlaceAutocomplete.getPlace(this, data);

                Log.e("Tag", "PlacesClass: " + foundPlace.getAddress() + foundPlace.getPhoneNumber());

                PlacesClass newPlace = new PlacesClass(foundPlace.getLatLng(), foundPlace.getName().toString(), false, foundPlace.getAddress().toString());
                savePlace(newPlace);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

}
