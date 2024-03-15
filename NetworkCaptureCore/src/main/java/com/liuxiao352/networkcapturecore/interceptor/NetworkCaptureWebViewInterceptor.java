package com.liuxiao352.networkcapturecore.interceptor;

import java.nio.charset.StandardCharsets;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import com.liuxiao352.networkcapturecore.database.NetworkCaptureDatabase;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureDetail;
import com.liuxiao352.networkcapturecore.entity.NetworkCaptureLog;
import com.liuxiao352.networkcapturecore.utils.NetworkCaptureTools;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okio.Buffer;

public class NetworkCaptureWebViewInterceptor {

  public static void shouldInterceptRequest(WebResourceRequest request,
      WebResourceResponse response) {
    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
      return;
    }
    long requestTime = System.currentTimeMillis();
    long responseTime = requestTime;
    long duration = 0;
    String scheme = "";
    String host = "";
    String query = "";
    String path = "";
    int responseCode = -1;
    String responseHeader = "";
    String responseBody = "";
    String method = "";
    String requestHeader = "";
    String requestBody = "";
    String requestContentType = "";
    try {
      Uri uri = request.getUrl();
      if (uri.getScheme() != null) {
        scheme = uri.getScheme();
      }
      if (uri.getHost() != null) {
        host = uri.getHost();
      }
      if (uri.getQuery() != null) {
        query = uri.getQuery();
      }
      if (uri.getPath() != null) {
        path = uri.getPath();
      }
      method = request.getMethod();
      requestHeader = NetworkCaptureTools.toJson(request.getRequestHeaders());

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      NetworkCaptureLog log =
          new NetworkCaptureLog(scheme, host, path, query, method, responseCode, duration,
              requestTime);
      NetworkCaptureDetail detail =
          new NetworkCaptureDetail(scheme, host, path, query, method, responseCode, requestHeader,
              requestBody, requestContentType, responseHeader, responseBody, requestTime,
              responseTime, duration);
      NetworkCaptureDatabase.getInstance().getNetworkCaptureDao().insert(log, detail);
    }
  }
}
