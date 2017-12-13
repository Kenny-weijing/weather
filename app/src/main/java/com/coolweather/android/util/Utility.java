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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Utility {
    public static List<Province> handleProviceResponse(String response){
        List<Province> provinceList = new ArrayList<Province>();
        try {
            if (TextUtils.isEmpty(response))
                return null;
            JSONArray proviceArray = new JSONArray(response);
            for (int i=0;i<proviceArray.length();i++)
            {
                JSONObject proviceObj = proviceArray.getJSONObject(i);
                Province provice = new Province();
                provice.setProviceName(proviceObj.getString("name"));
                provice.setProviceCode(proviceObj.getInt("id"));
                provinceList.add(provice);
            }
            return provinceList;
        }
        catch (Exception ex){
            Log.e("Utility","handleProviceResponse Error:"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    public static List<City> handleCityResponse(String response,int proviceCode){
        List<City> cityList = new ArrayList<City>();
        try {
            if (TextUtils.isEmpty(response))
                return null;
            JSONArray cityArray = new JSONArray(response);
            for (int i=0;i<cityArray.length();i++)
            {
                JSONObject cityObj = cityArray.getJSONObject(i);
                City city = new City();
                city.setCityName(cityObj.getString("name"));
                city.setCityCode(cityObj.getInt("id"));
                city.setproviceCode(proviceCode);
                cityList.add(city);
            }
            return cityList;
        }
        catch (Exception ex){
            Log.e("Utility","handleCityResponse Error:"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    public static List<County> handleCountyResponse(String response,int cityCode){
        List<County> countyList = new ArrayList<County>();
        try {
            if (TextUtils.isEmpty(response))
                return null;
            JSONArray countyArray = new JSONArray(response);
            for (int i=0;i<countyArray.length();i++)
            {
                JSONObject countyObj = countyArray.getJSONObject(i);
                County county = new County();
                county.setId(countyObj.getInt("id"));
                county.setCountyName(countyObj.getString("name"));
                county.setWeatherId(countyObj.getString("weather_id"));
                county.setcityCode(cityCode);
                countyList.add(county);
            }
            return countyList;
        }
        catch (Exception ex){
            Log.e("Utility","handleCountyResponse Error:"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public static Weather handleWeatherResponse(String response)
    {
        try {
            JSONObject mainObj = new JSONObject(response);
            JSONArray jsonArray = mainObj.getJSONArray("HeWeather");
            String weather  = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weather,Weather.class);
        }
        catch (Exception ex){
            Log.e("Utility","handleWeatherResponse Error:"+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponseForHub(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
