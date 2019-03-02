package com.example.anmol.a150010041_project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = findViewById(R.id.include);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
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
                Intent intent_record = new Intent(this, DisplayMessageActivity.class);
                startActivity(intent_record);
                return true;

            case R.id.action_logout:
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void submitLogin(View view) {

        EditText login_first_name = findViewById(R.id.first_name);
        String first_name = login_first_name.getText().toString();
        EditText login_last_name = findViewById(R.id.last_name);
        String last_name = login_last_name.getText().toString();
        EditText login_email = findViewById(R.id.email);
        String email = login_email.getText().toString();
        EditText login_mobile = findViewById(R.id.mobile);
        String mobile = login_mobile.getText().toString();
        EditText login_date_of_birth = findViewById(R.id.date_of_birth);
        String date_of_birth = login_date_of_birth.getText().toString();
        RadioGroup login_gender = findViewById(R.id.gender);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.first_name), first_name);
        editor.putString(getString(R.string.last_name), last_name);
        editor.putString(getString(R.string.email), email);
        editor.putString(getString(R.string.mobile), mobile);
        editor.putString(getString(R.string.date_of_birth), date_of_birth);
//        editor.putString(getString(R.string.gender), gender);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
