package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Log.i("MainActivity", "start app!");
            setContentView(R.layout.activity_main);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            if (prefs.getString("weather", null) != null) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (Exception ex){
            Log.e("MainActivity ", "onCreate error:"+ ex.getMessage());
        }
    }
}
