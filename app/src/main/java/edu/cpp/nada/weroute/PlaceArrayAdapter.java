package edu.cpp.nada.weroute;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nada on 5/7/17.
 */

public class PlaceArrayAdapter extends ArrayAdapter<PlacesClass> {

    private Context context;
    private int layoutResource;
    private List<PlacesClass> places;

    public PlaceArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PlacesClass> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.places = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutResource, parent, false);

        /*ImageView imageView = (ImageView) view.findViewById(R.id.profileImageView);
        imageView.setImageResource(places.get(position).get());*/

        TextView textViewName = (TextView) view.findViewById(R.id.placeNameTextView);
        textViewName.setText(places.get(position).getName());

        TextView textViewNum = (TextView) view.findViewById(R.id.distanceTextView);
        textViewNum.setText(places.get(position).getCity());

        return view;
    }
}
