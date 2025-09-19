package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<City> cities;
    CustomList cityAdapter;
    ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);

        // seed data (like the screenshots)
        cities = new ArrayList<>();
        cities.add(new City("Edmonton", "AB"));
        cities.add(new City("Vancouver", "BC"));
        cities.add(new City("Toronto", "ON"));
        cities.add(new City("Hamilton", "ON"));
        cities.add(new City("Denver", "CO"));
        cities.add(new City("Los Angeles", "CA"));
        sortCities();

        cityAdapter = new CustomList(this, cities);
        cityList.setAdapter(cityAdapter);

        // tap row -> edit dialog
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = cities.get(position);
            AddCityFragment dialog = AddCityFragment.newInstance(selected);
            dialog.setAddEditListener(new AddCityFragment.AddEditListener() {
                @Override public void onCityEdited() {
                    sortCities();
                    cityAdapter.notifyDataSetChanged();
                }
                @Override public void onCityCreated(City newCity) { /* not used here */ }
            });
            dialog.show(getSupportFragmentManager(), "EditCity");
        });

        // FAB -> add dialog (empty)
        fabAdd.setOnClickListener(v -> {
            AddCityFragment dialog = AddCityFragment.newInstance(null);
            dialog.setAddEditListener(new AddCityFragment.AddEditListener() {
                @Override public void onCityEdited() { /* not used for add */ }
                @Override public void onCityCreated(City newCity) {
                    cities.add(newCity);
                    sortCities();
                    cityAdapter.notifyDataSetChanged();
                }
            });
            dialog.show(getSupportFragmentManager(), "AddCity");
        });
    }

    private void sortCities() {
        Collections.sort(cities, new Comparator<City>() {
            @Override public int compare(City a, City b) {
                int byName = a.getName().compareToIgnoreCase(b.getName());
                return (byName != 0) ? byName : a.getProvince().compareToIgnoreCase(b.getProvince());
            }
        });
    }
}
