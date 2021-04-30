package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myapplication.DataAnalysis.Datastreams;
import com.myapplication.DataAnalysis.JsonRootBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private static  String DeviceID = "";//679497608
    private static  String ApiKey = "";//qHe2JVwH6C=F0DDc5faYsPqZt4s=

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        //获取数据线程
        final Runnable getRunable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                  GoLogin();
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                Looper.loop();
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceID = usernameEditText.getText().toString();
                ApiKey = passwordEditText.getText().toString();
                // Handle view click here
                new Thread(getRunable).start();
            }
        });

    }


    public void  GoLogin(){
        //        Toast.makeText(MainActivity.this, "开始从云服务器获取数据", Toast.LENGTH_SHORT).show();    //提示
     //   String response = null;
        int errno;


        try{

            URL url = new URL("http://api.heclouds.com/devices/" + DeviceID + "/datastreams/" );
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(15*1000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("api-key",ApiKey);
            if (connection.getResponseCode() == 200){   //返回码是200，网络正常

           /*     InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                int len = 0;
                byte buffer[] = new byte[1024];
                while((len = inputStream.read(buffer))!=-1){
                    os.write(buffer,0,len);
                }
                inputStream.close();
                os.close();
                response = os.toString();
                Log.w("response",response);*/

                String url_g="http://api.heclouds.com/devices/" + DeviceID + "/datapoints";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url_g)
                        .header("api-key", ApiKey)
                        .build();
                Log.w("url_g",url_g);
                Log.w("ApiKey",ApiKey);
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();


                JsonRootBean app = new Gson().fromJson(responseData, JsonRootBean.class);
                 errno = app.getErrno();

                Log.w("response_errno", String.valueOf(errno));


                if(errno==0) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("DeviceID", DeviceID);
                    intent.putExtra("ApiKey", ApiKey);


                    Log.w("message:", DeviceID + "," + ApiKey);
                    startActivity(intent);
                }




            }else{
                //返回码不是200，网络异常
                Toast.makeText(LoginActivity.this, "网络异常,登录失败 ", Toast.LENGTH_SHORT).show();


            }

        }catch (IOException e){
            Toast.makeText(LoginActivity.this, " 获取数据失败 ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

    }
}