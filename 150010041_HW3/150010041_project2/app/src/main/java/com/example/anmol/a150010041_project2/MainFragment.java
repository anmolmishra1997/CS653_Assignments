package com.example.anmol.a150010041_project2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    static ArrayList<String> initial_timestamps = new ArrayList<>(5);


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);

        Switch main_record = (Switch) v.findViewById(R.id.main_record);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        boolean record = Boolean.parseBoolean(
                sharedPref.getString(getString(R.string.record), "false")
        );

        main_record.setChecked(record);

        main_record.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                EditText main_label = getActivity().findViewById(R.id.main_label);
                String label = main_label.getText().toString();


                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                Switch main_record = (Switch) view.findViewById(R.id.main_record);
                boolean checked = main_record.isChecked();



                String accelerometer = sharedPref.getString(getString(R.string.accelerometer), "true");
                String gps = sharedPref.getString(getString(R.string.gps), "true");
                String login = sharedPref.getString(getString(R.string.login), "");
                if (login.matches("")) {
                    Toast.makeText(getActivity(), "You are not logged in.", Toast.LENGTH_SHORT).show();
                    main_record.setChecked(false);

                    return;
                }

                String first_name = sharedPref.getString(getString(R.string.first_name), "");
                String last_name = sharedPref.getString(getString(R.string.last_name), "");
                String email = sharedPref.getString(getString(R.string.email), "");
                String mobile = sharedPref.getString(getString(R.string.mobile), "");
                String age = sharedPref.getString(getString(R.string.date_of_birth), "");
                String gender = sharedPref.getString(getString(R.string.gender), "");

                if(checked) {

                    if (label.matches("")) {
                        Toast.makeText(getActivity(), "You did not enter label", Toast.LENGTH_SHORT).show();
                        main_record.setChecked(false);

                        return;
                    }

                    Intent intent = new Intent(getActivity(), SensorService.class);
                    intent.putExtra(getString(R.string.accelerometer), accelerometer);
                    intent.putExtra(getString(R.string.gps), gps);
                    intent.putExtra(getString(R.string.first_name), first_name);
                    intent.putExtra(getString(R.string.last_name), last_name);
                    intent.putExtra(getString(R.string.email), email);
                    intent.putExtra(getString(R.string.mobile), mobile);
                    intent.putExtra(getString(R.string.date_of_birth), age);
                    intent.putExtra(getString(R.string.gender), gender);
                    intent.putExtra(getString(R.string.label), label);
                    String filename = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss'.csv'").format(new Date());
                    intent.putExtra(getString(R.string.filename), filename);
                    getActivity().startService(intent);
                }
                else {
                    getActivity().stopService(new Intent(getActivity(), SensorService.class));
                    String current_time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    initial_timestamps.add(0, current_time);
                    if (initial_timestamps.size() == 6)
                        initial_timestamps.remove(5);
                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, initial_timestamps);

                    ListView listView = getActivity().findViewById(R.id.listView);
                    listView.setAdapter(adapter);
                }

                editor.putString(getString(R.string.record), Boolean.toString(checked));
                editor.apply();

            }
        });

        return v;
    }

}



