package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<City> {
    private final ArrayList<City> cities;
    private final LayoutInflater inflater;

    public CustomList(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
        this.cities = cities;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = (convertView != null)
                ? convertView
                : inflater.inflate(R.layout.content, parent, false);

        City city = cities.get(position);
        ((TextView) row.findViewById(R.id.city_name)).setText(city.getName());
        ((TextView) row.findViewById(R.id.city_province)).setText(city.getProvince());
        return row;
    }
}
