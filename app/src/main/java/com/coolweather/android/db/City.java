package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/12/1.
 */

public class City extends  DataSupport{
    private  int id;
    private  String cityName;
    private  String cityCode;
    private  int proviceId;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getProviceName(){
        return cityName;
    }
    public void setProviceName(String proviceName){
        this.cityName = proviceName;
    }
    public String getCityCode(){
        return cityCode;
    }
    public void setCityCode(String cityCode){
        this.cityCode = cityCode;
    }
    public int getProviceId(){
        return proviceId;
    }
    public void setProviceId(int proviceId){
        this.proviceId = proviceId;
    }
}