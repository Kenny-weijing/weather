package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Utility {
    public static boolean handleProviceResponse(String response){
        try {
            if (TextUtils.isEmpty(response))
                return false;
            JSONArray proviceArray = new JSONArray(response);
            for (int i=0;i<proviceArray.length();i++)
            {
                JSONObject proviceObj = proviceArray.getJSONObject(i);
                Province provice = new Province();
                provice.setProviceName(proviceObj.getString("name"));
                provice.setProviceCode(proviceObj.getInt("id"));
                provice.save();
            }
        }
        catch (Exception ex){
            Log.e("Utility","handleProviceResponse Error:"+ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
    public static boolean handleCityResponse(String response,int proviceId){
        try {
            if (TextUtils.isEmpty(response))
                return false;
            JSONArray cityArray = new JSONArray(response);
            for (int i=0;i<cityArray.length();i++)
            {
                JSONObject cityObj = cityArray.getJSONObject(i);
                City city = new City();
                city.setCityName(cityObj.getString("name"));
                city.setCityCode(cityObj.getInt("id"));
                city.setProviceId(proviceId);
                city.save();
            }
        }
        catch (Exception ex){
            Log.e("Utility","handleCityResponse Error:"+ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }
    public static boolean handleCountyResponse(String response,int cityId){
        try {
            if (TextUtils.isEmpty(response))
                return false;
            JSONArray countyArray = new JSONArray(response);
            for (int i=0;i<countyArray.length();i++)
            {
                JSONObject countyObj = countyArray.getJSONObject(i);
                County county = new County();
                county.setCountyName(countyObj.getString("name"));
                county.setWeatherId(countyObj.getString("weather_id"));
                county.setCityId(cityId);
                county.save();
            }
        }
        catch (Exception ex){
            Log.e("Utility","handleCountyResponse Error:"+ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public static Weather handleWeatherReaponse(String response)
    {
        try {
            JSONObject mainObj = new JSONObject(response);
            JSONArray weatherArray = mainObj.getJSONArray("HeWeather");
            String weather  = weatherArray.getJSONObject(0).toString();
            return new Gson().fromJson(weather,Weather.class);
        }
        catch (Exception ex){
            Log.e("Utility","handleWeatherReaponse Error:"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
