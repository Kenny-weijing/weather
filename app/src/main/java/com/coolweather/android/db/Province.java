package com.coolweather.android.db;

import org.litepal.crud.DataSupport;
/**
 * Created by Administrator on 2017/12/1.
 */

public class Province  extends  DataSupport{
    private  int id;
    private  String proviceName;
    private  int proviceCode;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
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
