package com.coolweather.android;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/12/1.
 */

public class ChooseAreaFragment extends Fragment{
    public static final int LEVEL_PROVIOCE=0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;
    private ProgressDialog progressDialog;

    private TextView textView;
    private Button btnBack;
    private ListView listView;
    private ArrayAdapter<String> adapterArray;
    private List<String> dataList = new ArrayList<>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selProvice;
    private City selCity;
    private int selLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.choose_are, container, false);
            textView = (TextView) view.findViewById(R.id.textTitle);
            btnBack = (Button) view.findViewById(R.id.btnBack);
            listView = (ListView) view.findViewById(R.id.listViewWeather);
            adapterArray = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
            listView.setAdapter(adapterArray);
            return view;
        }
        catch (Exception ex){
            Log.e("ChooseAreaFragment","OnCreateView"+ex.getMessage());
        }
        return null;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void  onItemClick(AdapterView<?> parent, View view,int position,long id){
                if (selLevel== LEVEL_PROVIOCE){
                    selProvice = provinceList.get(position);
                    queryCities();
                }else if (selLevel== LEVEL_CITY){
                    selCity = cityList.get(position);
                    queryCounties();
                }else if (selLevel==LEVEL_COUNTY){
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity){
                        Intent intent = new Intent(getActivity(),WeatherActivity.class);
                        intent.putExtra("weatherid",weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if(getActivity() instanceof WeatherActivity) {
                        WeatherActivity activity = (WeatherActivity)getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeLayout.setRefreshing(true);
                        activity.requestWeatherInfo(weatherId);
                    }

                }
            }
        });
        btnBack.setOnClickListener(backClickEvent);
    }
    private OnClickListener backClickEvent = new OnClickListener(){
        public void onClick(View v){
            if (selLevel== LEVEL_COUNTY){
                queryCities();
            }else if (selLevel== LEVEL_CITY){
                queryProvinces();
            }
        }
    };

    private void queryProvinces(){
        textView.setText("中国省市");
        btnBack.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size()<1){
            String url = "http://guolin.tech/api/china";
            queryDataFromService(url,"province");
        }else {
            dataList.clear();
            for (Province privince:provinceList) {
                dataList.add(privince.getproviceName());
            }
            adapterArray.notifyDataSetChanged();
            listView.setSelection(0);
            selLevel =LEVEL_PROVIOCE;
        }
    }
    private void queryCities(){
        textView.setText(selProvice.getproviceName());
        btnBack.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("province=?",String.valueOf(selProvice.getId())).find(City.class);
        if (cityList.size()<1){
            int provinceCode = selProvice.getProviceCode();
            String address = "http://guolin.tech/api/china" + provinceCode;
            queryDataFromService(address,"city");
        }else {
            dataList.clear();
            for (City city:cityList) {
                dataList.add(city.getCityName());
            }
            adapterArray.notifyDataSetChanged();
            listView.setSelection(0);
            selLevel =LEVEL_CITY;
        }
    }
    private void queryCounties(){
        textView.setText(selCity.getCityName());
        btnBack.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid=?",String.valueOf(selCity.getId())).find(County.class);
        if (countyList.size()<1){
            int provinceCode = selProvice.getProviceCode();
            int cityCode = selCity.getCityCode();
            String address = "http://guolin.tech/api/china" + provinceCode+"/"+cityCode;
            queryDataFromService(address,"county");
        }else {
            dataList.clear();
            for (County county:countyList) {
                dataList.add(county.getCountyName());
            }
            adapterArray.notifyDataSetChanged();
            listView.setSelection(0);
            selLevel =LEVEL_COUNTY;
        }
    }
    private void  queryDataFromService(String url, final String type){
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"请求数据失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resJson = response.body().string();
                boolean result = false;
                switch (type)
                {
                    case "province":
                        result = Utility.handleProviceResponse(resJson);
                        break;
                    case "city":
                        result = Utility.handleCityResponse(resJson,selProvice.getId());
                        break;
                    case "county":
                        result = Utility.handleCountyResponse(resJson,selCity.getId());
                        break;
                }
                if ("province".equals(type)){
                    result = Utility.handleProviceResponse(resJson);
                } else if ("city".equals(type)){
                    result = Utility.handleCityResponse(resJson,selProvice.getId());
                } else if ("county".equals(type)){
                    result = Utility.handleCountyResponse(resJson,selCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            }  else if ("city".equals(type)){
                                queryCities();
                            } else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    private  void  showProgressDialog(){
        if (progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}
