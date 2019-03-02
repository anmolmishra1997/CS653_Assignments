package com.example.anmol.a150010041_project2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SensorService extends Service implements SensorEventListener, LocationListener{

    SensorManager sensorManager = null;
    File file;
    PrintWriter writer;

    boolean accelerometer;
    boolean gps;

    LocationManager lm;

    double last_accelx = 0.0;
    double last_accely = 0.0;
    double last_accelz = 0.0;

    double last_lat = 0.0;
    double last_long = 0.0;

    String label;

    public SensorService() {
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public File getPublicStorageFile(String fileName) {

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), fileName);

        if (!file.mkdirs()) {
            Log.d("MAKE DIR", file.mkdirs() + "");
        }

        return file;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int id)
    {
        accelerometer = Boolean.parseBoolean(intent.getStringExtra(getString(R.string.accelerometer)));
        gps = Boolean.parseBoolean(intent.getStringExtra(getString(R.string.gps)));


        String filename = intent.getStringExtra(getString(R.string.filename));
        label = intent.getStringExtra(getString(R.string.label));

        String first_name = intent.getStringExtra(getString(R.string.first_name));
        String last_name = intent.getStringExtra(getString(R.string.last_name));
        String email = intent.getStringExtra(getString(R.string.email));
        String mobile = intent.getStringExtra(getString(R.string.mobile));
        String age = intent.getStringExtra(getString(R.string.date_of_birth));
        String gender = intent.getStringExtra(getString(R.string.gender));

        String output = first_name + "," + last_name + "," + mobile + "," + email + "," + gender + "," + age;

        if(isExternalStorageWritable()) {

            File path = getPublicStorageFile(getString(R.string.app_name));
            file = new File(path, filename);


            try {
                writer = new PrintWriter(file);
                writer.println(output);
            }
            catch (Exception e) {
                e.printStackTrace();
            }



            if(accelerometer) {
                sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                sensorManager.registerListener(this,
                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                        sensorManager.SENSOR_DELAY_NORMAL);
            }

            if(gps) {
                try {
                    // try to get your GPS location using the LOCATION.SERVIVE provider
                    lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    // This listener will catch and disseminate location updates
                    int minTime = sensorManager.SENSOR_DELAY_NORMAL;
                    float minDistance = 0; // frequency update: 50 meter


                    lm.requestLocationUpdates( //request GPS updates
                            LocationManager.NETWORK_PROVIDER,
                            minTime, minDistance, this);
                } catch (SecurityException e) {
                }
            }
        }
        return START_STICKY;

    }

    public void onSensorChanged(SensorEvent event)
    {
        synchronized (this)
        {
            switch (event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER:
                    String timestamp = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date());
                    double accelx = event.values[0];
                    double accely = event.values[1];
                    double accelz = event.values[2];
                    String output = timestamp + "," + last_lat + "," + last_long + "," + accelx + "," + accely + "," + accelz + "," + label;
                    synchronized (writer) {
                        writer.println(output);
                    }
                    last_accelx = accelx;
                    last_accely = accely;
                    last_accelz = accelz;
                    break;
            }
        }
    }

    public void onLocationChanged(Location location) {

        synchronized (this) {
            String timestamp = new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date());
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String output = timestamp + "," + latitude + "," + longitude + "," + last_accelx + "," + last_accely + "," + last_accelz + "," + label;
            synchronized (writer) {
                writer.println(output);
            }
            last_lat = latitude;
            last_long = longitude;
        }
    }

    @Override
    public void onDestroy() {
        if(accelerometer) {
            sensorManager.unregisterListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        }
        if(gps) {
            lm.removeUpdates(this);
        }
        writer.close();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status,
                                Bundle extras) {}

    @Override
    public void onProviderDisabled(String provider) {}

}