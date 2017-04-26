package edu.cpp.nada.weroute;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.List;


public class Places extends Activity {

    static final String[] PLACES = new String[] { "Cal Poly Pomona", "Downtown LA", "Anaheim"};
    List<String> ingredientsList= new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingredientsList);
        ListView listView= (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Places.this, WeatherRouteDetails.class);

                //temp hardcoded latlang
                intent.putExtra("Lat", "33.688953");
                intent.putExtra("Lng", "-117.8215295");

                startActivity(intent);
            }
        });
    }

    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void gotToActivity(View view) {
        Intent intent = new Intent(Places.this, WeatherRouteDetails.class);
        startActivity(intent);
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                ingredientsList.add(place.getName().toString());
                adapter.notifyDataSetChanged();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
