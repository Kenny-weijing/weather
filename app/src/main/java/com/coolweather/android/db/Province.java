package com.coolweather.android.db;

/**
 * Created by Administrator on 2017/12/1.
 */

public class Province{
    private String proviceName;
    private int proviceCode;

    public String getproviceName(){
        return proviceName;
    }
    public void setProviceName(String proviceName){
        this.proviceName = proviceName;
    }
    public int getProviceCode(){
        return proviceCode;
    }
    public void setProviceCode(int proviceCode){
        this.proviceCode = proviceCode;
    }
}
