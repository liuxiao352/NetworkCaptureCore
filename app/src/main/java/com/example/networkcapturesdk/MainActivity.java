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
        new Request.Builder().get().url("https://www.wanandroid.com/banner/json").build();
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