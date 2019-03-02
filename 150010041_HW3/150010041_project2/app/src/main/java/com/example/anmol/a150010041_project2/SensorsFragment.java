package com.example.anmol.a150010041_project2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorsFragment extends Fragment{


    public SensorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sensors, container, false);

        Switch sensors_accelerometer = (Switch) v.findViewById(R.id.sensors_accelerometer);
        Switch sensors_gps = (Switch) v.findViewById(R.id.sensors_gps);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        boolean accelerometer = Boolean.parseBoolean(
                sharedPref.getString(getString(R.string.accelerometer), "true")
        );
        boolean gps = Boolean.parseBoolean(
                sharedPref.getString(getString(R.string.gps), "true")
        );

        sensors_accelerometer.setChecked(accelerometer);
        sensors_gps.setChecked(gps);

        sensors_accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Switch sensors_accelerometer = (Switch) view.findViewById(R.id.sensors_accelerometer);
                boolean checked = sensors_accelerometer.isChecked();

                editor.putString(getString(R.string.accelerometer), Boolean.toString(checked));
                editor.apply();
            }
        });

        sensors_gps.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Switch sensors_gps = (Switch) view.findViewById(R.id.sensors_gps);
                boolean checked = sensors_gps.isChecked();

                editor.putString(getString(R.string.gps), Boolean.toString(checked));
                editor.apply();
            }
        });

        return v;
    }

}
