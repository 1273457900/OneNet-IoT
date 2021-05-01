package com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapplication.DataAnalysis.Datapoints;
import com.myapplication.DataAnalysis.Datastreams;
import com.myapplication.DataAnalysis.JsonRootBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    TextView air_temperature;//温度
    TextView air_humidity;//湿度
    TextView air_smoke;//烟雾
    TextView air_carbon;//一氧化碳
   // TextView DEV;
  //  TextView DEVID;
    Switch aSwitch;
    LinearLayout hum_layout;
    LinearLayout temp_layout;
    LinearLayout mq2_layout;
    LinearLayout fire_layout;

    private boolean autoChangeDates;
  //  private  String DeviceID ;//设备ID 679497608
   // private  String ApiKey ;//密钥  qHe2JVwH6C=F0DDc5faYsPqZt4s=

      private  String DeviceID="679497608" ;//设备ID 679497608
     private  String ApiKey="qHe2JVwH6C=F0DDc5faYsPqZt4s=" ;//密钥  qHe2JVwH6C=F0DDc5faYsPqZt4s=

    int maxID ;


    int count = 5;//获取数据的数量
    String[] array=new String[count];

    private Spinner spinner;
    private  List<Integer> list = new ArrayList<Integer>();
    private ArrayAdapter<Integer> adapter;
    Intent intent1;
    NetworkThread networkThread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        air_temperature=findViewById(R.id.temperature);//温度
        air_carbon=findViewById(R.id.carbon);//co
        air_smoke=findViewById(R.id.smoke);//烟雾
        air_humidity=findViewById(R.id.humidity);//湿度
        hum_layout=findViewById(R.id.hum_lauout);
        temp_layout=findViewById(R.id.temp_layout);
        mq2_layout=findViewById(R.id.mq2_layout);
        fire_layout=findViewById(R.id.fire_layout);
         intent1 = new Intent(MainActivity.this,line_chart.class);

      //  DEV=findViewById(R.id.DEV);
    //    DEVID=findViewById(R.id.DEVID);
        aSwitch=findViewById(R.id.aSwitch);
        autoChangeDates=false;

       /* final Intent intent = getIntent();
        DeviceID=intent.getStringExtra("DeviceID");
        ApiKey=intent.getStringExtra("ApiKey");
        Log.w("DeviceID",DeviceID);
        Log.w("Apikey",ApiKey);
            String url_g="http://api.heclouds.com/devices/" + DeviceID + "/datapoints";

*/


       networkThread.start();


//        networkThread1.networkThread();


        mq2_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent1 = new Intent(MainActivity.this,line_chart.class);
                intent1.putExtra("data",array[4]);
                startActivity(intent1);
            }
        });

    }




    private Handler mainHandle = new Handler(Looper.getMainLooper());
    private Thread networkThread = new Thread(){
        @Override
        public void run() {
            super.run();
                    Get();
        }
    };

    public void Get() {
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
                parseJSONWithGSON(responseData);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void parseJSONWithGSON(String jsonData) {

        JsonRootBean app = new Gson().fromJson(jsonData, JsonRootBean.class);
        List<Datastreams> streams = app.getData().getDatastreams();
        //获取数据流名称

        for (int j=0;j<streams.size();j++){
            String id = streams.get(j).getId();
            Log.w("id","id="+id);
            String toValue = null; //承接value值
            List<Datapoints> points = streams.get(j).getDatapoints();
            for (int i = 0; i < points.size(); i++) {
                String time = points.get(i).getAt();
                String value = points.get(i).getValue();
                Log.w("tag","time="+time);
                Log.w("tag","value="+value);
                Log.w("tag","string="+count);
                toValue=value;
            }
            array[j]=toValue;
            Log.w("array[%d]"+j,"="+array[j]);

        }

        mainHandle.post(new Runnable() {
            @Override
            public void run() {
                //UI更新
                int x=Integer.valueOf(array[3]);//设备ID

                if(maxID !=Math.max(x, maxID))
                {
                    maxID = Math.max(x, maxID);
                    setList(maxID);
                }

                int select = getSelectitems();

                if (autoChangeDates==false) {
                    if (select == x) {
                        UIupdate(array);
                    }
                }else
                {
                    UIupdate(array);
                }

                Log.w("maxID ","="+maxID);
            }


        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //选中时 do some thing
                    autoChangeDates=true;
                    Toast.makeText(MainActivity.this, "自动显示设备数据已打开",  Toast.LENGTH_SHORT).show();
                } else {
                    //非选中时 do some thing
                    autoChangeDates=false;
                    Toast.makeText(MainActivity.this, "手动选择设备数据已打开", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //networkThread.interrupt();
    }

    public void setList(int maxID)
    {
        list.clear();
        for (int i=1;i<=maxID;i++)
        {
            list.add(i);
        }
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = new ArrayAdapter<Integer>(MainActivity.this, R.layout.items, list);

        spinner.setAdapter(adapter);
    }

    public int getSelectitems(){

        int select = (int)spinner.getSelectedItemId()+1;;
        Log.w("select","%d"+select);
        return select;
    }

    public void UIupdate(String[] array){

      //  DEVID.setText("\n  " + array[3]);//设备ID
        spinner.setSelection(Integer.parseInt(array[3])-1);
        air_humidity.setText(array[0]);//湿度
        air_temperature.setText(array[1]);//温度
        if (array[2].equals("0"))//火焰光线
        {
            air_carbon.setText("弱");
            air_carbon.setTextColor(Color.rgb(104,131, 139));
        }

        else
            {
            air_carbon.setText("强");
            air_carbon.setTextColor(Color.rgb(255,0, 0));
            }

        air_smoke.setText(array[4]);//MQ2

    }




}
