package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddEditListener {
        void onCityEdited();
        void onCityCreated(City newCity);
    }

    private AddEditListener listener;

    public void setAddEditListener(AddEditListener l) { this.listener = l; }

    public static AddCityFragment newInstance(@Nullable City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        AddCityFragment f = new AddCityFragment();
        f.setArguments(args);
        return f;
    }

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireActivity())
                .inflate(R.layout.fragment_add_edit_city, null);

        EditText etName = view.findViewById(R.id.et_city_name);
        EditText etProv = view.findViewById(R.id.et_city_province);

        City editing = null;
        if (getArguments() != null) {
            editing = (City) getArguments().getSerializable("city");
            if (editing != null) {
                etName.setText(editing.getName());
                etProv.setText(editing.getProvince());
            }
        }
        City finalEditing = editing;

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Add/Edit City")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (d, w) -> {
                    String name = etName.getText().toString().trim();
                    String prov = etProv.getText().toString().trim();
                    if (finalEditing != null) {
                        finalEditing.setName(name);
                        finalEditing.setProvince(prov);
                        if (listener != null) listener.onCityEdited();
                    } else {
                        City created = new City(name, prov);
                        if (listener != null) listener.onCityCreated(created);
                    }
                })
                .create();
    }
}
