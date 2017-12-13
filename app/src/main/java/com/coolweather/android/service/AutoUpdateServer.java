package com.coolweather.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.coolweather.android.gson.Weather;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateServer extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //public int OnStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingPic();
        //定时任务
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int updateInterval = 8*24*60*60*1000;
        long triggerAlarmTime = SystemClock.elapsedRealtime()+updateInterval;
        Intent i = new Intent(this,AutoUpdateServer.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,i,0);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(alarmManager.ELAPSED_REALTIME_WAKEUP,triggerAlarmTime,pendingIntent);
        return super.onStartCommand(intent,flags,startId);
    }

    public void updateWeather(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        if (weatherString!=null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            String weatherId = weather.basic.weatherId;
            String weatherUrl = "http://guolin.tech/api/weather?cityid="+weatherId+"&key=72c4d6872dd14a80bf92612286ee5236";
            //显示天气
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String resJson = response.body().string();
                    final Weather weather = Utility.handleWeatherResponse(resJson);
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateServer.this).edit();
                    editor.putString("weather",resJson);
                    editor.apply();
                }
            });
        }
    }

    public void updateBingPic(){
        String requestUrl = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("AutoUpdateServer","loadBingPic Error!");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resJson = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateServer.this).edit();
                editor.putString("bing_pic",resJson);
                editor.apply();
            }
        });
    }
}
