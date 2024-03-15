package com.example.networkcapturesdk;

import java.io.IOException;

import android.os.Bundle;

import com.liuxiao352.networkcapturecore.interceptor.NetworkCaptureInterceptor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    OkHttpClient client =
        new OkHttpClient.Builder().addInterceptor(new NetworkCaptureInterceptor()).build();
    Request request =
        new Request.Builder().get().url("\n" +
            "https://a241980011c4cf29fa436afeb37d6180.dlied1.cdntips.net/imtt2.dd.qq.com/sjy.00009/sjy.00004/16891/apk/5CBCC45758184DF2688A2B322CEC4C54.apk?mkey=lego_ztc&f=00&sche_type=7&fsname=com.xunmeng.pinduoduo_7.1.0.apk&cip=222.128.0.50&proto=https").build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(@NonNull Call call, @NonNull IOException e) {

      }

      @Override
      public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

      }
    });
  }
}