package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/1.
 */

public class County extends  DataSupport{
    private  int id;
    private  String countyName;
    private  String cityId;
    private  int weatherId;

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
    public String getCityId(){
        return cityId;
    }
    public void setCityId(String cityId){
        this.cityId = cityId;
    }
    public int getWeatherId(){
        return weatherId;
    }
    public void setWeatherId(int weatherId){
        this.weatherId = weatherId;
    }
}
