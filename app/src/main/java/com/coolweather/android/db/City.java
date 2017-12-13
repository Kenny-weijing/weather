package com.coolweather.android.db;

/**
 * Created by Administrator on 2017/12/1.
 */

public class City {
    private  String cityName;
    private  int cityCode;
    private  int proviceCode;

    public String getCityName(){
        return cityName;
    }
    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    public int getCityCode(){
        return cityCode;
    }
    public void setCityCode(int cityCode){
        this.cityCode = cityCode;
    }

    public int getproviceCode(){
        return proviceCode;
    }
    public void setproviceCode(int proviceId){
        this.proviceCode = proviceCode;
    }
}
