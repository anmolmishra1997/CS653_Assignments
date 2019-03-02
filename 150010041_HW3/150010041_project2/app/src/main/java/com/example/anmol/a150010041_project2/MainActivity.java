package com.example.anmol.a150010041_project2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sensors:
                return true;

            case R.id.action_record:
                stopService(new Intent(this, SensorService.class ));
//                Intent intent1 = new Intent(this, DisplayMessageActivity.class);
//                startActivity(intent1);
                return true;

            case R.id.action_logout:
                Intent intent = new Intent(this, SensorService.class);
                intent.putExtra("accelerometer", "true");
                intent.putExtra("gps", "false");
                intent.putExtra("fileName", "FirstTrial.csv");
                startService(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
