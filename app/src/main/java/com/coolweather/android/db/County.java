package com.coolweather.android.db;

/**
 * Created by Administrator on 2017/12/1.
 */

public class County{
    private  int id;
    private  String countyName;
    private  int cityCode;
    private  String weatherId;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCountyName(){
        return countyName;
    }
    public void setCountyName(String countyName){
        this.countyName = countyName;
    }
    public int getcityCode(){
        return cityCode;
    }
    public void setcityCode(int cityCode){
        this.cityCode = cityCode;
    }
    public String getWeatherId(){
        return weatherId;
    }
    public void setWeatherId(String weatherId){
        this.weatherId = weatherId;
    }
}
