package com.example.networkcapturesdk;

import java.io.IOException;
import java.util.Objects;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    testOkHttp();
    testWebView();
  }

  private void testOkHttp() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    Interceptor networkCaptureInterceptor = getNetworkCaptureInterceptor();
    if (networkCaptureInterceptor != null) {
      builder.addInterceptor(networkCaptureInterceptor);
    }
    OkHttpClient client = builder.build();
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

  @SuppressLint("SetJavaScriptEnabled")
  private void testWebView() {
    WebView.setWebContentsDebuggingEnabled(true);
    WebView webView = findViewById(R.id.webView);
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    webView.loadUrl("https://juejin.cn/");
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        String vConsoleJs = getVConsoleJs();
        if (!TextUtils.isEmpty(vConsoleJs)) {
          view.evaluateJavascript(vConsoleJs, null);
        }
      }
    });
  }

  @Nullable
  public static Interceptor getNetworkCaptureInterceptor() {
    try {
      Class<?> clazz =
          Class.forName("com.liuxiao352.networkcapturecore.interceptor.NetworkCaptureInterceptor");
      return (Interceptor) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      return null;
    }
  }

  @Nullable
  public static String getVConsoleJs() {
    try {
      Class<?> clazz =
          Class.forName("com.liuxiao352.networkcapturecore.utils.NetworkCaptureWebTools");
      return Objects.requireNonNull(clazz.getMethod("getVConsoleJs").invoke(null)).toString();
    } catch (Exception e) {
      return null;
    }
  }
}