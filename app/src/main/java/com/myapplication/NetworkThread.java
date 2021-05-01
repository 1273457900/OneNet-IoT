package com.myapplication;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapplication.DataAnalysis.Datapoints;
import com.myapplication.DataAnalysis.Datastreams;
import com.myapplication.DataAnalysis.JsonRootBean;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkThread {

    int count = 10;//获取数据的数量
    String[] array=new String[count];
    int maxID ;
    public  String DeviceID ;//设备ID 679497608
    public String ApiKey ;//密钥  qHe2JVwH6C=F0DDc5faYsPqZt4s=

    private Handler mainHandle = new Handler(Looper.getMainLooper());


    public void setDeviceID(String DeviceID){
        this.DeviceID=DeviceID;
    }

    public void setApikey(String ApiKey) {
        this.ApiKey=ApiKey;
    }
    public String[] getarray(){
        return array;
    }
    public String getMQ2(){
        return array[2*4];
    }







    public String[]  Get() {
        String []arr=new String[100];
        final String[] a=new String[100];
        try {

            String url_g="http://api.heclouds.com/devices/" + DeviceID + "/datapoints";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url_g)
                    .header("api-key", ApiKey)
                    .build();
            Log.w("url_g",url_g);
            Log.w("ApiKey",ApiKey);
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            arr=parseJSONWithGSON(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private String[]  parseJSONWithGSON(String jsonData) {

        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        //获取数据流名称

        for (int j=0;j<streams.size();j++){
            String id = streams.get(j).getId();
            Log.w("id","id="+id);
            String toValue = null; //承接value值
            String toTime = null; //承接value值
            List<Datapoints> points = streams.get(j).getDatapoints();
            for (int i = 0; i < points.size(); i++) {
                String time = points.get(i).getAt();
                String value = points.get(i).getValue();
                Log.w("tag","time="+time);
                Log.w("tag","value="+value);
                Log.w("tag","string="+count);
                toValue=value;
                toTime=time;
            }

            array[2*j]=toValue;
            array[2*j+1]=toTime;
            Log.w("array[%d]"+2*j,"="+array[2*j]);

        }
        return  array;

    }



}
