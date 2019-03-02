package com.example.anmol.a150010041_project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisplayMessageActivity extends AppCompatActivity {

    static ArrayList<String> initial_timestamps = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, initial_timestamps);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sensors:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_record:
                return true;

            case R.id.action_logout:
                Intent intent_logout = new Intent(this, Login.class);
                startActivity(intent_logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void onCheck(View view) {
        Switch record_switch = findViewById(R.id.main_record);
        if (record_switch.isChecked()) {
            // Don't do anything
        } else {
            String current_time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            initial_timestamps.add(0, current_time);
            if (initial_timestamps.size() == 6)
                initial_timestamps.remove(5);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, initial_timestamps);

            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }
    }
}
