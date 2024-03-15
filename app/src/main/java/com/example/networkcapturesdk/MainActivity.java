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
        new Request.Builder().get().url("https://test.matchplay.com/api/sports-query/score/matches/list?maxTime=2024-09-11 00:00:00 GMT+0800&sportId=sr:sport:1&sortType=1&minTime=2023-09-17 00:00:00 GMT+0800&rollType=1&showOdds=0&startTime=2024-03-15 00:00:00 GMT+0800&showSubscribe=0&live=0").build();
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